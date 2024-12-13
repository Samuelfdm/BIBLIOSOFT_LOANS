package edu.eci.cvds.BiblioSoftLoans.dto.Studient;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StudentDTO {
    private String id;
    private String fullName;

    public StudentDTO(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public StudentDTO() {
    }
}