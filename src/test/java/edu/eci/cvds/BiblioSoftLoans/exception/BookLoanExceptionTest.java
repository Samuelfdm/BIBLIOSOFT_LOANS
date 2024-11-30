package edu.eci.cvds.BiblioSoftLoans.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookLoanExceptionTest {

    private BookLoanException loanException;

    @BeforeEach
    public void setUp() {
        loanException = new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
    }

    @Test
    public void testConstructorWithErrorType() {
        assertNotNull(loanException);
        assertEquals(BookLoanException.ErrorType.ALREADY_BORROWED, loanException.getErrorType());
    }

    @Test
    public void testUserMessageForAlreadyBorrowed() {
        loanException = new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
        assertEquals("El libro ya está prestado.", loanException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForAlreadyBorrowed() {
        loanException = new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
        assertEquals("Intento de préstamo fallido. El libro ya está prestado.", loanException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForNotAvailable() {
        loanException = new BookLoanException(BookLoanException.ErrorType.NOT_AVAILABLE);
        assertEquals("El libro no está disponible para préstamo en este momento.", loanException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForNotAvailable() {
        loanException = new BookLoanException(BookLoanException.ErrorType.NOT_AVAILABLE);
        assertEquals("Intento de préstamo fallido. El libro no está disponible.", loanException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForInvalidBook() {
        loanException = new BookLoanException(BookLoanException.ErrorType.INVALID_BOOK);
        assertEquals("El código del libro no es válido.", loanException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForInvalidBook() {
        loanException = new BookLoanException(BookLoanException.ErrorType.INVALID_BOOK);
        assertEquals("Intento de préstamo fallido. Código de libro inválido.", loanException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForNoLoanFound() {
        loanException = new BookLoanException(BookLoanException.ErrorType.NO_LOAN_FOUND);
        assertEquals("El libro no tiene un préstamo activo registrado.", loanException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForNoLoanFound() {
        loanException = new BookLoanException(BookLoanException.ErrorType.NO_LOAN_FOUND);
        assertEquals("Intento de devolución fallido. El libro no está registrado como prestado.", loanException.getDeveloperMessage());
    }

    @Test
    public void testExceptionWithCause() {
        Throwable cause = new Throwable("Cause of the error");
        loanException = new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED, cause);
        assertNotNull(loanException.getCause());
        assertEquals("Cause of the error", loanException.getCause().getMessage());
    }

    @Test
    public void testErrorType() {
        loanException = new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
        assertEquals(BookLoanException.ErrorType.ALREADY_BORROWED, loanException.getErrorType());
    }
}