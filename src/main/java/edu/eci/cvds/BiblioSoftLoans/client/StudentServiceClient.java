package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceClient {

    private final WebClient webClient;
    //El modulo de estudiantes aun no nos ha mostrado cual es su URI y por eso ponemos otra de ejemplo http://localhost:8082/api/students
    public StudentServiceClient(@Value("${student.service.url}") String studentServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(studentServiceUrl)
                .build();
    }
    //Aqui falta corregir las URI por las reales que aun no nos mandan
    public Mono<StudentDTO> getStudentById(Long studentId) {
        return webClient.get()
                .uri("/students/{studentId}", studentId)
                .retrieve()
                .bodyToMono(StudentDTO.class);
    }
}