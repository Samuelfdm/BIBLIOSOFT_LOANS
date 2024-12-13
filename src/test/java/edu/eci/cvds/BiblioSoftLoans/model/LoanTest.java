package edu.eci.cvds.BiblioSoftLoans.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    private Loan loan;
    private LoanHistory loanHistory1;
    private LoanHistory loanHistory2;

    @BeforeEach
    void setUp() {
        loan = new Loan(
                "673bb7ad521c2f08810d4f7c",
                "Maria Perez",
                "COPY123",
                "BOOK000",
                "libro de la selva",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                LoanState.Loaned
        );
        loan.setBookId("BOOK456");
        loan.setLoanHistory(new ArrayList<>());
        loanHistory1 = new LoanHistory(LocalDate.now(), "BUENO");
        loanHistory2 = new LoanHistory(LocalDate.now().minusDays(2), "Damaged");
    }

    @Test
    void testLoanCreation() {
        assertEquals("673bb7ad521c2f08810d4f7c", loan.getStudentId());
        assertEquals("Maria Perez", loan.getStudentName());
        assertEquals("COPY123", loan.getCopyId());
        assertEquals("BOOK456", loan.getBookId());
        assertEquals(LocalDate.now(), loan.getLoanDate());
        assertEquals(LocalDate.now().plusDays(7), loan.getMaxReturnDate());
        assertEquals(LoanState.Loaned, loan.getLoanState());
        assertTrue(loan.getLoanHistory().isEmpty());
    }

    @Test
    void testAddHistory() {
        loan.addHistory(loanHistory1);
        assertEquals(1, loan.getLoanHistory().size());
        assertEquals(loan, loanHistory1.getLoan());
    }

    @Test
    void testRemoveHistory() {
        loan.addHistory(loanHistory1);
        loan.removeHistory(loanHistory1);
        assertTrue(loan.getLoanHistory().isEmpty());
        assertNull(loanHistory1.getLoan());
    }

    @Test
    void testSettersAndGetters() {
        loan.setStudentId("NEWID");
        loan.setCopyId("COPY789");
        loan.setBookId("BOOK987");
        loan.setLoanDate(LocalDate.now().minusDays(5));
        loan.setMaxReturnDate(LocalDate.now().plusDays(10));
        loan.setLoanState(LoanState.Returned);

        assertEquals("NEWID", loan.getStudentId());
        assertEquals("COPY789", loan.getCopyId());
        assertEquals("BOOK987", loan.getBookId());
        assertEquals(LocalDate.now().minusDays(5), loan.getLoanDate());
        assertEquals(LocalDate.now().plusDays(10), loan.getMaxReturnDate());
        assertEquals(LoanState.Returned, loan.getLoanState());
    }

    @Test
    void testMultipleLoanHistories() {
        loan.addHistory(loanHistory1);
        loan.addHistory(loanHistory2);
        assertEquals(2, loan.getLoanHistory().size());
    }

    @Test
    void testHistoryDetailsAfterAddition() {
        loan.addHistory(loanHistory1);
        assertEquals(LocalDate.now(), loan.getLoanHistory().get(0).getRecordDate());
        assertEquals("BUENO", loan.getLoanHistory().get(0).getCopyState());
    }

    @Test
    void testLoanStateTransition() {
        loan.setLoanState(LoanState.Returned);
        assertEquals(LoanState.Returned, loan.getLoanState());
    }

    @Test
    void testLoanReturnDateUpdate() {
        loan.setMaxReturnDate(LocalDate.now().plusDays(14));
        assertEquals(LocalDate.now().plusDays(14), loan.getMaxReturnDate());
    }
}
