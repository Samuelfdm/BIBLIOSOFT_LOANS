package edu.eci.cvds.BiblioSoftLoans.dto.Student;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReturnStudentDTO {
    private String fullName;

    public ReturnStudentDTO(String fullName) {
        this.fullName = fullName;
    }

    public ReturnStudentDTO() {
    }
}
