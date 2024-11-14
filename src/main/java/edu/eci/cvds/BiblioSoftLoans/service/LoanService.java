package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.client.BookServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.StudentServiceClient;
import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.exception.BookApiException;
import edu.eci.cvds.BiblioSoftLoans.exception.BookLoanException;
import edu.eci.cvds.BiblioSoftLoans.exception.StudentException;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanHistoryRepository;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
public class LoanService implements ILoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanHistoryRepository LoanHistoryRepository;

    @Autowired
    private BookServiceClient bookServiceClient;

    @Autowired
    private StudentServiceClient studentServiceClient;

    @Override
    @Transactional
    public LoanResponseDTO requestLoan(LoanRequestDTO loanRequest) {
        // Verificar que el estudiante existe
        StudentDTO student = studentServiceClient.getStudentById(loanRequest.getStudentId()).block();
        if (student == null) {
            throw new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND);
        }

        // Verificar que el estudiante no tenga un préstamo activo del libro solicitado
        if (!checkStudentHasBook(loanRequest.getStudentId(), loanRequest.getCopyId())) {
            throw new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
        }

        // Verificar que el ejemplar está disponible
        CopyDTO copy = bookServiceClient.getBookCopyById(loanRequest.getCopyId()).block();
        if (copy == null || !"AVAILABLE".equals(copy.getDisponibility())) {
            throw new BookLoanException(BookLoanException.ErrorType.STUDENT_ALREADY_HAS_BOOK);
        }

        // Crear el préstamo con la fecha de devolución generada
        LocalDate returnDate = generateReturnDate(copy);
        Loan loan = new Loan(
                loanRequest.getStudentId(),
                loanRequest.getCopyId(),
                LocalDate.now(),
                returnDate,
                LoanState.Loaned
        );

        // Guardar el préstamo en la base de datos
        loanRepository.save(loan);

        // Cambiar la disponibilidad del ejemplar en el módulo de libros a "BORROWED"
        bookServiceClient.updateCopyDisponibility(loanRequest.getCopyId(), LoanState.Loaned);

        // Retornar la respuesta del préstamo
        return new LoanResponseDTO(
                loan.getId(),
                loan.getCopyId(),
                loan.getStudentId(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getLoanState(),
                loan.getLoanHistory()
        );
    }


    /**
     * Checks if a student currently has a specific book loaned.
     *
     * @param studentId the ID of the student.
     * @param bookCode the code of the book.
     * @return true if the student has the book, false otherwise.
     */
    public boolean checkStudentHasBook(Long studentId, String bookCode) {
        List<Loan> loans = loanRepository.findByCopyIdAndStudentId(bookCode, studentId);
        return loans.isEmpty();
    }

    @Override
    @Transactional
    public ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest) {
        // Lógica para encontrar el préstamo correspondiente para retornar
        Long loanId = loanRepository.findIdByCopyIdAndStudentIdAndLoanState(returnRequest.getCopyId(), returnRequest.getStudentId(), LoanState.Loaned);
        if (loanId == null) {
            throw new BookLoanException(BookLoanException.ErrorType.NO_LOAN_FOUND);
        }
        LocalDate returnDate = LocalDate.now();
        // Determinar el estado final de la copia
        // Cuando devuelven un libro se supone que el administrador escribe en que estado llego
        CopyState finalCopyState = returnRequest.getFinalCopyState();
        loanRepository.deleteById(loanId);
        updateHistory(finalCopyState);

        // Actualizar el préstamo en la base de datos, marcándolo como devuelto
        // TOCA DECIDIR SI guardamos el prestamo como devuelto en la base o lo eliminamos por completo
        //updateLoanAsReturned(loanId, returnDate, LoanState.Returned);

        // Retornar el DTO con los detalles de la devolución
        return new ReturnResponseDTO(loanId, returnDate, finalCopyState);
    }

    public void updateHistory(CopyState copyState){
        LoanHistory history = new LoanHistory(LocalDate.now(),copyState);
        LoanHistoryRepository.save(history);
    }


    public List<LoanHistory> getLoanHistory(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + loanId));
        return loan.getLoanHistory();
    }

    /**
     * Genera la fecha de devolución de un préstamo según la categoría del libro.
     *
     * @param copyRequest Copia del libro.
     * @return La fecha de devolución para el préstamo.
     * @throws BookApiException si la categoría del libro no es válida o no se encuentra.
     */
    public LocalDate generateReturnDate(CopyDTO copyRequest) {
        LocalDate loanDate = LocalDate.now();
        String bookCategory = copyRequest.getCategory();
        int daysToAdd;

        try {
            daysToAdd = switch (bookCategory) {
                case "INFANTIL" -> 7;
                case "LITERATURA" -> 14;
                case "NO_FICCION" -> 10;
                case "CUENTOS" -> 5;
                default -> 18;
            };
        } catch (IllegalArgumentException e) {
            throw new BookApiException(BookApiException.ErrorType.DATA_NOT_FOUND, e);
        }

        return loanDate.plusDays(daysToAdd);
    }

    public String showAvailability(String id) {
        CopyDTO copy = bookServiceClient.getBookCopyById(id).block();
        return (copy != null) ? copy.getDisponibility() : "Unavailable";
    }


    public List<Loan> showLoan(){
        return loanRepository.findAll();
    }


}