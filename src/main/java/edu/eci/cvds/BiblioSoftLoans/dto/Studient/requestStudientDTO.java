package edu.eci.cvds.BiblioSoftLoans.dto.Studient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class requestStudientDTO {
    private String idEstudiante;

    public requestStudientDTO(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

}
