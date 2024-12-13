package edu.eci.cvds.BiblioSoftLoans.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CopyDTOTest {

    @Test
    public void testCopyDTOSettersAndGetters() {
        CopyDTO copy = new CopyDTO();
        copy.setId("copy123");
        copy.setBook("book456");
        copy.setBarCode("barcode789");
        copy.setState("GOOD");
        copy.setDisponibility(CopyDTO.CopyDispo.AVAILABLE);
        copy.setUbication("A1");
        copy.setActive(true);

        assertEquals("copy123", copy.getId());
        assertEquals("book456", copy.getBook());
        assertEquals("barcode789", copy.getBarCode());
        assertEquals("GOOD", copy.getState());
        assertEquals(CopyDTO.CopyDispo.AVAILABLE, copy.getDisponibility());
        assertEquals("A1", copy.getUbication());
        assertTrue(copy.isActive());
    }

    @Test
    public void testCopyDTODefaultConstructor() {
        CopyDTO copy = new CopyDTO();

        assertNull(copy.getId());
        assertNull(copy.getBook());
        assertNull(copy.getBarCode());
        assertNull(copy.getState());
        assertNull(copy.getDisponibility());
        assertNull(copy.getUbication());
        assertFalse(copy.isActive());
    }

    @Test
    public void testCopyDTOConstructorWithValues() {
        CopyDTO copy = new CopyDTO("copy123", "book456", "GOOD", "barcode789",
                "A1", CopyDTO.CopyDispo.BORROWED, true);

        assertEquals("copy123", copy.getId());
        assertEquals("book456", copy.getBook());
        assertEquals("barcode789", copy.getBarCode());
        assertEquals("GOOD", copy.getState());
        assertEquals(CopyDTO.CopyDispo.BORROWED, copy.getDisponibility());
        assertEquals("A1", copy.getUbication());
        assertTrue(copy.isActive());
    }

    @Test
    public void testCopyDTOToString() {
        CopyDTO copy = new CopyDTO("copy123", "book456", "DAMAGED", "barcode789",
                "A3", CopyDTO.CopyDispo.AVAILABLE, true);

        String copyString = copy.toString();

        assertTrue(copyString.contains("id=copy123"));
        assertTrue(copyString.contains("book=book456"));
        assertTrue(copyString.contains("barCode=barcode789"));
        assertTrue(copyString.contains("state=DAMAGED"));
        assertTrue(copyString.contains("disponibility=AVAILABLE"));
        assertTrue(copyString.contains("ubication=A3"));
        assertTrue(copyString.contains("active=true"));
    }

    @Test
    public void testCopyDTOEqualityAndHashCode() {
        CopyDTO copy1 = new CopyDTO("copy123", "book456", "DAMAGED", "barcode789",
                "A1", CopyDTO.CopyDispo.AVAILABLE, true);

        CopyDTO copy2 = new CopyDTO("copy123", "book456", "DAMAGED", "barcode789",
                "A1", CopyDTO.CopyDispo.AVAILABLE, true);

        assertEquals(copy1, copy2);
        assertEquals(copy1.hashCode(), copy2.hashCode());

        copy2.setUbication("B1");
        assertNotEquals(copy1, copy2);
        assertNotEquals(copy1.hashCode(), copy2.hashCode());
    }

    @Test
    public void testCopyDTOJsonSerialization() throws Exception {
        // Usar una librería como Jackson para probar la serialización/deserialización
        ObjectMapper objectMapper = new ObjectMapper();

        CopyDTO copy = new CopyDTO("copy123", "book456", "GOOD", "barcode789",
                "A1", CopyDTO.CopyDispo.BORROWED, true);

        String json = objectMapper.writeValueAsString(copy);
        CopyDTO deserializedCopy = objectMapper.readValue(json, CopyDTO.class);

        assertEquals(copy, deserializedCopy);
    }
}
