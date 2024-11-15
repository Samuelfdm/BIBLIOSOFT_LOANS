package edu.eci.cvds.BiblioSoftLoans.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LoanHistoryTest {

    private LoanHistory loanHistory;
    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan(1L, "COPY123", LocalDate.now(), LocalDate.now().plusDays(7), LoanState.Loaned);
        loanHistory = new LoanHistory(LocalDate.now(), CopyState.Good);
        loanHistory.setLoan(loan);
    }

    @Test
    void testLoanHistoryCreation() {
        assertEquals(LocalDate.now(), loanHistory.getDate());
        assertEquals(CopyState.Good, loanHistory.getCopyState());
        assertEquals(loan, loanHistory.getLoan());
    }

    @Test
    void testSetCopyState() {
        loanHistory.setCopyState(CopyState.Damaged);
        assertEquals(CopyState.Damaged, loanHistory.getCopyState());
    }

    @Test
    void testSetDate() {
        LocalDate newDate = LocalDate.now().minusDays(3);
        loanHistory.setDate(newDate);
        assertEquals(newDate, loanHistory.getDate());
    }

    @Test
    void testSetLoan() {
        Loan newLoan = new Loan(2L, "COPY789", LocalDate.now(), LocalDate.now().plusDays(10), LoanState.Loaned);
        loanHistory.setLoan(newLoan);
        assertEquals(newLoan, loanHistory.getLoan());
    }

    @Test
    void testDefaultConstructor() {
        LoanHistory emptyLoanHistory = new LoanHistory();
        assertNull(emptyLoanHistory.getDate());
        assertNull(emptyLoanHistory.getCopyState());
        assertNull(emptyLoanHistory.getLoan());
    }

    @Test
    void testLoanHistoryDateNotNull() {
        assertNotNull(loanHistory.getDate());
    }

    @Test
    void testLoanHistoryCopyStateNotNull() {
        assertNotNull(loanHistory.getCopyState());
    }

    @Test
    void testLoanHistoryLoanNotNullAfterSet() {
        Loan newLoan = new Loan(2L, "COPY999", LocalDate.now(), LocalDate.now().plusDays(5), LoanState.Loaned);
        loanHistory.setLoan(newLoan);
        assertNotNull(loanHistory.getLoan());
    }

    @Test
    void testDateInPast() {
        loanHistory.setDate(LocalDate.now().minusDays(30));
        assertTrue(loanHistory.getDate().isBefore(LocalDate.now()));
    }

    @Test
    void testDateInFuture() {
        loanHistory.setDate(LocalDate.now().plusDays(15));
        assertTrue(loanHistory.getDate().isAfter(LocalDate.now()));
    }

    @Test
    void testDateConsistencyOnMultipleChanges() {
        LocalDate newDate1 = LocalDate.now().minusDays(2);
        LocalDate newDate2 = LocalDate.now().plusDays(5);
        loanHistory.setDate(newDate1);
        assertEquals(newDate1, loanHistory.getDate());

        loanHistory.setDate(newDate2);
        assertEquals(newDate2, loanHistory.getDate());
    }

    @Test
    void testLoanAssociationIntegrity() {
        Loan anotherLoan = new Loan(3L, "COPY555", LocalDate.now(), LocalDate.now().plusDays(7), LoanState.Loaned);
        loanHistory.setLoan(anotherLoan);
        assertEquals(anotherLoan, loanHistory.getLoan());
    }
}
