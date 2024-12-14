package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.Book.ApiResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.BookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceClientTest {

    private MockWebServer mockWebServer;
    private BookServiceClient bookServiceClient;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").toString();
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        bookServiceClient = new BookServiceClient(baseUrl);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void testGetBodyCopy() {
        // Crear una respuesta simulada
        CopyDTO mockCopy = new CopyDTO();
        mockCopy.setId("123");
        mockCopy.setBookId("book123");
        mockCopy.setState("GOOD");
        mockCopy.setDisponibility(CopyDTO.CopyDispo.AVAILABLE);

        ApiResponseDTO<CopyDTO> response = new ApiResponseDTO<>();
        response.setStatus("success");
        response.setMessage("Copy retrieved successfully");
        response.setBody(List.of(mockCopy));

        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                    {
                        "status": "success",
                        "message": "Copy retrieved successfully",
                        "body": [{"id": "123", "book": "book123", "state": "GOOD", "disponibility": "AVAILABLE"}]
                    }
                """)
                .addHeader("Content-Type", "application/json"));

        // Ejecutar el método
        Mono<ApiResponseDTO<CopyDTO>> result = bookServiceClient.getBodyCopy("123");

        // Validar el resultado
        ApiResponseDTO<CopyDTO> responseDTO = result.block();
        assertNotNull(responseDTO);
        assertEquals("success", responseDTO.getStatus());
        assertEquals(1, responseDTO.getBody().size());
        assertEquals("123", responseDTO.getBody().get(0).getId());
    }

    @Test
    void testGetBodyBook() {
        // Crear una respuesta simulada
        BookDTO mockBook = new BookDTO();
        mockBook.setBookId("book123");
        mockBook.setTitle("Test Book");
        mockBook.setAuthor("Author Name");

        ApiResponseDTO<BookDTO> response = new ApiResponseDTO<>();
        response.setStatus("success");
        response.setMessage("Book retrieved successfully");
        response.setBody(List.of(mockBook));

        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                    {
                        "status": "success",
                        "message": "Book retrieved successfully",
                        "body": [{"bookId": "book123", "title": "Test Book", "author": "Author Name"}]
                    }
                """)
                .addHeader("Content-Type", "application/json"));

        // Ejecutar el método
        Mono<ApiResponseDTO<BookDTO>> result = bookServiceClient.getBodyBook("book123");

        // Validar el resultado
        ApiResponseDTO<BookDTO> responseDTO = result.block();
        assertNotNull(responseDTO);
        assertEquals("success", responseDTO.getStatus());
        assertEquals(1, responseDTO.getBody().size());
        assertEquals("book123", responseDTO.getBody().get(0).getBookId());
    }

    @Test
    void testGetAllBooks() {
        // Crear una respuesta simulada
        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                    {
                        "status": "success",
                        "message": "Books retrieved successfully",
                        "body": [
                            {"bookId": "book1", "title": "Book 1", "author": "Author 1"},
                            {"bookId": "book2", "title": "Book 2", "author": "Author 2"}
                        ]
                    }
                """)
                .addHeader("Content-Type", "application/json"));

        // Ejecutar el método
        Mono<List<BookDTO>> result = bookServiceClient.getAllBooks();

        // Validar el resultado
        List<BookDTO> books = result.block();
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("book1", books.get(0).getBookId());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Author 1", books.get(0).getAuthor());
    }
}
