package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.ExpiredLoansDTO;
import edu.eci.cvds.BiblioSoftLoans.exception.NotificationException;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
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

    public void notificationForLoan(Loan loan) {
        try {
            webClient.post()
                    .uri("/notifications/loan-made")
                    .bodyValue(loan)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            throw new NotificationException(NotificationException.ErrorType.CONNECTION_FAILED, e);
        }

    }
}