package edu.eci.cvds.BiblioSoftLoans.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class ReturnResponseDTOTest {

    @Test
    public void testReturnResponseDTO() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(1L, LocalDate.now(), "123", "BUENO");

        assertNotNull(returnResponse);
        assertEquals(Long.valueOf(1), returnResponse.getLoanId());
        assertEquals(LocalDate.now(), returnResponse.getReturnDate());
        assertEquals("BUENO", returnResponse.getFinalCopyState());  // Usando String
    }

    @Test
    public void testReturnResponseDTOWithDamagedCopyState() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(1L, LocalDate.now(), "123", "DAÑADO");

        assertNotNull(returnResponse);
        assertEquals("DAÑADO", returnResponse.getFinalCopyState());  // Usando String
    }

    @Test
    public void testReturnResponseDTOWithModerateCopyState() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(1L, LocalDate.now(), "123", "MODERADO");

        assertNotNull(returnResponse);
        assertEquals("MODERADO", returnResponse.getFinalCopyState());  // Usando String
    }

    @Test
    public void testReturnResponseDTOWithNullCopyState() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(1L, LocalDate.now(), "123", null);

        assertNotNull(returnResponse);
        assertNull(returnResponse.getFinalCopyState());
    }

    @Test
    public void testReturnResponseDTOWithNullLoanId() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(null, LocalDate.now(), "123", "BUENO");

        assertNotNull(returnResponse);
        assertNull(returnResponse.getLoanId());
    }

    @Test
    public void testReturnResponseDTOWithNullReturnDate() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(1L, null, "123", "BUENO");

        assertNotNull(returnResponse);
        assertNull(returnResponse.getReturnDate());
    }

    @Test
    public void testReturnResponseDTOWithAllNullValues() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(null, null, null,null);

        assertNotNull(returnResponse);
        assertNull(returnResponse.getLoanId());
        assertNull(returnResponse.getReturnDate());
        assertNull(returnResponse.getFinalCopyState());
    }

    @Test
    public void testReturnResponseDTOWithValidFields() {
        LocalDate returnDate = LocalDate.of(2024, 11, 14);
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(6L, returnDate, "123","BUENO");

        assertNotNull(returnResponse);
        assertEquals(Long.valueOf(6), returnResponse.getLoanId());
        assertEquals(returnDate, returnResponse.getReturnDate());
        assertEquals("BUENO", returnResponse.getFinalCopyState());  // Usando String
    }
}
