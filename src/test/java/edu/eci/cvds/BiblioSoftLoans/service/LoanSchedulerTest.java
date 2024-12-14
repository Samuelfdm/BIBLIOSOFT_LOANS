package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanSchedulerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanScheduler loanScheduler;

    @Test
    void testCheckForExpiredLoans() {
        // Simular los préstamos activos
        Loan loan1 = new Loan(); // Simular préstamo
        loan1.setLoanState(LoanState.Loaned);
        Loan loan2 = new Loan(); // Simular préstamo
        loan2.setLoanState(LoanState.Loaned);

        // Simula la recuperación de préstamos activos (solo si realmente se usa)
        when(loanService.getLoans()).thenReturn(Arrays.asList(loan1, loan2));

        // Llamar al método programado directamente
        loanScheduler.checkForExpiredLoans();

        // Verificar que el servicio `checkForExpiredLoans` fue llamado
        verify(loanService, times(1)).checkForExpiredLoans();

        // Limpiar el stubbing no utilizado
        reset(loanService); // Eliminar el stubbing innecesario
    }

    @Test
    void testCheckForExpiredLoansWhenNoLoans() {
        // Llamar al método programado directamente
        loanScheduler.checkForExpiredLoans();

        // Verificar que el servicio `checkForExpiredLoans` fue llamado
        verify(loanService, times(1)).checkForExpiredLoans();

        // Limpiar el stubbing no utilizado
        reset(loanService); // Eliminar el stubbing innecesario
    }
}