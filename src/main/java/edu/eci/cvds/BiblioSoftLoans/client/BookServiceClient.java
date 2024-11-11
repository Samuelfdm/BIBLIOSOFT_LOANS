package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.CopyDTO;
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

    public Mono<CopyDTO> getBookCopyById(String copyId) {
        return webClient.get()
                .uri("/books/copies/{copyId}", copyId)
                .retrieve()
                .bodyToMono(CopyDTO.class);
    }
}