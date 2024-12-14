package edu.eci.cvds.BiblioSoftLoans.client;
/*
import edu.eci.cvds.BiblioSoftLoans.dto.Student.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private StudentServiceClient studentServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        String baseUrl = "http://localhost:8080";  // Aquí puedes poner la URL del servicio.
        studentServiceClient = new StudentServiceClient(baseUrl);

        // Configuración de WebClient mock
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void testGetStudentById() {
        String studentId = "12345";
        String token = "someToken";

        // Crear un mock de respuesta
        StudentDTO studentDTO = new StudentDTO(studentId, "John Doe");
        when(responseSpec.bodyToMono(StudentDTO.class)).thenReturn(Mono.just(studentDTO));

        // Ejecutar el método
        Mono<StudentDTO> result = studentServiceClient.getStudentById(studentId, token);

        // Validar el resultado
        StudentDTO resultStudent = result.block();
        assertNotNull(resultStudent);
        assertEquals(studentId, resultStudent.getId());
        assertEquals("John Doe", resultStudent.getFullName());
    }

    @Test
    void testGetStudentById_notFound() {
        String studentId = "nonExistentId";
        String token = "someToken";

        // Simular que no se encuentra el estudiante
        when(responseSpec.bodyToMono(StudentDTO.class)).thenReturn(Mono.empty());

        // Ejecutar el método
        Mono<StudentDTO> result = studentServiceClient.getStudentById(studentId, token);

        // Validar que el resultado es vacío
        assertNull(result.block());
    }

    @Test
    void testImprimi() {
        // Crear el mock de respuesta para el método imprimi()
        String mockResponse = "Hello from imprimi!";
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(mockResponse));

        // Ejecutar el método
        Mono<String> result = studentServiceClient.imprimi();

        // Validar el resultado
        assertNotNull(result.block());
        assertEquals(mockResponse, result.block());
    }
}
*/