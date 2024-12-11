package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.client.BookServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.StudentServiceClient;
import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.exception.BookApiException;
import edu.eci.cvds.BiblioSoftLoans.exception.BookLoanException;
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
        String studentId = "67323424";
        // Verificar que el estudiante existe

        //StudentDTO student = studentServiceClient.getStudentById(loanRequest.getStudentId()).block();

        //if (student == null) {
            //throw new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND);
        //}

        CopyDTO copy = bookServiceClient.getBookCopyById(loanRequest.getCopyId()).block();
        System.out.println(copy.toString());

        // Verificar que el estudiante no tenga un préstamo activo del libro asociado al ejemplar solicitado
        if (copy == null || checkStudentHasBook(studentId, copy.getBook())) {
            throw new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
        }

        //Verificamos la disponibilidad del ejemplar
        if (copy.getDisponibility() != null && !"AVAILABLE".equals(copy.getDisponibility())) {
            throw new BookLoanException(BookLoanException.ErrorType.STUDENT_ALREADY_HAS_BOOK);
        }

        // Creamos el prestamo con toda su información
        LocalDate maxReturnDate = generateReturnDate(copy);
        Loan loan = new Loan(
                studentId,
                loanRequest.getCopyId(),
                copy.getBook(),
                LocalDate.now(),
                maxReturnDate,
                LoanState.Loaned
        );

        // Guardar el préstamo en la base de datos
        loanRepository.save(loan);

        // Actualizar la información del Historial del ejemplar en el prestamo
        String initialCopyState = copy.getState();
        // Crea el nuevo registro de historial y lo guarda en la base de datos LoanHistory
        LoanHistory loanHistory = updateHistory(CopyState.valueOf(initialCopyState),loan);




        // Cambiar la disponibilidad del ejemplar en el módulo de libros a "BORROWED"
        //bookServiceClient.updateCopyDisponibility(loanRequest.getCopyId(), LoanState.Loaned);

        // Retornar la respuesta del préstamo
        return new LoanResponseDTO(
                loan.getId(),
                loan.getCopyId(),
                loan.getBookId(),
                loan.getStudentId(),
                loan.getLoanDate(),
                loan.getMaxReturnDate(),
                loan.getLoanState(),
                loan.getLoanHistory()
        );
    }

    @Override
    @Transactional
    public ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest) {
        // Lógica para encontrar el préstamo correspondiente para retornar
        Loan loan = loanRepository.findByCopyIdAndStudentIdAndLoanState(returnRequest.getCopyId(), returnRequest.getStudentId(), LoanState.Loaned);
        if (loan == null) {
            System.out.println("aqui si");
            throw new BookLoanException(BookLoanException.ErrorType.NO_LOAN_FOUND);
        }

        // Determinar el estado final de la copia
        CopyState finalCopyState = returnRequest.getFinalCopyState();

        // Crea el nuevo registro de historial y lo guarda en la base de datos LoanHistory
        LoanHistory loanHistory = updateHistory(finalCopyState,loan);

        // Actualizar el préstamo en la base de datos, marcándolo como devuelto
        loan.setLoanState(LoanState.Returned);
        loanRepository.save(loan);

        // Actualizar la disponibilidad y el estado del ejemplar en el módulo de libros
        bookServiceClient.updateCopy(returnRequest.getCopyId(), CopyDTO.CopyDispo.AVAILABLE,finalCopyState);

        // Retornar el DTO con los detalles de la devolución
        return new ReturnResponseDTO(loan.getId(), loanHistory.getRecordDate(), finalCopyState);
    }

    @Override
    public List<Loan> getLoans(){
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getLoans(String state) {
        return loanRepository.findByLoanState(LoanState.valueOf(state));
    }

    @Override
    public List<Loan> getLoansStudent(Long studentId) {
        return List.of();
    }

    public List<Loan> getLoansStudentState(String studentId, String state) {
        return  loanRepository.findByStudentIdAndLoanState(studentId, LoanState.valueOf(state));
    }

    @Override
    public List<Loan> getLoansStudent(String studentId) {
        return loanRepository.findByStudentId(studentId);
    }

    @Override
    public List<Loan> getLoansStudent(String studentId, String state) {
        return List.of();
    }

    public LoanHistory updateHistory(CopyState copyState,Loan loan){
        //Actualizamos fechas de prestamo o retorno y los estados en que llego el ejemplar
        LoanHistory history = new LoanHistory(LocalDate.now(),copyState, loan);
        return LoanHistoryRepository.save(history);
    }

    public boolean checkStudentHasBook(String studentId, String bookCode) {
        if (bookCode == null) {
            return false;
        }
        List<Loan> loans = loanRepository.findByBookIdAndStudentIdAndLoanState(bookCode, studentId, LoanState.Loaned);
        return !loans.isEmpty();
    }



    public LocalDate generateReturnDate(CopyDTO copyRequest) {
        LocalDate loanDate = LocalDate.now();
        String bookId = copyRequest.getBook();
        BookDTO book = bookServiceClient.getBookById(bookId).block();
        String bookCategory = book.getBookId();

        int daysToAdd;
        try {
            daysToAdd = switch (bookCategory) {
                case "Literatura" -> 14;
                case "Infantil" -> 7;
                case "Científico" -> 10;
                default -> 18;
            };
        } catch (IllegalArgumentException e) {
            throw new BookApiException(BookApiException.ErrorType.DATA_NOT_FOUND, e);
        }

        return loanDate.plusDays(daysToAdd);
    }

    public void checkForExpiredLoans() {
        List<Loan> activeLoans = loanRepository.findByLoanState(LoanState.Loaned);
        for (Loan loan : activeLoans) {
            // Si la fecha de vencimiento ha pasado, cambiamos el estado del préstamo a Expired
            if (loan.getMaxReturnDate().isBefore(LocalDate.now())) {
                loan.setLoanState(LoanState.Expired);
                loanRepository.save(loan); // Guardar el préstamo actualizado
            }
        }
    }

    public List<LoanHistory> getHistory(){
        return LoanHistoryRepository.findAll();
    }
}