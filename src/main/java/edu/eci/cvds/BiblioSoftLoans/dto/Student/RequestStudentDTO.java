package edu.eci.cvds.BiblioSoftLoans.dto.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RequestStudentDTO {
    private String idEstudiante;

    public RequestStudentDTO(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}