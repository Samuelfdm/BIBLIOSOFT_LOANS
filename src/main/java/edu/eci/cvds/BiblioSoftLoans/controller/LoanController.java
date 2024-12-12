package edu.eci.cvds.BiblioSoftLoans.controller;

import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.requestdisponibilityDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanBookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanStudient;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/loans")
public class LoanController implements ILoanController{

    @Autowired
    private LoanService loanService;

    // Endpoint para solicitar un préstamo
    @PostMapping("/requestLoan")
    public ResponseEntity<LoanResponseDTO> requestLoan(@RequestBody LoanRequestDTO loanRequest) {
        LoanResponseDTO response = loanService.requestLoan(loanRequest);
        return ResponseEntity.ok(response);
    }

    // Endpoint para devolver un libro
    @PostMapping("/returnLoan")
    public ResponseEntity<ReturnResponseDTO> returnLoan(@RequestBody ReturnRequestDTO returnRequest) {
        ReturnResponseDTO response = loanService.returnBook(returnRequest);
        return ResponseEntity.ok(response);
    }

    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos y Devueltos) de TODOS los estudiantes
    @GetMapping("/getLoans")
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    //falta
    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos o Devueltos) de TODOS los estudiantes
    @GetMapping("/getLoans/state")
    public List<Loan> getLoans(@RequestParam(value = "state") String state) {
        return loanService.getLoans(state);
    }

    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos y Devueltos) de un estudiante en especifico
    @GetMapping("/getLoans/{studentId}")
    public List<Loan> getLoansStudent(@PathVariable String studentId) {
        return loanService.getLoansStudent(studentId);
    }

    //falta
    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos o Devueltos) de un estudiante en especifico
    @GetMapping("/getLoans/state/{studentId}")
    public List<Loan> getLoansStudent(@PathVariable String studentId, @RequestParam(value = "state") String state) {
        return loanService.getLoansStudentState(studentId, state);
    }

    @GetMapping("/getHistory")
    public List<LoanHistory> getHistory() {
        return loanService.getHistory();
    }


    @GetMapping("/getHistoryStudient/{studentId}")
    public List<HistoryLoanStudient> getHistoryStudient(@PathVariable String studentId) {
        return loanService.getHistoryStudient(studentId);
    }

    @GetMapping("/getHistorybook/{copyId}")
    public List<HistoryLoanBookDTO> getHistoryCopy(@PathVariable String copyId) {
        return loanService.getHistoryCopy(copyId);
    }

    @PostMapping("/getDisponibilitybyTitle")
    public List<String> getDisponibilitybyTitle(@RequestBody requestdisponibilityDTO request) {
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            return loanService.getDisponibilitybyTitle(request.getTitle());
        } else {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
    }


    @PostMapping("/getDisponibilitybyAuthor")
    public List<String> getDisponibilitybyAuthor(@RequestBody requestdisponibilityDTO request) {
        if (request.getAuthor() != null && !request.getAuthor().isEmpty()) {
            return loanService.getDisponibilitybyAuthor(request.getAuthor());
        } else {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
    }


}