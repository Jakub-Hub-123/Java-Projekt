package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.Customer;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    /**
     * Stworzenie przykładowego klienta
     */
    Customer c = new Customer("Jakub", "Nowak", "ID123", "Opis");
/**
 * Testy getterów
 */

/**
 * Sprawdza imię
 */
@Test
    public void getFirstNameTest() {
        assertEquals("Jakub", c.getFirstName());
    }
    /**
     * Sprawdza nazwisko
     */
    @Test
    public void getLastNameTest() {
        assertEquals("Nowak", c.getLastName());
    }
    /**
     * Sprawdza numer dokumentu
     */
    @Test
    public void getDocumentNumberTest() {
        assertEquals("ID123", c.getDocumentNumber());
    }
    /**
     * Sprawdza notatki
     */
    @Test
    public void getNotesTest() {
        assertEquals("Opis", c.getNotes());
    }
    /**
     * Sprawdza, czy obiekt zawiera imię (służy do czytelnego opisu obiektu)
     */
    @Test
    public void toStringTest() {
        assertTrue(c.toString().contains("Jakub"));
    }
    /**
     * Sprawdza równość klientów po numerze dokumentu (porównuje logicznie dwóch klientów)
     */
    @Test
    public void equalsTest() {
        Customer c2 = new Customer("X", "Y", "ID1", "inne");
        assertEquals(c, c2);
    }
    /**
     * Sprawdza, czy obiekty równe logicznie mają ten sam hash
     */
    @Test
    public void hashCodeTest() {
        Customer c2 = new Customer("X", "Y", "ID1", "inne");
        assertEquals(c.hashCode(), c2.hashCode());
    }
}
