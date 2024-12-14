package edu.eci.cvds.BiblioSoftLoans.dto.Loans;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HistoryLoanBookDTOTest {

    @Test
    void testHistoryLoanBookDTO() {
        // Crear datos de prueba
        String nameBook = "Test Book";
        String copyId = "copy123";
        LocalDate loanDate = LocalDate.now();
        String loanState = "Loaned";

        // Crear el objeto HistoryLoanBookDTO
        HistoryLoanBookDTO historyLoanBookDTO = new HistoryLoanBookDTO(nameBook, copyId, loanDate, loanState);

        // Validar que los valores están correctamente asignados
        assertEquals(nameBook, historyLoanBookDTO.getNameBook());
        assertEquals(copyId, historyLoanBookDTO.getCopyId());
        assertEquals(loanDate, historyLoanBookDTO.getLoanDate());
        assertEquals(loanState, historyLoanBookDTO.getLoanState());
    }

    @Test
    void testHistoryLoanBookDTOWithDefaultConstructor() {
        // Crear el objeto usando el constructor por defecto
        HistoryLoanBookDTO historyLoanBookDTO = new HistoryLoanBookDTO();

        // Validar que los valores por defecto son null
        assertNull(historyLoanBookDTO.getNameBook());
        assertNull(historyLoanBookDTO.getCopyId());
        assertNull(historyLoanBookDTO.getLoanDate());
        assertNull(historyLoanBookDTO.getLoanState());
    }

    @Test
    void testSetterMethods() {
        // Crear el objeto usando el constructor por defecto
        HistoryLoanBookDTO historyLoanBookDTO = new HistoryLoanBookDTO();

        // Asignar valores usando los setters
        historyLoanBookDTO.setNameBook("Another Test Book");
        historyLoanBookDTO.setCopyId("copy456");
        historyLoanBookDTO.setLoanDate(LocalDate.of(2023, 12, 31));
        historyLoanBookDTO.setLoanState("Returned");

        // Validar que los valores fueron correctamente asignados
        assertEquals("Another Test Book", historyLoanBookDTO.getNameBook());
        assertEquals("copy456", historyLoanBookDTO.getCopyId());
        assertEquals(LocalDate.of(2023, 12, 31), historyLoanBookDTO.getLoanDate());
        assertEquals("Returned", historyLoanBookDTO.getLoanState());
    }
}

