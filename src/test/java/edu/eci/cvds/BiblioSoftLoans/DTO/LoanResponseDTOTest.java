package edu.eci.cvds.BiblioSoftLoans.DTO;
import edu.eci.cvds.BiblioSoftLoans.dto.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import edu.eci.cvds.BiblioSoftLoans.dto.LoanResponseDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class LoanResponseDTOTest {

    @Test
    public void testLoanResponseDTO() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(1L, "copy101", 101L, LocalDate.now(), LocalDate.now().plusDays(7), LoanState.Loaned, Collections.emptyList());

        assertNotNull(loanResponse);
        assertEquals(Long.valueOf(1), loanResponse.getLoanId());
        assertEquals("copy101", loanResponse.getCopyId());
        assertEquals(Long.valueOf(101), loanResponse.getStudentId());
        assertEquals(LocalDate.now(), loanResponse.getLoanDate());
        assertEquals(LocalDate.now().plusDays(7), loanResponse.getReturnDate());
        assertEquals(LoanState.Loaned, loanResponse.getLoanState());
    }

    @Test
    public void testLoanResponseDTOWithEmptyLoanHistory() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(8L, "copy105", 108L, LocalDate.now(), LocalDate.now().plusDays(10), LoanState.Loaned, Collections.emptyList());

        assertNotNull(loanResponse);
        assertTrue(loanResponse.getLoanHistory().isEmpty());
    }


    @Test
    public void testLoanResponseDTOReturnDateBeforeLoanDate() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(10L, "copy107", 110L, LocalDate.now().plusDays(1), LocalDate.now(), LoanState.Loaned, Collections.emptyList());

        assertTrue(loanResponse.getReturnDate().isBefore(loanResponse.getLoanDate()));
    }

    @Test
    public void testLoanResponseDTOReturnDateEqualsLoanDate() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(11L, "copy108", 111L, LocalDate.now(), LocalDate.now(), LoanState.Loaned, Collections.emptyList());

        assertEquals(loanResponse.getLoanDate(), loanResponse.getReturnDate());
    }

    @Test
    public void testLoanResponseDTOLoanStateOverdue() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(13L, "copy110", 113L, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), LoanState.Loaned, Collections.emptyList());

        assertEquals(LoanState.Loaned, loanResponse.getLoanState());
        assertTrue(loanResponse.getLoanDate().isBefore(LocalDate.now()));
        assertTrue(loanResponse.getReturnDate().isBefore(LocalDate.now()));
    }

    @Test
    public void testLoanResponseDTOWithNullLoanHistory() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(14L, "copy111", 114L, LocalDate.now(), LocalDate.now().plusDays(5), LoanState.Loaned, null);

        assertNull(loanResponse.getLoanHistory());
    }

    @Test
    public void testLoanResponseDTOWithInvalidState() {
        LoanResponseDTO loanResponse = new LoanResponseDTO(15L, "copy112", 115L, LocalDate.now(), LocalDate.now().plusDays(3), LoanState.Loaned, Collections.emptyList());

        assertEquals(LoanState.Loaned, loanResponse.getLoanState());
        assertNotNull(loanResponse.getLoanDate());
    }
}
