package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.Book.ApiResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.BookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyUpdateDTO;
import edu.eci.cvds.BiblioSoftLoans.exception.BookApiException;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookServiceClient {

    private final WebClient webClient;
    @Autowired


    public BookServiceClient(@Value("${book.service.url}") String bookServiceUrl) {
        // Configurar el tamaño máximo del búfer
        int bufferSize = 10 * 1024 * 1024; // 10 MB
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(bufferSize))
                .build();

        this.webClient = WebClient.builder()
                .baseUrl(bookServiceUrl)
                .exchangeStrategies(strategies)
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


    //traer el cuerpo de todos los libros para despues filtar
    public Mono<List<BookDTO>> getAllBooks() {
        return webClient.get()
                .uri("/BookModule/getAllBooks")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<BookDTO>>() {})
                .flatMap(apiResponseDTO -> {
                    if (apiResponseDTO != null && apiResponseDTO.getBody() != null) {
                        return Mono.just(apiResponseDTO.getBody());
                    } else {
                        return Mono.error(new RuntimeException("No books found in the response"));
                    }
                });
    }


    //traer copia por codigo de barras
    public Mono<ApiResponseDTO<CopyDTO>> getBodyCopiesBycodebar(String code) {
        return webClient.get()
                .uri("/CopyModule/findByBarCode?barCode=" + code)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CopyDTO>>() {});
    }


    //traer el cuerpo de todas las copias de un libro
    public Mono<ApiResponseDTO<CopyDTO>> getBodyCopiesByBookId(String BookID) {
        return webClient.get()
                .uri("/BookModule/getCopies?bookId=" + BookID)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CopyDTO>>() {});
    }

    //filtra por titulo los libros
    public Mono<BookDTO> getBookByTitle(String title) {
        return getAllBooks()
                .flatMapMany(Flux::fromIterable)
                .filter(bookDTO -> title.equalsIgnoreCase(bookDTO.getTitle()))
                .next()
                .switchIfEmpty(Mono.error(new RuntimeException("No book found with title: " + title)));
    }

    //filtra por autor los libros
    public Mono<BookDTO> getBookByAuthor(String author) {
        return getAllBooks()
                .flatMapMany(Flux::fromIterable)
                .filter(bookDTO -> author.equalsIgnoreCase(bookDTO.getAuthor()))
                .next()
                .switchIfEmpty(Mono.error(new RuntimeException("No book found with author: " + author)));
    }


    //trae todas las copias de el libro una vez encontrado por titulo
    public Mono<List<CopyDTO>> getCopiesByTitle(String title) {
        return getBookByTitle(title)
                .flatMap(book -> getCopiesByBookId(book.getBookId()));
    }

    //trae todas las copias de el libro una vez encontrado por autor
    public Mono<List<CopyDTO>> getCopiesByAuthor(String author) {
        return getBookByAuthor(author)
                .flatMap(book -> getCopiesByBookId(book.getBookId()));
    }


    //trae todas las copias de un libro
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

    public Mono<List<CopyDTO>> getCopiesByBookId(String bookId) {
        return webClient.get()
                .uri("/BookModule/getCopies?bookId=" + bookId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CopyDTO>>() {})
                .flatMap(apiResponseDTO -> {
                    if (apiResponseDTO != null && apiResponseDTO.getBody() != null) {
                        return Mono.just(apiResponseDTO.getBody());
                    } else {
                        return Mono.error(new RuntimeException("No copies found for book ID: " + bookId));
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



    public Mono<CopyDTO> getCopiesBycodebar(String code) {
        return getBodyCopiesBycodebar(code)
                .flatMap(apiResponseDTO -> {
                    if (apiResponseDTO != null && apiResponseDTO.getBody() != null && !apiResponseDTO.getBody().isEmpty()) {
                        CopyDTO copyDTO = apiResponseDTO.getBody().get(0);
                        return Mono.just(copyDTO);
                    } else {
                        return Mono.error(new RuntimeException("No book found in the response"));
                    }
                });
    }

    // Actualizar disponibilidad de un ejemplar
    public void updateCopy(String copyId, CopyDTO.CopyDispo copyDispo, String state) {
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
