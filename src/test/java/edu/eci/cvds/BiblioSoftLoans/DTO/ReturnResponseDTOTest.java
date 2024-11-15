package edu.eci.cvds.BiblioSoftLoans.DTO;
import edu.eci.cvds.BiblioSoftLoans.dto.ReturnResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.model.CopyState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;


public class ReturnResponseDTOTest {

    @Test
    public void testReturnResponseDTO() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(1L, LocalDate.now(), CopyState.Good);

        assertNotNull(returnResponse);
        assertEquals(Long.valueOf(1), returnResponse.getLoanId());
        assertEquals(LocalDate.now(), returnResponse.getReturnDate());
        assertEquals(CopyState.Good, returnResponse.getFinalCopyState());
    }

    @Test
    public void testReturnResponseDTOWithDamagedCopyState() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(2L, LocalDate.now(), CopyState.Good);

        assertNotNull(returnResponse);
        assertEquals(CopyState.Good, returnResponse.getFinalCopyState());
    }

    @Test
    public void testReturnResponseDTOWithModerateCopyState() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(3L, LocalDate.now(), CopyState.Good);

        assertNotNull(returnResponse);
        assertEquals(CopyState.Good, returnResponse.getFinalCopyState());
    }

    @Test
    public void testReturnResponseDTOWithNullCopyState() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(4L, LocalDate.now(), null);

        assertNotNull(returnResponse);
        assertNull(returnResponse.getFinalCopyState());
    }

    @Test
    public void testReturnResponseDTOWithNullLoanId() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(null, LocalDate.now(), CopyState.Good);

        assertNotNull(returnResponse);
        assertNull(returnResponse.getLoanId());
    }

    @Test
    public void testReturnResponseDTOWithNullReturnDate() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(5L, null, CopyState.Good);

        assertNotNull(returnResponse);
        assertNull(returnResponse.getReturnDate());
    }

    @Test
    public void testReturnResponseDTOWithAllNullValues() {
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(null, null, null);

        assertNotNull(returnResponse);
        assertNull(returnResponse.getLoanId());
        assertNull(returnResponse.getReturnDate());
        assertNull(returnResponse.getFinalCopyState());
    }

    @Test
    public void testReturnResponseDTOWithValidFields() {
        LocalDate returnDate = LocalDate.of(2024, 11, 14);
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(6L, returnDate, CopyState.Good);

        assertNotNull(returnResponse);
        assertEquals(Long.valueOf(6), returnResponse.getLoanId());
        assertEquals(returnDate, returnResponse.getReturnDate());
        assertEquals(CopyState.Good, returnResponse.getFinalCopyState());
    }
}
