package edu.eci.cvds.BiblioSoftLoans.controller;
/*
import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import edu.eci.cvds.BiblioSoftLoans.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestLoanSuccess() {
        LoanRequestDTO request = new LoanRequestDTO(1L, "COPY123");
        LoanResponseDTO response = new LoanResponseDTO(1L, "COPY123", 1L, LocalDate.now(), LocalDate.now().plusDays(7), LoanState.Loaned, new ArrayList<>());

        when(loanService.requestLoan(request)).thenReturn(response);
        ResponseEntity<LoanResponseDTO> result = loanController.requestLoan(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testReturnLoanSuccess() {
        ReturnRequestDTO request = new ReturnRequestDTO(1L, "COPY123",CopyState.Moderate);
        ReturnResponseDTO response = new ReturnResponseDTO(1L, LocalDate.now(), CopyState.Moderate);

        when(loanService.returnBook(request)).thenReturn(response);
        ResponseEntity<ReturnResponseDTO> result = loanController.returnLoan(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testGetLoanHistorySuccess() {
        Long loanId = 1L;
        List<LoanHistory> history = new ArrayList<>();
        history.add(new LoanHistory(LocalDate.now(), CopyState.Moderate));

        when(loanService.getLoanHistory(loanId)).thenReturn(history);
        ResponseEntity<List<LoanHistory>> result = loanController.getLoanHistory(loanId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(history, result.getBody());
    }

    @Test
    void testGetLoanHistoryNotFound() {
        Long loanId = 99L;

        when(loanService.getLoanHistory(loanId)).thenThrow(new IllegalArgumentException("Loan history not found"));
        ResponseEntity<List<LoanHistory>> result = loanController.getLoanHistory(loanId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testShowAvailabilitySuccess() {
        String copyId = "COPY123";
        String expectedAvailability = "Available";

        when(loanService.showAvailability(copyId)).thenReturn(expectedAvailability);
        String result = loanController.showAvailability(copyId);

        assertEquals(expectedAvailability, result);
    }

    @Test
    void testShowAvailabilityUnavailable() {
        String copyId = "COPY999";
        String expectedAvailability = "Unavailable";

        when(loanService.showAvailability(copyId)).thenReturn(expectedAvailability);
        String result = loanController.showAvailability(copyId);

        assertEquals(expectedAvailability, result);
    }

    @Test
    void testShowLoanSuccess() {
        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan());

        when(loanService.showLoan()).thenReturn(loans);
        List<Loan> result = loanController.showLoan();

        assertEquals(loans, result);
    }

    @Test
    void testRequestLoanWithInvalidData() {
        LoanRequestDTO request = new LoanRequestDTO(1L, "COPY123");
        when(loanService.requestLoan(request)).thenThrow(new IllegalArgumentException("Invalid loan request"));

        assertThrows(IllegalArgumentException.class, () -> loanController.requestLoan(request));
    }

    @Test
    void testReturnLoanWithInvalidData() {
        ReturnRequestDTO request = new ReturnRequestDTO(1L, "COPY123", CopyState.Moderate);
        when(loanService.returnBook(request)).thenThrow(new IllegalArgumentException("Invalid return request"));

        assertThrows(IllegalArgumentException.class, () -> loanController.returnLoan(request));
    }

    @Test
    void testGetLoanHistoryEmptyList() {
        Long loanId = 1L;
        when(loanService.getLoanHistory(loanId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<LoanHistory>> result = loanController.getLoanHistory(loanId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().isEmpty());
    }

    @Test
    void testShowAvailabilityInvalidCopyId() {
        String invalidCopyId = "INVALID_ID";
        when(loanService.showAvailability(invalidCopyId)).thenThrow(new IllegalArgumentException("Invalid copy ID"));

        assertThrows(IllegalArgumentException.class, () -> loanController.showAvailability(invalidCopyId));
    }

    @Test
    void testShowLoanNoLoans() {
        when(loanService.showLoan()).thenReturn(new ArrayList<>());

        List<Loan> result = loanController.showLoan();

        assertTrue(result.isEmpty());
    }

    @Test
    void testReturnLoanWithServiceError() {
        ReturnRequestDTO request = new ReturnRequestDTO(1L, "COPY123", CopyState.Moderate);
        when(loanService.returnBook(request)).thenThrow(new RuntimeException("Service unavailable"));

        assertThrows(RuntimeException.class, () -> loanController.returnLoan(request));
    }
}
*/