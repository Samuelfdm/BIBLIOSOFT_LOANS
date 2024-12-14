package edu.eci.cvds.BiblioSoftLoans.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationExceptionTest {

    @Test
    void testNotificationExceptionConnectionFailed() {
        NotificationException exception = new NotificationException(NotificationException.ErrorType.CONNECTION_FAILED);
        assertEquals("No se pudo conectar con el servicio de notificaciones.", exception.getUserMessage());
        assertEquals("Fallo en la conexión con el servicio de notificaciones. Verificar la URL y disponibilidad del servicio.", exception.getDeveloperMessage());
        assertEquals(NotificationException.ErrorType.CONNECTION_FAILED, exception.getErrorType());
    }

    @Test
    void testNotificationExceptionInvalidNotificationData() {
        NotificationException exception = new NotificationException(NotificationException.ErrorType.INVALID_NOTIFICATION_DATA);

        assertEquals("Los datos de la notificación no son válidos.", exception.getUserMessage());
        assertEquals("Los datos enviados para la notificación no cumplen con los requisitos esperados.", exception.getDeveloperMessage());
        assertEquals(NotificationException.ErrorType.INVALID_NOTIFICATION_DATA, exception.getErrorType());
    }

    @Test
    void testNotificationExceptionInvalidMessage() {
        NotificationException exception = new NotificationException(NotificationException.ErrorType.INVALID_MESSAGE);

        assertEquals("Ocurrió un error inesperado al enviar la notificación.", exception.getUserMessage());
        assertEquals("Error desconocido al intentar enviar la notificación.", exception.getDeveloperMessage());
        assertEquals(NotificationException.ErrorType.INVALID_MESSAGE, exception.getErrorType());
    }

    @Test
    void testNotificationExceptionNotificationServiceError() {
        NotificationException exception = new NotificationException(NotificationException.ErrorType.NOTIFICATION_SERVICE_ERROR);

        assertEquals("El servicio de notificaciones devolvió un error.", exception.getUserMessage());
        assertEquals("El servicio de notificaciones devolvió un código de error inesperado.", exception.getDeveloperMessage());
        assertEquals(NotificationException.ErrorType.NOTIFICATION_SERVICE_ERROR, exception.getErrorType());
    }

    @Test
    void testNotificationExceptionWithCause() {
        Throwable cause = new RuntimeException("Network issue");
        NotificationException exception = new NotificationException(NotificationException.ErrorType.CONNECTION_FAILED, cause);

        assertEquals("No se pudo conectar con el servicio de notificaciones.", exception.getUserMessage());
        assertEquals("Fallo en la conexión con el servicio de notificaciones. Verificar la URL y disponibilidad del servicio.", exception.getDeveloperMessage());
        assertEquals(NotificationException.ErrorType.CONNECTION_FAILED, exception.getErrorType());
        assertEquals("Network issue", exception.getCause().getMessage());
    }
}