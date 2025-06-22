package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    Customer customer = new Customer("Ewa", "Nowak", "ID123", "");
    SkiType type = new SkiType("Carving", "Narty na trasę");
    Ski ski = new Ski(type, "Head", "i.Supershape", "Tyrolia", 165);
    LocalDateTime from = LocalDateTime.now();
    LocalDateTime to = from.plusDays(2);
    Rental rental = new Rental(customer, ski, from, to, "Pierwszy raz");

    @Test
    void getCustomer() {
        assertEquals(customer, rental.getCustomer());
    }

    @Test
    void getSki() {
        assertEquals(ski, rental.getSki());
    }

    @Test
    void getDateFrom() {
        assertEquals(from, rental.getDateFrom());
    }

    @Test
    void getDateTo() {
        assertEquals(to, rental.getDateTo());
    }

    @Test
    void getStatus() {
        assertEquals(Rental.RentalStatus.ACTIVE, rental.getStatus());
    }

    @Test
    void setStatus() {
        rental.setStatus(Rental.RentalStatus.RETURNED);
        assertEquals(Rental.RentalStatus.RETURNED, rental.getStatus());
    }

    @Test
    void getNotes() {
        assertEquals("Pierwszy raz", rental.getNotes());
    }

    @Test
    void setNotes() {
        rental.setNotes("Zmiana");
        assertEquals("Zmiana", rental.getNotes());
    }

    @Test
    void isOverdue() {
        Rental r = new Rental(customer, ski, from.minusDays(5), from.minusDays(3), "");
        assertTrue(r.isOverdue(LocalDateTime.now()));
        r.setStatus(Rental.RentalStatus.RETURNED);
        assertFalse(r.isOverdue(LocalDateTime.now()));
    }

    @Test
    void testToString() {
        assertTrue(rental.toString().contains("Wypożyczenie"));
    }

    @Test
    void testEquals() {
        Rental rental2 = new Rental(customer, ski, from, to, "Pierwszy raz");
        assertEquals(rental, rental2);
    }

    @Test
    void testHashCode() {
        Rental rental2 = new Rental(customer, ski, from, to, "Pierwszy raz");
        assertEquals(rental.hashCode(), rental2.hashCode());
    }
}
