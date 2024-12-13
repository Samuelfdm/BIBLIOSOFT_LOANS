package edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoanRequestDTO {
    private String studentId;
    private String copyId;
    private String token;
}
