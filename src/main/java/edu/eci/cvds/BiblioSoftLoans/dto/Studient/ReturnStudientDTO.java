package edu.eci.cvds.BiblioSoftLoans.dto.Studient;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReturnStudientDTO  {
    private String fullName;

    public ReturnStudientDTO( String fullName) {
        this.fullName = fullName;
    }

    public ReturnStudientDTO() {
    }
}
