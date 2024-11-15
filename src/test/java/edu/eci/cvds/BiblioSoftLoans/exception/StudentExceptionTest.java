package edu.eci.cvds.BiblioSoftLoans.exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentExceptionTest {

    private StudentException studentException;

    @BeforeEach
    public void setUp() {
        studentException = new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND);
    }

    @Test
    public void testConstructorWithErrorType() {
        assertNotNull(studentException);
        assertEquals(StudentException.ErrorType.STUDENT_NOT_FOUND, studentException.getErrorType());
    }

    @Test
    public void testUserMessageForStudentNotFound() {
        studentException = new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND);
        assertEquals("El estudiante no fue encontrado.", studentException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForStudentNotFound() {
        studentException = new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND);
        assertEquals("Intento de préstamo fallido. Estudiante no encontrado.", studentException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForInvalidStudent() {
        studentException = new StudentException(StudentException.ErrorType.INVALID_STUDENT);
        assertEquals("El estudiante no es válido para este préstamo.", studentException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForInvalidStudent() {
        studentException = new StudentException(StudentException.ErrorType.INVALID_STUDENT);
        assertEquals("Intento de préstamo fallido. Estudiante no válido.", studentException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForBookAlreadyIssued() {
        studentException = new StudentException(StudentException.ErrorType.BOOK_ALREADY_ISSUED);
        assertEquals("El estudiante ya tiene este libro.", studentException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForBookAlreadyIssued() {
        studentException = new StudentException(StudentException.ErrorType.BOOK_ALREADY_ISSUED);
        assertEquals("Intento de préstamo fallido. El estudiante ya posee este libro.", studentException.getDeveloperMessage());
    }

    @Test
    public void testExceptionWithCause() {
        Throwable cause = new Throwable("Cause of the error");
        studentException = new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND, cause);
        assertNotNull(studentException.getCause());
        assertEquals("Cause of the error", studentException.getCause().getMessage());
    }

    @Test
    public void testErrorType() {
        studentException = new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND);
        assertEquals(StudentException.ErrorType.STUDENT_NOT_FOUND, studentException.getErrorType());
    }

    @Test
    public void testDefaultMessage() {
        StudentException exception = new StudentException(StudentException.ErrorType.valueOf("STUDENT_NOT_FOUND"));

        assertEquals("El estudiante no fue encontrado.", exception.getUserMessage());
        assertEquals("Intento de préstamo fallido. Estudiante no encontrado.", exception.getDeveloperMessage());
    }

    @Test
    public void testStudentNotFoundMessage() {
        StudentException exception = new StudentException(StudentException.ErrorType.STUDENT_NOT_FOUND);
        assertEquals("El estudiante no fue encontrado.", exception.getUserMessage());
        assertEquals("Intento de préstamo fallido. Estudiante no encontrado.", exception.getDeveloperMessage());
    }

    @Test
    public void testInvalidStudentMessage() {
        StudentException exception = new StudentException(StudentException.ErrorType.INVALID_STUDENT);
        assertEquals("El estudiante no es válido para este préstamo.", exception.getUserMessage());
        assertEquals("Intento de préstamo fallido. Estudiante no válido.", exception.getDeveloperMessage());
    }

    @Test
    public void testBookAlreadyIssuedMessage() {
        StudentException exception = new StudentException(StudentException.ErrorType.BOOK_ALREADY_ISSUED);
        assertEquals("El estudiante ya tiene este libro.", exception.getUserMessage());
        assertEquals("Intento de préstamo fallido. El estudiante ya posee este libro.", exception.getDeveloperMessage());
    }
}