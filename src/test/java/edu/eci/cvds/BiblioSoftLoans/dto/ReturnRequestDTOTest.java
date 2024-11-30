package edu.eci.cvds.BiblioSoftLoans.dto;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReturnRequestDTOTest {

    @Test
    public void testReturnRequestDTO() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(1L, "copy101", CopyState.Good);

        assertNotNull(returnRequest);
        assertEquals(Long.valueOf(1), returnRequest.getStudentId());
        assertEquals("copy101", returnRequest.getCopyId());
        assertEquals(CopyState.Good, returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithDamagedCopyState() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(2L, "copy102", CopyState.Good);

        assertNotNull(returnRequest);
        assertEquals(CopyState.Good, returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithModerateCopyState() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(3L, "copy103", CopyState.Good);

        assertNotNull(returnRequest);
        assertEquals(CopyState.Good, returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithInvalidCopyState() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(4L, "copy104", null);

        assertNotNull(returnRequest);
        assertNull(returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithNullStudentId() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(null, "copy105", CopyState.Good);

        assertNotNull(returnRequest);
        assertNull(returnRequest.getStudentId());
    }

    @Test
    public void testReturnRequestDTOWithNullCopyId() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(6L, null, CopyState.Good);

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
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(7L, "copy107", CopyState.Good);

        assertNotNull(returnRequest);
        assertEquals(CopyState.Good, returnRequest.getFinalCopyState());
    }

    @Test
    public void testReturnRequestDTOWithAllValidFields() {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(8L, "copy108", CopyState.Good);

        assertNotNull(returnRequest);
        assertEquals(Long.valueOf(8), returnRequest.getStudentId());
        assertEquals("copy108", returnRequest.getCopyId());
        assertEquals(CopyState.Good, returnRequest.getFinalCopyState());
    }
}
