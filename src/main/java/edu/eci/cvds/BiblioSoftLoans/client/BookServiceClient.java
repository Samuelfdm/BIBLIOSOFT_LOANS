package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.ApiResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.BookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.CopyUpdateDTO;
import edu.eci.cvds.BiblioSoftLoans.exception.BookApiException;
import edu.eci.cvds.BiblioSoftLoans.exception.BookLoanException;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;


import java.util.HashMap;
import java.util.Map;

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
                    // Comprobamos que apiResponseDTO no sea nulo y tiene datos en el body
                    if (apiResponseDTO != null && apiResponseDTO.getBody() != null && !apiResponseDTO.getBody().isEmpty()) {
                        // Obtenemos el primer elemento de la lista body
                        BookDTO bookDTO = apiResponseDTO.getBody().get(0);
                        return Mono.just(bookDTO);
                    } else {
                        return Mono.error(new RuntimeException("No book found in the response"));
                    }
                });
    }

    // Actualizar disponibilidad de un ejemplar
    public void updateCopy(String copyId, CopyDTO.CopyDispo copyDispo, CopyState state) {
        // Crear un objeto CopyUpdateDTO con los datos necesarios
        CopyUpdateDTO update = new CopyUpdateDTO(copyId, copyDispo, state);

        try {
            // Enviar la solicitud PATCH al endpoint correspondiente
            webClient.patch()
                    .uri("/CopyModule/update") // URL del endpoint
                    .bodyValue(update) // El objeto que contiene los datos a actualizar
                    .retrieve()
                    .bodyToMono(Void.class) // Esperar la respuesta vacía
                    .block(); // Bloquear hasta recibir la respuesta
        } catch (Exception e) {
            // Capturar cualquier excepción y lanzar una personalizada
            throw new BookApiException(BookApiException.ErrorType.UPDATE_FAILED, e);
        }
    }

}
