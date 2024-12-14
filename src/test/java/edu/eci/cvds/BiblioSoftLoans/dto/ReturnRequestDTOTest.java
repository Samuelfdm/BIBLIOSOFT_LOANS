package edu.eci.cvds.BiblioSoftLoans.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReturnRequestDTOTest {

    @Test
    public void testReturnRequestDTO() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO("67323424", "copy101", "BUENO");  // Usando String

        assertNotNull(returnRequest);
        assertEquals("67323424", returnRequest.getStudentId());
        assertEquals("copy101", returnRequest.getCopyId());
        assertEquals("BUENO", returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithDamagedCopyState() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO("67323424", "copy102", "DAÑADO");  // Usando String

        assertNotNull(returnRequest);
        assertEquals("DAÑADO", returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithModerateCopyState() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO("67323424", "copy103", "MODERADO");  // Usando String

        assertNotNull(returnRequest);
        assertEquals("MODERADO", returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithInvalidCopyState() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO("67323424", "copy104", null);

        assertNotNull(returnRequest);
        assertNull(returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithNullStudentId() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(null, "copy105", "BUENO");  // Usando String

        assertNotNull(returnRequest);
        assertNull(returnRequest.getStudentId());
    }

    @Test
    public void testReturnRequestDTOWithNullCopyId() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO("67323424", null, "BUENO");  // Usando String

        assertNotNull(returnRequest);
        assertNull(returnRequest.getCopyId());
    }

    @Test
    public void testReturnRequestDTOWithAllNullValues() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(null, null, null);

        assertNotNull(returnRequest);
        assertNull(returnRequest.getStudentId());
        assertNull(returnRequest.getCopyId());
        assertNull(returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithValidAndInvalidCopyState() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO("67323424", "copy107", "BUENO");  // Usando String

        assertNotNull(returnRequest);
        assertEquals("BUENO", returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithAllValidFields() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO("67323424", "copy108", "BUENO");  // Usando String

        assertNotNull(returnRequest);
        assertEquals("67323424", returnRequest.getStudentId());
        assertEquals("copy108", returnRequest.getCopyId());
        assertEquals("BUENO", returnRequest.getFinalCopyState());
    }
}
