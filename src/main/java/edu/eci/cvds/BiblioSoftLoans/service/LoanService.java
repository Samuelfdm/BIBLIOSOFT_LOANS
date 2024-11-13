package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.client.BookServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.StudentServiceClient;
import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService implements ILoanService {

    @Autowired
    private LoanRepository loanRepository;

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

        // Verificar que no tenga un prestamo activo del ejemplar solicitado


        // Verificar que el ejemplar está disponible
        CopyDTO copy = bookServiceClient.getBookCopyById(loanRequest.getCopyId()).block();
        if (!"AVAILABLE".equals(copy.getDisponibility())) {
            throw new IllegalStateException("The copy is not available for loan.");
        }

        // Crear un nuevo préstamo
        Loan loan = new Loan();
        loan.setCopyId(loanRequest.getCopyId());
        loan.setStudentId(loanRequest.getStudentId());
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(calculateReturnDate(copy));
        loan.setLoanState(LoanState.Loaned);

        loanRepository.save(loan);

        // Cambiar el estado del ejemplar a "Loaned" mediante el cliente del servicio de libros
        bookServiceClient.updateCopyDisponibility(loanRequest.getCopyId(), "Loaned");

        return new LoanResponseDTO(loan.getId(), loan.getCopyId(), loan.getUserId(), loan.getLoanDate(), loan.getReturnDate(), loan.isReturned());
    }

    @Override
    @Transactional
    public ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest) {
        // Lógica para encontrar el préstamo correspondiente
        Long loanId = loanRepository.findIdByCopyIdAndStudentIdAndLoanState(returnRequest.getCopyId(), returnRequest.getStudentId(), LoanState.Loaned);
        if (loanId == null) {
            throw new IllegalArgumentException("No se encontró un préstamo activo para la copia y usuario dados.");
        }

        // Calcular la fecha de devolución
        LocalDate returnDate = LocalDate.now();

        // Determinar el estado final de la copia
        CopyState finalCopyState = inspectCopyState(returnRequest.getCopyId());

        // Actualizar el préstamo en la base de datos, marcándolo como devuelto
        updateLoanAsReturned(loanId, returnDate, finalCopyState);

        // Retornar el DTO con los detalles de la devolución
        return new ReturnResponseDTO(loanId, returnDate, finalCopyState);
    }

    @Override
    public CopyDTO getCopy(String copyId) {
        return bookServiceClient.getBookCopyById(copyId).block();
    }

    @Override
    public List<LoanResponseDTO> getActiveLoansByStudent(Long studentId) {
        List<Loan> activeLoans = loanRepository.findByStudentIdAndLoanState(studentId, LoanState.Loaned);

        return activeLoans.stream()
                .map(loan -> new LoanResponseDTO(loan.getId(), loan.getCopyId(), loan.getStudentId(), loan.getLoanDate(), loan.getReturnDate(), loan.isReturned()))
                .collect(Collectors.toList());
    }
}