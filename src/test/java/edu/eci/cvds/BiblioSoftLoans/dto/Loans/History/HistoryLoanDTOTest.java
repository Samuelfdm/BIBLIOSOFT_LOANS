package edu.eci.cvds.BiblioSoftLoans.dto.Loans.History;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class HistoryLoanDTOTest {

    @Test
    void testHistoryLoanDTOConstructor() {
        // Crear datos de prueba
        String nameBook = "Test Book";
        String studentName = "Student Name";
        LocalDate loanDate = LocalDate.now();
        String loanState = "Loaned";

        // Crear el objeto HistoryLoanDTO
        HistoryLoanDTO historyLoanDTO = new HistoryLoanDTO(nameBook, studentName, loanDate, loanState);

        // Validar que los valores están correctamente asignados
        assertEquals(nameBook, historyLoanDTO.getNameBook());
        assertEquals(studentName, historyLoanDTO.getStudentName());
        assertEquals(loanDate, historyLoanDTO.getLoanDate());
        assertEquals(loanState, historyLoanDTO.getLoanState());
    }

    @Test
    void testHistoryLoanDTOWithDefaultConstructor() {
        // Crear el objeto usando el constructor por defecto
        HistoryLoanDTO historyLoanDTO = new HistoryLoanDTO();

        // Validar que los valores por defecto son null
        assertNull(historyLoanDTO.getNameBook());
        assertNull(historyLoanDTO.getStudentName());
        assertNull(historyLoanDTO.getLoanDate());
        assertNull(historyLoanDTO.getLoanState());
    }

    @Test
    void testSetterMethods() {
        // Crear el objeto usando el constructor por defecto
        HistoryLoanDTO historyLoanDTO = new HistoryLoanDTO();

        // Asignar valores usando los setters
        historyLoanDTO.setNameBook("Another Test Book");
        historyLoanDTO.setStudentName("Another Student");
        historyLoanDTO.setLoanDate(LocalDate.of(2023, 12, 31));
        historyLoanDTO.setLoanState("Returned");

        // Validar que los valores fueron correctamente asignados
        assertEquals("Another Test Book", historyLoanDTO.getNameBook());
        assertEquals("Another Student", historyLoanDTO.getStudentName());
        assertEquals(LocalDate.of(2023, 12, 31), historyLoanDTO.getLoanDate());
        assertEquals("Returned", historyLoanDTO.getLoanState());
    }
}