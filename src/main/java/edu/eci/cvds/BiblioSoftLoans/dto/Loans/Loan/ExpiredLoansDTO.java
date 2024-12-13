package edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpiredLoansDTO {
    private List<Loan> expiredLoans;
}