package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceClient {

    private final WebClient webClient;

    public StudentServiceClient(@Value("${student.service.url}") String studentServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(studentServiceUrl)
                .build();
    }

    //NOS FALTA CORREGIR LAS URL DEL MODULO DE ESTUDIANTES, LOS ENDPOINTS...

    public Mono<StudentDTO> getStudentById(Long studentId) {
        return webClient.get()
                .uri("/students/{studentId}", studentId)
                .retrieve()
                .bodyToMono(StudentDTO.class);
    }
}