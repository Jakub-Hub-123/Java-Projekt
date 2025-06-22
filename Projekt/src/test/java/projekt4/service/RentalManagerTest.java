package projekt4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projekt4.model.*;
import projekt4.service.RentalManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentalManagerTest {

    private RentalManager manager;
    private SkiType type;
    private Ski ski;
    private Customer customer;

    @BeforeEach
    void setUp() {
        manager = new RentalManager();
        type = new SkiType("TestType", "Opis testowy");
        ski = new Ski(type, "Volkl", "Racetiger", "Marker", 180);
        customer = new Customer("Adam", "Kowalski", "ID111", "Bez uwag");

        manager.addSkiType(type);
        manager.addSki(ski);
        manager.addCustomer(customer);
    }

    @Test
    void testRentAndReturn() {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = from.plusDays(1);
        manager.rentSki(customer, ski, from, to, "Test wypożyczenia");

        List<Rental> rentals = manager.getRentals();
        assertEquals(1, rentals.size());
        assertTrue(ski.isRented());

        Rental rental = rentals.get(0);
        manager.returnSki(rental, "Zwrot OK");
        assertEquals(Rental.RentalStatus.RETURNED, rental.getStatus());
        assertFalse(ski.isRented());
    }

    @Test
    void testAvailableSkis() {
        assertTrue(manager.getAvailableSkis().contains(ski));
        manager.rentSki(customer, ski, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "");
        assertFalse(manager.getAvailableSkis().contains(ski));
    }

    @Test
    void testOverdueRental() {
        manager.rentSki(customer, ski, LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(1), "Przeterminowane");
        List<Rental> overdue = manager.getOverdueRentals(LocalDateTime.now());
        assertEquals(1, overdue.size());
    }

    @Test
    void testCustomerSearch() {
        List<Customer> result = manager.searchCustomers("adam");
        assertEquals(1, result.size());
    }

    @Test
    void testSkiSearch() {
        List<Ski> result = manager.searchSkis("volkl");
        assertEquals(1, result.size());
    }
}
