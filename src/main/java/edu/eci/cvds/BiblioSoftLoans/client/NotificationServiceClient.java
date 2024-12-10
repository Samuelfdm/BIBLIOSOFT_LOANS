package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.ExpiredLoansDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NotificationServiceClient {
    private final WebClient webClient;

    public NotificationServiceClient(@Value("${notification.service.url}") String notificationServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(notificationServiceUrl)
                .build();
    }

    public void notificateExpiredLoans(ExpiredLoansDTO expiredLoansDTO) {
        webClient.post()
                .uri("/expiredLoans")
                .bodyValue(expiredLoansDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // Sincronizar para asegurar la entrega
    }
}
