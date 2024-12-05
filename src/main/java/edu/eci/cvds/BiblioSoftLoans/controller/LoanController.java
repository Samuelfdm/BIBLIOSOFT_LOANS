package edu.eci.cvds.BiblioSoftLoans.controller;

import edu.eci.cvds.BiblioSoftLoans.dto.*;
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
public class LoanController {

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

    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos o Devueltos) de TODOS
    @GetMapping("/loansActive")
    public List<Loan> loansActive() {
        return loanService.loansActive();
    }

    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos o Devueltos) de un estudiante en especifico
    @GetMapping("/loansActive/{studentId}")
    public List<Loan> loansActiveStudent(@PathVariable Long studentId) {
        return loanService.loansActiveStudent(studentId);
    }

    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos y Devueltos) de un estudiante en especifico
    @GetMapping("/loansAll/{studentId}")
    public List<Loan> loansAllStudent(@PathVariable Long studentId) {
        return loanService.loansAllStudent(studentId);
    }

    // Endpoint para obtener todos los prestamos en estado (Prestados, Vencidos y Devueltos) de TODOS
    @GetMapping
    public List<Loan> loansAll(){
        return loanService.loansAll();
    }
}