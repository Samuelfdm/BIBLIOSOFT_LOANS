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

        // Obtenemos la información del ejemplar
        CopyDTO copy = bookServiceClient.getBookCopyById(loanRequest.getCopyId()).block();

        // Verificar que el estudiante no tenga un préstamo activo del libro asociado al ejemplar solicitado
        if (copy == null || checkStudentHasBook(student.getId(), copy.getBookId())) {
            throw new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
        }

        //Verificamos la disponibilidad del ejemplar
        if (!"AVAILABLE".equals(copy.getDisponibility())) {
            throw new BookLoanException(BookLoanException.ErrorType.STUDENT_ALREADY_HAS_BOOK);
        }

        // Creamos el prestamo con toda su información
        LocalDate returnDate = generateReturnDate(copy);
        Loan loan = new Loan(
                loanRequest.getStudentId(),
                loanRequest.getCopyId(),
                copy.getBookId(),
                LocalDate.now(),
                returnDate,
                LoanState.Loaned
        );

        // Actualizar la información del Historial del ejemplar en el prestamo
        CopyState initialCopyState = CopyState.valueOf(copy.getState());
        LoanHistory loanHistory = updateHistory(initialCopyState);
        loan.addHistory(loanHistory);

        // Guardar el préstamo y el historial en las bases de datos
        loanRepository.save(loan);
        LoanHistoryRepository.save(loanHistory);

        // Cambiar la disponibilidad del ejemplar en el módulo de libros a "BORROWED"
        bookServiceClient.updateCopyDisponibility(loanRequest.getCopyId(), LoanState.Loaned);

        // Retornar la respuesta del préstamo
        return new LoanResponseDTO(
                loan.getId(),
                loan.getCopyId(),
                loan.getBookId(),
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
         return loanRepository.findByBookIdAndStudentIdAndLoanState(bookCode, studentId, LoanState.Loaned);
    }

    @Override
    @Transactional
    public ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest) {
        // Lógica para encontrar el préstamo correspondiente para retornar
        Loan loan = loanRepository.findByCopyIdAndStudentIdAndLoanState(returnRequest.getCopyId(), returnRequest.getStudentId(), LoanState.Loaned);
        if (loan == null) {
            throw new BookLoanException(BookLoanException.ErrorType.NO_LOAN_FOUND);
        }

        // Determinar el estado final de la copia
        CopyState finalCopyState = returnRequest.getFinalCopyState();
        LoanHistory loanHistory = updateHistory(finalCopyState);
        loan.addHistory(loanHistory);

        // Actualizar el préstamo en la base de datos, marcándolo como devuelto
        loan.setLoanState(LoanState.Returned);
        loanRepository.save(loan);

        // Actualizar la disponibilidad y el estado del ejemplar en el módulo de libros
        bookServiceClient.updateCopyDisponibility(returnRequest.getCopyId(), LoanState.Returned);

        // Actualizar el estado en que llego el ejemplar en el módulo de libros
        bookServiceClient.updateCopyState(returnRequest.getCopyId(), finalCopyState);

        // Retornar el DTO con los detalles de la devolución
        return new ReturnResponseDTO(loan.getId(), loanHistory.getDate(), finalCopyState);
    }

    @Override
    public List<Loan> loansActive() {
        return loanRepository.findByLoanState(LoanState.Loaned);
    }

    @Override
    public List<Loan> loansActiveStudent(Long studentId) {
        return loanRepository.findByStudentIdAndLoanState(studentId, LoanState.Loaned);
    }

    @Override
    public List<Loan> loansAllStudent(Long studentId) {
        return loanRepository.findByStudentId(studentId);
    }

    public List<Loan> loansAll(){
        return loanRepository.findAll();
    }

    public LoanHistory updateHistory(CopyState copyState){
        //Actualizamos fechas de prestamo o retorno y los estados en que llego el ejemplar
        LoanHistory history = new LoanHistory(LocalDate.now(),copyState);
        return LoanHistoryRepository.save(history);
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
}