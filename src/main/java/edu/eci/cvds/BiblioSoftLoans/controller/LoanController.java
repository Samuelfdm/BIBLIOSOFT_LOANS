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

    // Endpoint para obtener todos los prestamos
    @GetMapping("/getLoans")
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    // Endpoint para obtener todos los prestamos  de un estudiante en especifico
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

    //end point para traer historial de todos los libros
    @GetMapping("/getHistory")
    public List<LoanHistory> getHistory() {
        return loanService.getHistory();
    }

    //end point para traer historial de un estudiante
    @GetMapping("/getHistoryStudient/{studentId}")
    public List<HistoryLoanStudient> getHistoryStudient(@PathVariable String studentId) {
        return loanService.getHistoryStudient(studentId);
    }

    //end point para traer historial de una copia
    @GetMapping("/getHistorybook/{copyId}")
    public List<HistoryLoanBookDTO> getHistoryCopy(@PathVariable String copyId) {
        return loanService.getHistoryCopy(copyId);
    }

    //end point para buscar disponibilida de un libro por titulo
    @PostMapping("/getDisponibilitybyTitle")
    public List<String> getDisponibilitybyTitle(@RequestBody requestdisponibilityDTO request) {
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            return loanService.getDisponibilitybyTitle(request.getTitle());
        } else {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
    }

    //end point para buscar disponibilida de un libro por autor
    @PostMapping("/getDisponibilitybyAuthor")
    public List<String> getDisponibilitybyAuthor(@RequestBody requestdisponibilityDTO request) {
        if (request.getAuthor() != null && !request.getAuthor().isEmpty()) {
            return loanService.getDisponibilitybyAuthor(request.getAuthor());
        } else {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
    }

    //end point para traer todos los prestamos activos
    @GetMapping("/getActiveloans")
    public List<Loan> getActiveLoan() {
        return loanService.getActiveLoan();
    }

    //end point para traer todos los prestamos activos de un estudiante
    @GetMapping("/getActiveloans/{studentId}")
    public List<Loan> getActiveLoan(@PathVariable String studentId) {
        return loanService.getActiveLoanbyStudient(studentId);
    }

}