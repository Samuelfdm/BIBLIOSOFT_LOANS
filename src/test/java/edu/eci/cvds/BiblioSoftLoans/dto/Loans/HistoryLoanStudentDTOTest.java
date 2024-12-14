package edu.eci.cvds.BiblioSoftLoans.dto.Loans;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class HistoryLoanStudentDTOTest {

    @Test
    void testHistoryLoanStudentDTO() {
        // Crear datos de prueba
        String nameBook = "Test Book";
        String studentName = "Student Name";
        String copyId = "copy123";
        LocalDate loanDate = LocalDate.now();
        String loanState = "Loaned";

        // Crear el objeto HistoryLoanStudentDTO
        HistoryLoanStudentDTO historyLoanStudentDTO = new HistoryLoanStudentDTO(nameBook, studentName, copyId, loanDate, loanState);

        // Validar que los valores están correctamente asignados
        assertEquals(nameBook, historyLoanStudentDTO.getNameBook());
        assertEquals(studentName, historyLoanStudentDTO.getStudentName());
        assertEquals(copyId, historyLoanStudentDTO.getCopyId());
        assertEquals(loanDate, historyLoanStudentDTO.getLoanDate());
        assertEquals(loanState, historyLoanStudentDTO.getLoanState());
    }

    @Test
    void testHistoryLoanStudentDTOWithDefaultConstructor() {
        // Crear el objeto usando el constructor por defecto
        HistoryLoanStudentDTO historyLoanStudentDTO = new HistoryLoanStudentDTO();

        // Validar que los valores por defecto son null
        assertNull(historyLoanStudentDTO.getNameBook());
        assertNull(historyLoanStudentDTO.getStudentName());
        assertNull(historyLoanStudentDTO.getCopyId());
        assertNull(historyLoanStudentDTO.getLoanDate());
        assertNull(historyLoanStudentDTO.getLoanState());
    }

    @Test
    void testSetterMethods() {
        // Crear el objeto usando el constructor por defecto
        HistoryLoanStudentDTO historyLoanStudentDTO = new HistoryLoanStudentDTO();

        // Asignar valores usando los setters
        historyLoanStudentDTO.setNameBook("Another Test Book");
        historyLoanStudentDTO.setStudentName("Another Student");
        historyLoanStudentDTO.setCopyId("copy456");
        historyLoanStudentDTO.setLoanDate(LocalDate.of(2023, 12, 31));
        historyLoanStudentDTO.setLoanState("Returned");

        // Validar que los valores fueron correctamente asignados
        assertEquals("Another Test Book", historyLoanStudentDTO.getNameBook());
        assertEquals("Another Student", historyLoanStudentDTO.getStudentName());
        assertEquals("copy456", historyLoanStudentDTO.getCopyId());
        assertEquals(LocalDate.of(2023, 12, 31), historyLoanStudentDTO.getLoanDate());
        assertEquals("Returned", historyLoanStudentDTO.getLoanState());
    }
}
