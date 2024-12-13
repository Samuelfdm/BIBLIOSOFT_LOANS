package edu.eci.cvds.BiblioSoftLoans.client;

import edu.eci.cvds.BiblioSoftLoans.dto.Studient.StudentDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Studient.requestStudientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceClient {

    private final WebClient webClient;
    private final String studentServiceUrl;

    public StudentServiceClient(@Value("${student.service.url}") String studentServiceUrl) {
        this.studentServiceUrl = studentServiceUrl;
        this.webClient = WebClient.builder()
                .baseUrl(studentServiceUrl)
                .build();
    }

    public Mono<String> getStudentById(String studentId, String token) {
        imprimi();
        System.out.println(token);
        requestStudientDTO request = new requestStudientDTO(studentId);
        return webClient.post()
                .uri("/usuario/buscandoEstudiantePorId")
                .header("Authorization", "Bearer " + token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(name -> {
                    System.out.println("Nombre del Estudiante: " + name);
                });
    }

    public void imprimi() {
        String url = studentServiceUrl + "/usuario/buscandoEstudiantePorId";
        System.out.println("Request URL: " + url);
    }


}