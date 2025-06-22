package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.Ski;
import projekt4.model.SkiType;
import static org.junit.jupiter.api.Assertions.*;

public class SkiTest {
    /**
     * Typ nart
     */
    SkiType type = new SkiType("Biegówki", "Uniwersalne");
    /**
     * Tworzenie obiektu Ski
     */
    Ski ski = new Ski(type, "Head", "Q10", "Biegowe", 170);
/**
 * Testy getterów
 */
/**
 * Typ nart
 */
@Test
    public void getTypeTest() {
        assertEquals(type, ski.getType());
    }
    /**
     * Marka
     */
    @Test
    public void getBrandtest() {
        assertEquals("Head", ski.getBrand());
    }
    /**
     * Model
     */
    @Test
    public void getModelTest() {
        assertEquals("Q10", ski.getModel());
    }
    /**
     * Wiązania
     */
    @Test
    public void getBindingsTest() {
        assertEquals("Biegowe", ski.getBindings());
    }
    /**
     * długość
     */
    @Test
    public void getLengthTest() {
        assertEquals(170, ski.getLength());
    }
    /**
     * Test domyślnego stanu wypożyczenia (Wymagane false)
     */
    @Test
    public void isRentedTest() {
        assertFalse(ski.isRented());
    }
    /**
     * Sprawdza, czy opis zawiera markę
     */
    @Test
    public void toStringTest() {
        assertTrue(ski.toString().contains("Head"));
    }
    /**
     * Sprawdza równość identycznych obiektów
     */
    @Test
    public void equalsTest() {
        Ski s2 = new Ski(type, "Head", "Q10", "Biegowe", 170);
        assertEquals(ski, s2);
    }
    /**
     * Sprawdza, czy równe obiekty mają taki sam hash
     */
    @Test
    public void hashCodeTest() {
        Ski s2 = new Ski(type, "Head", "Q10", "Biegowe", 170);
        assertEquals(ski.hashCode(), s2.hashCode());
    }
}
