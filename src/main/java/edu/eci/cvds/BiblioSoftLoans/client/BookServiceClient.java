package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.ApiResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.BookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyUpdateDTO;
import edu.eci.cvds.BiblioSoftLoans.exception.BookApiException;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookServiceClient {

    private final WebClient webClient;
    @Autowired


    public BookServiceClient(@Value("${book.service.url}") String bookServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(bookServiceUrl)
                .build();
    }


    public Mono<ApiResponseDTO<CopyDTO>> getBodyCopy(String copyId) {
        return webClient.get()
                .uri("/CopyModule/getCopy?id=" + copyId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CopyDTO>>() {});
    }

    public Mono<ApiResponseDTO<BookDTO>> getBodyBook(String copyId) {
        return webClient.get()
                .uri("/BookModule/getBook?id=" + copyId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<BookDTO>>() {});
    }

    public Mono<CopyDTO> getBookCopyById(String copyId) {
        return getBodyCopy(copyId)
                .flatMap(apiResponseDTO -> {
                    if (apiResponseDTO != null && apiResponseDTO.getBody() != null && !apiResponseDTO.getBody().isEmpty()) {
                        CopyDTO copyDTO = apiResponseDTO.getBody().get(0);
                        return Mono.just(copyDTO);
                    } else {
                        return Mono.error(new RuntimeException("No CopyDTO found in the response"));
                    }
                });
    }


    public Mono<BookDTO> getBookById(String bookId) {
        return getBodyBook(bookId)
                .flatMap(apiResponseDTO -> {
                    if (apiResponseDTO != null && apiResponseDTO.getBody() != null && !apiResponseDTO.getBody().isEmpty()) {
                        BookDTO bookDTO = apiResponseDTO.getBody().get(0);
                        return Mono.just(bookDTO);
                    } else {
                        return Mono.error(new RuntimeException("No book found in the response"));
                    }
                });
    }

    // Actualizar disponibilidad de un ejemplar
    public void updateCopy(String copyId, CopyDTO.CopyDispo copyDispo, CopyState state) {
        CopyUpdateDTO update = new CopyUpdateDTO(copyId, copyDispo, state);

        try {
            webClient.patch()
                    .uri("/CopyModule/update") // URL del endpoint
                    .bodyValue(update)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            // Capturar cualquier excepción y lanzar una personalizada
            throw new BookApiException(BookApiException.ErrorType.UPDATE_FAILED, e);
        }
    }

}
