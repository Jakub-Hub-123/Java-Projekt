package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.Ski;
import projekt4.model.SkiType;

import static org.junit.jupiter.api.Assertions.*;

class SkiTest {

    SkiType type = new SkiType("All-Mountain", "Uniwersalne");
    Ski ski = new Ski(type, "Volkl", "Racetiger", "Marker", 170);

    @Test
    void getType() {
        assertEquals(type, ski.getType());
    }

    @Test
    void getBrand() {
        assertEquals("Volkl", ski.getBrand());
    }

    @Test
    void getModel() {
        assertEquals("Racetiger", ski.getModel());
    }

    @Test
    void getBindings() {
        assertEquals("Marker", ski.getBindings());
    }

    @Test
    void getLength() {
        assertEquals(170, ski.getLength());
    }

    @Test
    void isRented() {
        assertFalse(ski.isRented());
    }

    @Test
    void testToString() {
        assertTrue(ski.toString().contains("Volkl"));
    }

    @Test
    void testEquals() {
        Ski s2 = new Ski(type, "Volkl", "Racetiger", "Marker", 170);
        assertEquals(ski, s2);
    }

    @Test
    void testHashCode() {
        Ski s2 = new Ski(type, "Volkl", "Racetiger", "Marker", 170);
        assertEquals(ski.hashCode(), s2.hashCode());
    }
}
