package edu.eci.cvds.BiblioSoftLoans.controller;

import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.RequestDisponibilityDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.History.HistoryLoanDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanBookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanStudentDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.service.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @Test
    void testRequestLoan() {
        LoanRequestDTO request = new LoanRequestDTO();
        LoanResponseDTO response = new LoanResponseDTO();
        when(loanService.requestLoan(request)).thenReturn(response);

        ResponseEntity<LoanResponseDTO> result = loanController.requestLoan(request);

        assertNotNull(result);
        assertEquals(response, result.getBody());
        verify(loanService, times(1)).requestLoan(request);
    }

    @Test
    void testReturnLoan() {
        ReturnRequestDTO request = new ReturnRequestDTO();
        ReturnResponseDTO response = new ReturnResponseDTO();
        when(loanService.returnBook(request)).thenReturn(response);

        ResponseEntity<ReturnResponseDTO> result = loanController.returnLoan(request);

        assertNotNull(result);
        assertEquals(response, result.getBody());
        verify(loanService, times(1)).returnBook(request);
    }

    @Test
    void testGetLoans() {
        List<Loan> loans = Arrays.asList(new Loan(), new Loan());
        when(loanService.getLoans()).thenReturn(loans);

        List<Loan> result = loanController.getLoans();

        assertNotNull(result);
        assertEquals(loans.size(), result.size());
        verify(loanService, times(1)).getLoans();
    }

    @Test
    void testGetLoansByState() {
        String state = "Loaned";
        List<Loan> loans = Arrays.asList(new Loan());
        when(loanService.getLoans(state)).thenReturn(loans);

        List<Loan> result = loanController.getLoans(state);

        assertNotNull(result);
        assertEquals(loans.size(), result.size());
        verify(loanService, times(1)).getLoans(state);
    }

    @Test
    void testGetLoansStudent() {
        String studentId = "12345";
        List<Loan> loans = Arrays.asList(new Loan());
        when(loanService.getLoansStudent(studentId)).thenReturn(loans);

        List<Loan> result = loanController.getLoansStudent(studentId);

        assertNotNull(result);
        assertEquals(loans.size(), result.size());
        verify(loanService, times(1)).getLoansStudent(studentId);
    }

    @Test
    void testGetHistory() {
        List<HistoryLoanDTO> history = Arrays.asList(new HistoryLoanDTO());
        when(loanService.getHistory()).thenReturn(history);

        List<HistoryLoanDTO> result = loanController.getHistory();

        assertNotNull(result);
        assertEquals(history.size(), result.size());
        verify(loanService, times(1)).getHistory();
    }

    @Test
    void testGetDisponibilityByTitle() {
        RequestDisponibilityDTO request = new RequestDisponibilityDTO();
        request.setTitle("Book Title");
        List<String> disponibility = Arrays.asList("Copy1", "Copy2");
        when(loanService.getDisponibilityByTitle(request.getTitle())).thenReturn(disponibility);

        List<String> result = loanController.getDisponibilityByTitle(request);

        assertNotNull(result);
        assertEquals(disponibility.size(), result.size());
        verify(loanService, times(1)).getDisponibilityByTitle(request.getTitle());
    }

    @Test
    void testGetDisponibilityByTitle_EmptyTitle() {
        RequestDisponibilityDTO request = new RequestDisponibilityDTO();
        request.setTitle("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanController.getDisponibilityByTitle(request);
        });

        assertEquals("El título no puede estar vacío.", exception.getMessage());
    }

    @Test
    void testGetDisponibilityByAuthor() {
        RequestDisponibilityDTO request = new RequestDisponibilityDTO();
        request.setAuthor("Author Name");
        List<String> disponibility = Arrays.asList("Copy1", "Copy2");
        when(loanService.getDisponibilityByAuthor(request.getAuthor())).thenReturn(disponibility);

        List<String> result = loanController.getDisponibilityByAuthor(request);

        assertNotNull(result);
        assertEquals(disponibility.size(), result.size());
        verify(loanService, times(1)).getDisponibilityByAuthor(request.getAuthor());
    }

    @Test
    void testGetDisponibilityByAuthor_EmptyAuthor() {
        RequestDisponibilityDTO request = new RequestDisponibilityDTO();
        request.setAuthor("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanController.getDisponibilityByAuthor(request);
        });

        assertEquals("El autor no puede estar vacío.", exception.getMessage());
    }

    @Test
    void testGetLoansStudentWithState() {
        String studentId = "12345";
        String state = "Loaned";
        List<Loan> loans = Arrays.asList(new Loan());
        when(loanService.getLoansStudent(studentId, state)).thenReturn(loans);

        List<Loan> result = loanController.getLoansStudent(studentId, state);

        assertNotNull(result);
        assertEquals(loans.size(), result.size());
        verify(loanService, times(1)).getLoansStudent(studentId, state);
    }

    @Test
    void testGetHistoryByStudent() {
        String studentId = "12345";
        List<HistoryLoanStudentDTO> history = Arrays.asList(new HistoryLoanStudentDTO());
        when(loanService.getHistoryByStudent(studentId)).thenReturn(history);

        List<HistoryLoanStudentDTO> result = loanController.getHistoryByStudent(studentId);

        assertNotNull(result);
        assertEquals(history.size(), result.size());
        verify(loanService, times(1)).getHistoryByStudent(studentId);
    }

    @Test
    void testGetHistoryByCopy() {
        String copyId = "copy123";
        List<HistoryLoanBookDTO> history = Arrays.asList(new HistoryLoanBookDTO());
        when(loanService.getHistoryByCopy(copyId)).thenReturn(history);

        List<HistoryLoanBookDTO> result = loanController.getHistoryByCopy(copyId);

        assertNotNull(result);
        assertEquals(history.size(), result.size());
        verify(loanService, times(1)).getHistoryByCopy(copyId);
    }
}