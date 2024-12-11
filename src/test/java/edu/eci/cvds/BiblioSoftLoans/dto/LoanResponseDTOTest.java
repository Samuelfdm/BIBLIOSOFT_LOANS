package edu.eci.cvds.BiblioSoftLoans.dto;

import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class LoanResponseDTOTest {

    @Test
    public void testLoanResponseDTO() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(1L, "copy101", "book000", "67323424", LocalDate.now(), LocalDate.now().plusDays(7), LoanState.Loaned, Collections.emptyList());

        assertNotNull(loanResponse);
        assertEquals(Long.valueOf(1), loanResponse.getLoanId());
        assertEquals("copy101", loanResponse.getCopyId());
        assertEquals("67323424", loanResponse.getStudentId());
        assertEquals(LocalDate.now(), loanResponse.getLoanDate());
        assertEquals(LocalDate.now().plusDays(7), loanResponse.getReturnDate());
        assertEquals(LoanState.Loaned, loanResponse.getLoanState());
    }

    @Test
    public void testLoanResponseDTOWithEmptyLoanHistory() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(2L, "copy105", "book001", "67323425", LocalDate.now(), LocalDate.now().plusDays(10), LoanState.Loaned, Collections.emptyList());

        assertNotNull(loanResponse);
        assertTrue(loanResponse.getLoanHistory().isEmpty());
    }

    @Test
    public void testLoanResponseDTOReturnDateBeforeLoanDate() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(3L, "copy107", "book002", "67323426", LocalDate.now().plusDays(1), LocalDate.now(), LoanState.Loaned, Collections.emptyList());

        assertTrue(loanResponse.getReturnDate().isBefore(loanResponse.getLoanDate()));
    }

    @Test
    public void testLoanResponseDTOReturnDateEqualsLoanDate() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(4L, "copy108", "book003", "67323427", LocalDate.now(), LocalDate.now(), LoanState.Loaned, Collections.emptyList());

        assertEquals(loanResponse.getLoanDate(), loanResponse.getReturnDate());
    }

    @Test
    public void testLoanResponseDTOLoanStateOverdue() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(5L, "copy110", "book004", "67323428", LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), LoanState.Loaned, Collections.emptyList());

        assertEquals(LoanState.Loaned, loanResponse.getLoanState());
        assertTrue(loanResponse.getLoanDate().isBefore(LocalDate.now()));
        assertTrue(loanResponse.getReturnDate().isBefore(LocalDate.now()));
    }

    @Test
    public void testLoanResponseDTOWithNullLoanHistory() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(6L, "copy111", "book005", "67323429", LocalDate.now(), LocalDate.now().plusDays(5), LoanState.Loaned, null);

        assertNull(loanResponse.getLoanHistory());
    }

    @Test
    public void testLoanResponseDTOWithInvalidState() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(7L, "copy112", "book006", "67323430", LocalDate.now(), LocalDate.now().plusDays(3), LoanState.Loaned, Collections.emptyList());

        assertEquals(LoanState.Loaned, loanResponse.getLoanState());
        assertNotNull(loanResponse.getLoanDate());
    }
}
