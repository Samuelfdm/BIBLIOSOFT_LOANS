package edu.eci.cvds.BiblioSoftLoans.controller;

import edu.eci.cvds.BiblioSoftLoans.dto.*;
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

    // Endpoint para consultar el estado de una copia
    @GetMapping("/copy/{copyId}")
    public ResponseEntity<CopyDTO> getCopyStatus(@PathVariable String copyId) {
        CopyDTO copy = loanService.getCopy(copyId);
        return ResponseEntity.ok(copy);
    }

    // Endpoint para obtener los préstamos activos de un estudiante
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<LoanResponseDTO>> getActiveLoansByStudent(@PathVariable Long studentId) {
        List<LoanResponseDTO> activeLoans = loanService.getActiveLoansByStudent(studentId);
        return ResponseEntity.ok(activeLoans);
    }
}