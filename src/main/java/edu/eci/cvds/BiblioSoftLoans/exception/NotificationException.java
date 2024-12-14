package edu.eci.cvds.BiblioSoftLoans.exception;

public class NotificationException extends RuntimeException {

  private final String userMessage;
  private final String developerMessage;
  private final ErrorType errorType;

  public NotificationException(ErrorType errorType) {
    super();
    this.errorType = errorType;
    this.userMessage = setUserMessage(errorType);
    this.developerMessage = setDeveloperMessage(errorType);
  }

  public NotificationException(ErrorType errorType, Throwable cause) {
    super(cause);
    this.errorType = errorType;
    this.userMessage = setUserMessage(errorType);
    this.developerMessage = setDeveloperMessage(errorType);
  }

  private String setUserMessage(ErrorType errorType) {
    switch (errorType) {
      case CONNECTION_FAILED:
        return "No se pudo conectar con el servicio de notificaciones.";
      case INVALID_NOTIFICATION_DATA:
        return "Los datos de la notificación no son válidos.";
      case NOTIFICATION_SERVICE_ERROR:
        return "El servicio de notificaciones devolvió un error.";
      default:
        return "Ocurrió un error inesperado al enviar la notificación.";
    }
  }

  private String setDeveloperMessage(ErrorType errorType) {
    switch (errorType) {
      case CONNECTION_FAILED:
        return "Fallo en la conexión con el servicio de notificaciones. Verificar la URL y disponibilidad del servicio.";
      case INVALID_NOTIFICATION_DATA:
        return "Los datos enviados para la notificación no cumplen con los requisitos esperados.";
      case NOTIFICATION_SERVICE_ERROR:
        return "El servicio de notificaciones devolvió un código de error inesperado.";
      default:
        return "Error desconocido al intentar enviar la notificación.";
    }
  }

  public String getUserMessage() {
    return userMessage;
  }

  public String getDeveloperMessage() {
    return developerMessage;
  }

  public ErrorType getErrorType() {
    return errorType;
  }

  public enum ErrorType {
    CONNECTION_FAILED,
    INVALID_NOTIFICATION_DATA,
    NOTIFICATION_SERVICE_ERROR
  }
}
