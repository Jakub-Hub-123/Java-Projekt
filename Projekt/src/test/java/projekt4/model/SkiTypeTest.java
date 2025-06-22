package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.SkiType;
import static org.junit.jupiter.api.Assertions.*;

public class SkiTypeTest {
/**
 * Testy getterów
 */
/**
 * Nazwa typu
 */
@Test
    public void getNameTest() {
        SkiType type = new SkiType("Slalomowe", "Opis");
        assertEquals("Slalomowe", type.getName());
    }
    /**
     * Opis typu
     */
    @Test
    public void getDescriptionTest() {
        SkiType type = new SkiType("Slalomowe", "Opis");
        assertEquals("Opis", type.getDescription());
    }
    /**
     * Sprawdza, czy nazwa typu jest w opisie
     */
    @Test
    public void toStringTest() {
        SkiType type = new SkiType("Slalomowe", "Opis");
        assertTrue(type.toString().contains("Slalomowe"));
    }
    /**
     * Sprawdza czy typy z tą samą nazwą są równe
     */
    @Test
    public void equalsTest() {
        SkiType type1 = new SkiType("A", "Opis1");
        SkiType type2 = new SkiType("A", "Opis2");
        assertEquals(type1, type2);
    }
    /**
     * Sprawdza czy obiekty z identycznymi nazwami mają taki sam hash
     */
    @Test
    public void hashCodeTest() {
        SkiType type1 = new SkiType("A", "Opis1");
        SkiType type2 = new SkiType("A", "Opis2");
        assertEquals(type1.hashCode(), type2.hashCode());
    }
}
