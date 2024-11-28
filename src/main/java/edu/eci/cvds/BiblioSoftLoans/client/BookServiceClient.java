package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookServiceClient {

    private final WebClient webClient;

    public BookServiceClient(@Value("${book.service.url}") String bookServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(bookServiceUrl)
                .build();
    }

    //NOS FALTA CORREGIR LAS URL DEL MODULO DE LIBROS, LOS ENDPOINTS...

    public Mono<CopyDTO> getBookCopyById(String copyId) {
        return webClient.get()
                .uri("/getCopy?id={copyId}", copyId)
                .retrieve()
                .bodyToMono(CopyDTO.class);
    }

    public void updateCopyDisponibility(String copyId, LoanState loanState) {
        // Convertimos el estado de LoanState a una cadena compatible CopyDispo con el servicio de libros
        String disponibility = loanState == LoanState.Loaned ? "BORROWED" : "AVAILABLE";

        // Realizamos la solicitud PUT al servicio de libros
        webClient.put()
                .uri("/updateBook/?id={copyId}/disponibility", copyId) // AJUSTAR LA URI SEGUN EL MODULO DE LIBROS
                .bodyValue(disponibility)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> {
                    // Manejo de errores, como registrar el error y devolver un Mono vacío para evitar que falle la transacción
                    System.err.println("Error updating copy disponibility: " + e.getMessage());
                    return Mono.empty();
                });
    }

    public void updateCopyState(String copyId, CopyState copyState) {
        // Realizamos la solicitud PUT al servicio de libros
        webClient.put()
                .uri("/copies/{copyId}/state", copyId) // AJUSTAR LA URI SEGUN EL MODULO DE LIBROS
                .bodyValue(copyState)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> {
                    // Manejo de errores, como registrar el error y devolver un Mono vacío para evitar que falle la transacción
                    System.err.println("Error updating copy disponibility: " + e.getMessage());
                    return Mono.empty();
                });
    }
}