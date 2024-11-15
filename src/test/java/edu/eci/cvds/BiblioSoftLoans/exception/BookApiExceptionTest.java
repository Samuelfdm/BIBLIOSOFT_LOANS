package edu.eci.cvds.BiblioSoftLoans.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookApiExceptionTest {

    private BookApiException apiException;

    @BeforeEach
    public void setUp() {
        apiException = new BookApiException(BookApiException.ErrorType.API_ERROR);
    }

    @Test
    public void testConstructorWithErrorType() {
        assertNotNull(apiException);
        assertEquals(BookApiException.ErrorType.API_ERROR, apiException.getErrorType());
    }

    @Test
    public void testUserMessageForApiError() {
        apiException = new BookApiException(BookApiException.ErrorType.API_ERROR);
        assertEquals("No pudimos obtener la disponibilidad del libro. Por favor, inténtelo más tarde.", apiException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForApiError() {
        apiException = new BookApiException(BookApiException.ErrorType.API_ERROR);
        assertEquals("Error al consumir la API externa.", apiException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForDataNotFound() {
        apiException = new BookApiException(BookApiException.ErrorType.DATA_NOT_FOUND);
        assertEquals("No se encontraron datos para el libro solicitado.", apiException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForDataNotFound() {
        apiException = new BookApiException(BookApiException.ErrorType.DATA_NOT_FOUND);
        assertEquals("Datos no encontrados para el libro en la API.", apiException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForUpdateFailed() {
        apiException = new BookApiException(BookApiException.ErrorType.UPDATE_FAILED);
        assertEquals("No pudimos actualizar el estado del libro. Por favor, inténtelo más tarde.", apiException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForUpdateFailed() {
        apiException = new BookApiException(BookApiException.ErrorType.UPDATE_FAILED);
        assertEquals("Error al actualizar el estado del libro en la API.", apiException.getDeveloperMessage());
    }

    @Test
    public void testUserMessageForUnknownError() {
        apiException = new BookApiException(BookApiException.ErrorType.UNKNOWN_ERROR);
        assertEquals("Hubo un error inesperado. Por favor, intente nuevamente.", apiException.getUserMessage());
    }

    @Test
    public void testDeveloperMessageForUnknownError() {
        apiException = new BookApiException(BookApiException.ErrorType.UNKNOWN_ERROR);
        assertEquals("Error desconocido.", apiException.getDeveloperMessage());
    }

    @Test
    public void testExceptionWithCause() {
        Throwable cause = new Throwable("Cause of the error");
        apiException = new BookApiException(BookApiException.ErrorType.API_ERROR, cause);
        assertNotNull(apiException.getCause());
        assertEquals("Cause of the error", apiException.getCause().getMessage());
    }

    @Test
    public void testErrorType() {
        apiException = new BookApiException(BookApiException.ErrorType.DATA_NOT_FOUND);
        assertEquals(BookApiException.ErrorType.DATA_NOT_FOUND, apiException.getErrorType());
    }

    @Test
    public void testDefaultErrorType() {
        apiException = new BookApiException(BookApiException.ErrorType.UNKNOWN_ERROR);
        assertEquals(BookApiException.ErrorType.UNKNOWN_ERROR, apiException.getErrorType());
    }

    @Test
    public void testSetMessagesForApiError() {
        apiException = new BookApiException(BookApiException.ErrorType.API_ERROR);
        assertEquals("No pudimos obtener la disponibilidad del libro. Por favor, inténtelo más tarde.", apiException.getUserMessage());
        assertEquals("Error al consumir la API externa.", apiException.getDeveloperMessage());
    }

    @Test
    public void testSetMessagesForDataNotFound() {
        apiException = new BookApiException(BookApiException.ErrorType.DATA_NOT_FOUND);
        assertEquals("No se encontraron datos para el libro solicitado.", apiException.getUserMessage());
        assertEquals("Datos no encontrados para el libro en la API.", apiException.getDeveloperMessage());
    }

    @Test
    public void testSetMessagesForUpdateFailed() {
        apiException = new BookApiException(BookApiException.ErrorType.UPDATE_FAILED);
        assertEquals("No pudimos actualizar el estado del libro. Por favor, inténtelo más tarde.", apiException.getUserMessage());
        assertEquals("Error al actualizar el estado del libro en la API.", apiException.getDeveloperMessage());
    }

    @Test
    public void testSetMessagesForUnknownError() {
        apiException = new BookApiException(BookApiException.ErrorType.UNKNOWN_ERROR);
        assertEquals("Hubo un error inesperado. Por favor, intente nuevamente.", apiException.getUserMessage());
        assertEquals("Error desconocido.", apiException.getDeveloperMessage());
    }
}