package edu.eci.cvds.BiblioSoftLoans.dto;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanRequestDTOTest {

    @Test
    public void testLoanRequestDTOSettersAndGetters() {
        LoanRequestDTO loanRequest = new LoanRequestDTO("67323424", "copy123");

        assertEquals(Long.valueOf(1), loanRequest.getStudentId());
        assertEquals("copy123", loanRequest.getCopyId());
    }

    @Test
    public void testLoanRequestDTOConstructor() {
        LoanRequestDTO loanRequest = new LoanRequestDTO("67323424", "copy456");

        assertNotNull(loanRequest);
        assertEquals(Long.valueOf(2), loanRequest.getStudentId());
        assertEquals("copy456", loanRequest.getCopyId());
    }

    @Test
    public void testLoanRequestDTOEquality() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO("67323424", "copy789");
        LoanRequestDTO loanRequest2 = new LoanRequestDTO("67323424", "copy789");

        assertEquals(loanRequest1, loanRequest2);
    }

    @Test
    public void testLoanRequestDTONotEqual() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO("67323424", "copy101");
        LoanRequestDTO loanRequest2 = new LoanRequestDTO("67323424", "copy102");

        assertNotEquals(loanRequest1, loanRequest2);
    }

    @Test
    public void testLoanRequestDTOConstructorWithNull() {
        LoanRequestDTO loanRequest = new LoanRequestDTO(null, null);

        assertNull(loanRequest.getStudentId());
        assertNull(loanRequest.getCopyId());
    }

    @Test
    public void testLoanRequestDTOHashCode() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO("67323424", "copy103");
        LoanRequestDTO loanRequest2 = new LoanRequestDTO("67323424", "copy103");

        assertEquals(loanRequest1.hashCode(), loanRequest2.hashCode());
    }

    @Test
    public void testLoanRequestDTOToString() {
        LoanRequestDTO loanRequest = new LoanRequestDTO("67323424", "copy104");

        String loanRequestString = loanRequest.toString();

        assertTrue(loanRequestString.contains("studentId=7"));
        assertTrue(loanRequestString.contains("copyId=copy104"));
    }
}
