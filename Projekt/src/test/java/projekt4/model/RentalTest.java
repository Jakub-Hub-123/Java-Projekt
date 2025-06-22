package projekt4.model;

import org.junit.jupiter.api.Test;
import projekt4.model.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class RentalTest {
/**
 *Tworzymy przykładowego klienta
 */
    Customer customer = new Customer("Jakub", "Nowak", "ID123456789", "");
    /**
    *Tworzymy przykładowy typ nart
     */
    SkiType type = new SkiType("Szosowe", "Narty na trasę");
    /**
     * Tworzenie przykładowych nart
     */
    Ski ski = new Ski(type, "Head", "Q10", "Alpejskie", 170);
    /**
     * Data wypożyczenia (od i do)
     */
    LocalDateTime from = LocalDateTime.now();
    LocalDateTime to = from.plusDays(2);
    /**
     * Dodajemy nowe wypożyczenie
      */

    Rental rental = new Rental(customer, ski, from, to, "Pierwszy raz");
    /**
     * Testy getterów:
      */

    /**
     * Czy jest zwracany ten sam klient
      */

    @Test
    public void getCustomerTest() {
        assertEquals(customer, rental.getCustomer());
    }

    /**
     * Czy zwracane narty są poprawne
      */

    @Test
    public void getSkiTest() {
        assertEquals(ski, rental.getSki());
    }

    /**
     * Sprawdzanie daty rozpoczęcia wypożyczenia
      */

    @Test
    public void getDateFromTest() {
        assertEquals(from, rental.getDateFrom());
    }

    /**
     * Sprawdzanie daty zakończenia wypożyczenia
      */

    @Test
    public void getDateToTest() {
        assertEquals(to, rental.getDateTo());
    }

    /**
     * Sprawdzanie domyślnego statusu wypożyczenia (Powinien być ACTIVE)
      */

    @Test
    public void getStatusTest() {
        assertEquals(Rental.RentalStatus.ACTIVE, rental.getStatus());
    }
    /**
     * Sprawdza, czy program poprawnie zwraca notatki
      */

    @Test
    public void getNotesTest() {
        assertEquals("Pierwszy raz", rental.getNotes());
    }

    /**
     * Testy setterów
      */

    /**
     * Testuje zmianę statusu wypożyczenia (RETURNED)
      */

    @Test
    public void setStatusTest() {
        rental.setStatus(Rental.RentalStatus.RETURNED);
        assertEquals(Rental.RentalStatus.RETURNED, rental.getStatus());
    }

    /**
     * Zmiana notatek
      */

    @Test
    public void setNotesTest() {
        rental.setNotes("Zmiana");
        assertEquals("Zmiana", rental.getNotes());
    }
    /**
     * Sprawdza, czy wypożyczenia są przeterminowane
      */

    @Test
    public void isOverdueTest() {
        Rental r = new Rental(customer, ski, from.minusDays(5), from.minusDays(3), "");
        assertTrue(r.isOverdue(LocalDateTime.now()));
        r.setStatus(Rental.RentalStatus.RETURNED);
        assertFalse(r.isOverdue(LocalDateTime.now()));
    }
    /**
     * Sprawdza, czy zawiera słowo "Wypożyczenie"
      */

    @Test
    public void toStringTest() {
        assertTrue(rental.toString().contains("Wypożyczenie"));
    }
    /**
     * Sprawdza, czy dwa identyczne wypożyczenia są sobie równe
      */

    @Test
    public void equalsTest() {
        Rental rental2 = new Rental(customer, ski, from, to, "Pierwszy raz");
        assertEquals(rental, rental2);
    }
    /**
     * Sprawdza, czy identyczne obiekty mają taki sam hash (Powinno być spójne z equals)
      */

    @Test
    public void hashCodeTest() {
        Rental rental2 = new Rental(customer, ski, from, to, "Pierwszy raz");
        assertEquals(rental.hashCode(), rental2.hashCode());
    }
}
