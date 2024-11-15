package edu.eci.cvds.BiblioSoftLoans.DTO;
import edu.eci.cvds.BiblioSoftLoans.dto.CopyDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CopyDTOTest {

    @Test
    public void testCopyDTOSettersAndGetters() {
        CopyDTO copy = new CopyDTO();
        copy.setId("copy123");
        copy.setBookId("book456");
        copy.setBarCode("barcode789");
        copy.setState("GOOD");
        copy.setDisponibility("AVAILABLE");
        copy.setCategory("LITERATURE");

        assertEquals("copy123", copy.getId());
        assertEquals("book456", copy.getBookId());
        assertEquals("barcode789", copy.getBarCode());
        assertEquals("GOOD", copy.getState());
        assertEquals("AVAILABLE", copy.getDisponibility());
        assertEquals("LITERATURE", copy.getCategory());
    }

    @Test
    public void testCopyDTODefaultConstructor() {
        CopyDTO copy = new CopyDTO();

        assertNull(copy.getId());
        assertNull(copy.getBookId());
        assertNull(copy.getBarCode());
        assertNull(copy.getState());
        assertNull(copy.getDisponibility());
        assertNull(copy.getCategory());
    }

    @Test
    public void testCopyDTOConstructorWithValues() {
        CopyDTO copy = new CopyDTO();
        copy.setId("copy123");
        copy.setBookId("book456");
        copy.setBarCode("barcode789");
        copy.setState("MODERATE");
        copy.setDisponibility("LOANED");
        copy.setCategory("SCIENCE");

        assertNotNull(copy);
        assertEquals("copy123", copy.getId());
        assertEquals("book456", copy.getBookId());
        assertEquals("barcode789", copy.getBarCode());
        assertEquals("MODERATE", copy.getState());
        assertEquals("LOANED", copy.getDisponibility());
        assertEquals("SCIENCE", copy.getCategory());
    }

    @Test
    public void testCopyDTOToString() {
        CopyDTO copy = new CopyDTO();
        copy.setId("copy123");
        copy.setBookId("book456");
        copy.setBarCode("barcode789");
        copy.setState("DAMAGED");
        copy.setDisponibility("AVAILABLE");
        copy.setCategory("HISTORY");

        String copyString = copy.toString();

        assertTrue(copyString.contains("id=copy123"));
        assertTrue(copyString.contains("bookId=book456"));
        assertTrue(copyString.contains("barCode=barcode789"));
        assertTrue(copyString.contains("state=DAMAGED"));
        assertTrue(copyString.contains("disponibility=AVAILABLE"));
        assertTrue(copyString.contains("category=HISTORY"));
    }

    @Test
    public void testCopyDTOEquality() {
        CopyDTO copy1 = new CopyDTO();
        copy1.setId("copy123");
        copy1.setBookId("book456");
        copy1.setBarCode("barcode789");
        copy1.setState("DAMAGED");
        copy1.setDisponibility("AVAILABLE");
        copy1.setCategory("HISTORY");

        CopyDTO copy2 = new CopyDTO();
        copy2.setId("copy123");
        copy2.setBookId("book456");
        copy2.setBarCode("barcode789");
        copy2.setState("DAMAGED");
        copy2.setDisponibility("AVAILABLE");
        copy2.setCategory("HISTORY");

        assertEquals(copy1, copy2);
    }
}
