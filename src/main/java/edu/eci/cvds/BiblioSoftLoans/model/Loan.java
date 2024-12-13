package edu.eci.cvds.BiblioSoftLoans.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "copy_id", nullable = false)
    private String copyId;

    @Column(name = "book_id", nullable = false)
    private String bookId;

    @Column(name = "name_book", nullable = true)
    private String nameBook;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "max_return_date", nullable = false)
    private LocalDate maxReturnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_state", nullable = false)
    private LoanState loanState;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LoanHistory> loanHistory;

    public Loan(String studentId, String studentName, String copyId, String bookId, String nameBook, LocalDate loanDate, LocalDate maxReturnDate, LoanState loanState) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.copyId = copyId;
        this.bookId = bookId;
        this.nameBook = nameBook;
        this.loanDate = loanDate;
        this.maxReturnDate = maxReturnDate;
        this.loanState = loanState;
        this.loanHistory = new ArrayList<>();
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
