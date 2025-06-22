package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer c = new Customer("Anna", "Nowak", "ID1", "Opis");

    @Test
    void getFirstName() {
        assertEquals("Anna", c.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Nowak", c.getLastName());
    }

    @Test
    void getDocumentNumber() {
        assertEquals("ID1", c.getDocumentNumber());
    }

    @Test
    void getNotes() {
        assertEquals("Opis", c.getNotes());
    }

    @Test
    void testToString() {
        assertTrue(c.toString().contains("Anna"));
    }

    @Test
    void testEquals() {
        Customer c2 = new Customer("X", "Y", "ID1", "inne");
        assertEquals(c, c2);
    }

    @Test
    void testHashCode() {
        Customer c2 = new Customer("X", "Y", "ID1", "inne");
        assertEquals(c.hashCode(), c2.hashCode());
    }
}
