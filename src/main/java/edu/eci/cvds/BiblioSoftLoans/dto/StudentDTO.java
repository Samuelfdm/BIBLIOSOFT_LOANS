package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StudentDTO {
    private Long id;
    private String fullName;
}