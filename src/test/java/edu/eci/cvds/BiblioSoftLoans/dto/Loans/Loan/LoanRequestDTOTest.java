package edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanRequestDTOTest {

    @Test
    public void testLoanRequestDTOSettersAndGetters() {
        LoanRequestDTO loanRequest = new LoanRequestDTO("67323424", "copy123","vmlmslkmv");

        assertEquals("67323424", loanRequest.getStudentId());
        assertEquals("copy123", loanRequest.getCopyId());
    }

    @Test
    public void testLoanRequestDTOConstructor() {
        LoanRequestDTO loanRequest = new LoanRequestDTO("67323424", "copy456","vmlmslkmv");

        assertNotNull(loanRequest);
        assertEquals("67323424", loanRequest.getStudentId());
        assertEquals("copy456", loanRequest.getCopyId());
    }

    @Test
    public void testLoanRequestDTOEquality() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO("67323424", "copy789","vmlmslkmv");
        LoanRequestDTO loanRequest2 = new LoanRequestDTO("67323424", "copy789","vmlmslkmv");

        assertEquals(loanRequest1, loanRequest2);
    }

    @Test
    public void testLoanRequestDTONotEqual() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO("67323424", "copy101","vmlmslkmv");
        LoanRequestDTO loanRequest2 = new LoanRequestDTO("67323424", "copy102","vmlmslkmv");

        assertNotEquals(loanRequest1, loanRequest2);
    }

    @Test
    public void testLoanRequestDTOConstructorWithNull() {
        LoanRequestDTO loanRequest = new LoanRequestDTO(null, null,null);

        assertNull(loanRequest.getStudentId());
        assertNull(loanRequest.getCopyId());
    }

    @Test
    public void testLoanRequestDTOHashCode() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO("67323424", "copy103","vmlmslkmv");
        LoanRequestDTO loanRequest2 = new LoanRequestDTO("67323424", "copy103","vmlmslkmv");

        assertEquals(loanRequest1.hashCode(), loanRequest2.hashCode());
    }
}