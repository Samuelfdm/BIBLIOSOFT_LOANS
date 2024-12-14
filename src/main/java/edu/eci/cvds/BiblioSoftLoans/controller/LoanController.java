package edu.eci.cvds.BiblioSoftLoans.controller;

import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.RequestDisponibilityDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanBookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.History.HistoryLoanDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanStudentDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
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

    // Endpoint para obtener todos los prestamos
    @GetMapping("/getLoans")
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    // Endpoint para obtener todos los prestamos en un estado especifico (Loaned, Expired o Returned)
    @GetMapping("/getLoans/state")
    public List<Loan> getLoans(@RequestParam(value = "state") String state) {
        return loanService.getLoans(state);
    }

    // Endpoint para obtener todos los prestamos de un estudiante en especifico
    @GetMapping("/getLoans/{studentId}")
    public List<Loan> getLoansStudent(@PathVariable String studentId) {
        return loanService.getLoansStudent(studentId);
    }

    // Endpoint para obtener todos los prestamos en un estado especifico (Loaned, Expired o Returned) de un estudiante en especifico
    @GetMapping("/getLoans/state/{studentId}")
    public List<Loan> getLoansStudent(@PathVariable String studentId, @RequestParam(value = "state") String state) {
        return loanService.getLoansStudent(studentId, state);
    }

    // ----------------------------------------------------------------------------------------------------

    // EndPoint para traer el historial de prestamos de todos los libros de todos los estudiantes (resumido)
    @GetMapping("/getHistory")
    public List<HistoryLoanDTO> getHistory() {
        return loanService.getHistory();
    }

    // EndPoint para traer el historial de prestamos por estudiante (resumido)
    @GetMapping("/getHistoryByStudent/{studentId}")
    public List<HistoryLoanStudentDTO> getHistoryByStudent(@PathVariable String studentId) {
        return loanService.getHistoryByStudent(studentId);
    }

    // EndPoint para traer el historial de prestamos de una copia especifica (resumido)
    @GetMapping("/getHistoryByCopy/{copyId}")
    public List<HistoryLoanBookDTO> getHistoryByCopy(@PathVariable String copyId) {
        return loanService.getHistoryByCopy(copyId);
    }

    // EndPoint para buscar la disponibilidad de un libro por titulo
    @PostMapping("/getDisponibilityByTitle")
    public List<String> getDisponibilityByTitle(@RequestBody RequestDisponibilityDTO request) {
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            return loanService.getDisponibilityByTitle(request.getTitle());
        } else {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
    }

    // EndPoint para buscar la disponibilidad de un libro por autor
    @PostMapping("/getDisponibilityByAuthor")
    public List<String> getDisponibilityByAuthor(@RequestBody RequestDisponibilityDTO request) {
        if (request.getAuthor() != null && !request.getAuthor().isEmpty()) {
            return loanService.getDisponibilityByAuthor(request.getAuthor());
        } else {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
    }
}