package projekt4.service;

import projekt4.model.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasa zarządzająca danymi wypożyczalni: narty, klienci, wypożyczenia.
 */
public class RentalManager implements Serializable {

    private final List<SkiType> skiTypes = new ArrayList<>();
    private final List<Ski> skis = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Rental> rentals = new ArrayList<>();

    // === Operacje na SkiType ===

    /**
     * Dodaje nowy typ nart
     */
    public void addSkiType(SkiType type) {
        skiTypes.add(type);
    }
    /**
     * Usuwa typ nart
     */
    public void removeSkiType(SkiType type) {
        skiTypes.remove(type);
    }
    /**
     * @return lista typów nart
     */
    public List<SkiType> getSkiTypes() {
        return Collections.unmodifiableList(skiTypes);
    }

    // === Operacje na Ski ===

    /**
     * Dodaje nowe narty
     */
    public void addSki(Ski ski) {
        skis.add(ski);
    }
    /**
     * Usuwa narty
     */
    public void removeSki(Ski ski) {
        skis.remove(ski);
    }
    /**
     * @return lista nart
     */
    public List<Ski> getSkis() {
        return Collections.unmodifiableList(skis);
    }

    // === Operacje na Customer ===

    /**
     * Dodaje klienta
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    /**
     * Usuwa klienta
     */
    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }
    /**
     * @return lista klientów
     */
    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    // === Operacje na Rental ===

    /**
     * Wypożycza narty klientowi.
     * @param customer klient
     * @param ski narty
     * @param from data od
     * @param to data do
     * @param notes uwagi
     */
    public void rentSki(Customer customer, Ski ski, LocalDateTime from, LocalDateTime to, String notes) {
        if (ski.isRented()) throw new IllegalStateException("Narty są już wypożyczone");
        ski.setRented(true);
        rentals.add(new Rental(customer, ski, from, to, notes));
    }

    /**
     * Zwraca narty.
     * @param rental wypożyczenie
     * @param returnNotes notatki zwrotu
     */
    public void returnSki(Rental rental, String returnNotes) {
        rental.setStatus(Rental.RentalStatus.RETURNED);
        rental.getSki().setRented(false);
        rental.setNotes(rental.getNotes() + " | Zwrot: " + returnNotes);
    }

    /**
     * Usuwa wypożyczenie.
     * @param rental wypożyczenie do usunięcia
     */
    public void removeRental(Rental rental) {
        rental.getSki().setRented(false);
        rentals.remove(rental);
    }

    /**
     * @return lista wypożyczeń
     */
    public List<Rental> getRentals() {
        return Collections.unmodifiableList(rentals);
    }

    // === Raportowanie ===

    /**
     * @return lista dostępnych nart
     */
    public List<Ski> getAvailableSkis() {
        return skis.stream().filter(ski -> !ski.isRented()).collect(Collectors.toList());
    }

    /**
     * @param now aktualna data
     * @return lista przeterminowanych wypożyczeń
     */
    public List<Rental> getOverdueRentals(LocalDateTime now) {
        return rentals.stream()
                .filter(r -> r.isOverdue(now))
                .collect(Collectors.toList());
    }

    /**
     * @return lista aktywnych wypożyczeń
     */
    public List<Rental> getActiveRentals() {
        return rentals.stream()
                .filter(r -> r.getStatus() == Rental.RentalStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    // === Wyszukiwanie ===

    /**
     * Szuka klientów po imieniu, nazwisku lub numerze dokumentu.
     * @param query tekst do wyszukania
     * @return lista pasujących klientów
     */
    public List<Customer> searchCustomers(String query) {
        return customers.stream()
                .filter(c -> c.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        c.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                        c.getDocumentNumber().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Szuka nart po marce lub modelu.
     * @param query tekst do wyszukania
     * @return lista pasujących nart
     */
    public List<Ski> searchSkis(String query) {
        return skis.stream()
                .filter(s -> s.getBrand().toLowerCase().contains(query.toLowerCase()) ||
                        s.getModel().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
