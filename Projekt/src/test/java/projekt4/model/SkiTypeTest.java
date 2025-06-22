package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.SkiType;

import static org.junit.jupiter.api.Assertions.*;

class SkiTypeTest {

    @Test
    void getName() {
        SkiType type = new SkiType("Freeride", "Opis");
        assertEquals("Freeride", type.getName());
    }

    @Test
    void getDescription() {
        SkiType type = new SkiType("Freeride", "Opis");
        assertEquals("Opis", type.getDescription());
    }

    @Test
    void testToString() {
        SkiType type = new SkiType("Freeride", "Opis");
        assertTrue(type.toString().contains("Freeride"));
    }

    @Test
    void testEquals() {
        SkiType type1 = new SkiType("A", "Opis1");
        SkiType type2 = new SkiType("A", "Opis2");
        assertEquals(type1, type2);
    }

    @Test
    void testHashCode() {
        SkiType type1 = new SkiType("A", "Opis1");
        SkiType type2 = new SkiType("A", "Opis2");
        assertEquals(type1.hashCode(), type2.hashCode());
    }
}
