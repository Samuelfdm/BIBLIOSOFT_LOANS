package edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanRequestDTO {
    private String studentId;
    private String copyId;
    private String token;
}
