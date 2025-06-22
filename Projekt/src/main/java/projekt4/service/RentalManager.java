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
    public void addSkiType(SkiType type) {
        skiTypes.add(type);
    }

    public void removeSkiType(SkiType type) {
        skiTypes.remove(type);
    }

    public List<SkiType> getSkiTypes() {
        return Collections.unmodifiableList(skiTypes);
    }

    // === Operacje na Ski ===
    public void addSki(Ski ski) {
        skis.add(ski);
    }

    public void removeSki(Ski ski) {
        skis.remove(ski);
    }

    public List<Ski> getSkis() {
        return Collections.unmodifiableList(skis);
    }

    // === Operacje na Customer ===
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    // === Operacje na Rental ===
    public void rentSki(Customer customer, Ski ski, LocalDateTime from, LocalDateTime to, String notes) {
        if (ski.isRented()) throw new IllegalStateException("Narty są już wypożyczone");
        ski.setRented(true);
        rentals.add(new Rental(customer, ski, from, to, notes));
    }

    public void returnSki(Rental rental, String returnNotes) {
        rental.setStatus(Rental.RentalStatus.RETURNED);
        rental.getSki().setRented(false);
        rental.setNotes(rental.getNotes() + " | Zwrot: " + returnNotes);
    }

    public void removeRental(Rental rental) {
        rental.getSki().setRented(false);
        rentals.remove(rental);
    }

    public List<Rental> getRentals() {
        return Collections.unmodifiableList(rentals);
    }

    // === Raportowanie ===
    public List<Ski> getAvailableSkis() {
        return skis.stream().filter(ski -> !ski.isRented()).collect(Collectors.toList());
    }

    public List<Rental> getOverdueRentals(LocalDateTime now) {
        return rentals.stream()
                .filter(r -> r.isOverdue(now))
                .collect(Collectors.toList());
    }

    public List<Rental> getActiveRentals() {
        return rentals.stream()
                .filter(r -> r.getStatus() == Rental.RentalStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    // === Wyszukiwanie ===
    public List<Customer> searchCustomers(String query) {
        return customers.stream()
                .filter(c -> c.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        c.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                        c.getDocumentNumber().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Ski> searchSkis(String query) {
        return skis.stream()
                .filter(s -> s.getBrand().toLowerCase().contains(query.toLowerCase()) ||
                        s.getModel().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
