package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.Studient.StudentDTO;
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

    public Mono<String> getStudentById(String studentId, String token) {
        return webClient.post()
                .uri("/usuario/buscandoEstudiantePorId")
                .header("Authorization", "Bearer " + token)
                .bodyValue("{\"idEstudiante\":\"" + studentId + "\"}")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(name -> {
                    System.out.println("Nombre del Estudiante: " + name);
                });
    }


}