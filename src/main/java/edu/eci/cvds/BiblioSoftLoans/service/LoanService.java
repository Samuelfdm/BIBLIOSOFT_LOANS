package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.client.BookServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.StudentServiceClient;
import edu.eci.cvds.BiblioSoftLoans.dto.*;
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
            throw new IllegalArgumentException("Student not found.");
        }

        // Verificar que no tenga un prestamo activo del LIBRO solicitado.
        // Si el estudiante ya tiene un prestamo activo del LIBRO solicitado, NO le puedo dar otra COPIA.
        List<Loan> loansActive = loanRepository.findByCopyIdAndStudentIdAndLoanState(loanRequest.getCopyId(),loanRequest.getStudentId(), LoanState.Loaned);
        if (loansActive != null) {
            throw new IllegalArgumentException("The student already has a loan of this book.");
        }

        // Verificar que el ejemplar está disponible, es decir, si aun hay copias para prestar o ya se acabaron.
        CopyDTO copy = bookServiceClient.getBookCopyById(loanRequest.getCopyId()).block();
        if (copy == null || !"AVAILABLE".equals(copy.getDisponibility())) {
            throw new IllegalStateException("The copy is not available for loan.");
        }

        // Crear un nuevo préstamo
        Loan loan = new Loan();
        loan.setCopyId(loanRequest.getCopyId());
        loan.setStudentId(loanRequest.getStudentId());
        loan.setLoanDate(LocalDate.now());
        //loan.setReturnDate(calculateReturnDate(copy));
        loan.setLoanState(LoanState.Loaned);

        //Agregar el estado del libro en el historial al ser prestado inicialmente
        CopyState initialCopyState = CopyState.valueOf(copy.getState()); //ESTO PUEDE FALLAR PORQUE EL MODULO DE LIBROS TIENE NOMBRES DIFERENTES
        LoanHistory loanHistory = new LoanHistory(LocalDate.now(), initialCopyState);
        loan.addHistory(loanHistory);

        loanRepository.save(loan);
        LoanHistoryRepository.save(loanHistory);

        // Cambiar la disponibilidad del ejemplar en el modulo de libros a "Loaned" mediante el cliente del servicio de libros
        //El modulo de libros maneja una enumeracion con: AVAILABLE y BORROWED deberia ser LOANED
        bookServiceClient.updateCopyDisponibility(loanRequest.getCopyId(), LoanState.Loaned);

        return new LoanResponseDTO(loan.getId(), loan.getCopyId(), loan.getStudentId(), loan.getLoanDate(), loan.getReturnDate(), loan.getLoanState(), loan.getLoanHistory());
    }

    @Override
    @Transactional
    public ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest) {
        // Lógica para encontrar el préstamo correspondiente para retornar
        Long loanId = loanRepository.findIdByCopyIdAndStudentIdAndLoanState(returnRequest.getCopyId(), returnRequest.getStudentId(), LoanState.Loaned);
        if (loanId == null) {
            throw new IllegalArgumentException("No se encontró un préstamo activo para la copia y usuario dados.");
        }

        // Colocar la fecha actual de devolución
        LocalDate returnDate = LocalDate.now();

        // Determinar el estado final de la copia
        // Cuando devuelven un libro se supone que el administrador escribe en que estado llego
        CopyState finalCopyState = returnRequest.getFinalCopyState();

        //actualizar historial de estado
        //ESTO ME FALTA POR IMPLEMENTAR

        // Actualizar el préstamo en la base de datos, marcándolo como devuelto
        // TOCA DECIDIR SI guardamos el prestamo como devuelto en la base o lo eliminamos por completo
        //updateLoanAsReturned(loanId, returnDate, LoanState.Returned);

        // Retornar el DTO con los detalles de la devolución
        return new ReturnResponseDTO(loanId, returnDate, finalCopyState);
    }

    public List<LoanHistory> getLoanHistory(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + loanId));
        return loan.getLoanHistory();
    }
}