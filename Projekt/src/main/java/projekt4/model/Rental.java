package projekt4.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Klasa reprezentująca wypożyczenie nart przez klienta.
 */
public class Rental implements Serializable {

    public enum RentalStatus {
        ACTIVE, RETURNED, OVERDUE
    }

    private Customer customer;
    private Ski ski;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private RentalStatus status;
    private String notes;

    public Rental(Customer customer, Ski ski, LocalDateTime dateFrom, LocalDateTime dateTo, String notes) {
        this.customer = customer;
        this.ski = ski;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = RentalStatus.ACTIVE;
        this.notes = notes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Ski getSki() {
        return ski;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isOverdue(LocalDateTime now) {
        return status == RentalStatus.ACTIVE && dateTo.isBefore(now);
    }

    @Override
    public String toString() {
        return String.format("Wypożyczenie: %s -> %s [%s - %s], Status: %s, Uwagi: %s",
                customer, ski, dateFrom, dateTo, status, notes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return Objects.equals(customer, rental.customer) &&
                Objects.equals(ski, rental.ski) &&
                Objects.equals(dateFrom, rental.dateFrom) &&
                Objects.equals(dateTo, rental.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, ski, dateFrom, dateTo);
    }
}
