package edu.eci.cvds.BiblioSoftLoans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId; // ID del estudiante (referencia externa)

    @Column(name = "copy_id", nullable = false)
    private String copyId; // Código del ejemplar/copia (referencia externa)

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_state", nullable = false)
    private LoanState loanState;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "loan_id")
    private List<LoanHistory> loanHistory;

    public Loan(Long studentId, String copyId, LocalDate loanDate, LocalDate returnDate, LoanState loanState) {
        this.studentId = studentId;
        this.copyId = copyId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.loanState = loanState;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getCopyId() {
        return copyId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LoanState getLoanState() {
        return loanState;
    }

    public List<LoanHistory> getLoanHistory() {
        return loanHistory;
    }

    public void addHistory(LoanHistory loanHistory) {
        this.loanHistory.add(loanHistory);
        loanHistory.setLoan(this);
    }

    public void removeHistory(LoanHistory loanHistory) {
        this.loanHistory.remove(loanHistory);
        loanHistory.setLoan(null);
    }
}