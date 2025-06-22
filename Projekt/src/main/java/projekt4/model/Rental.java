package projekt4.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Klasa reprezentująca wypożyczenie nart przez klienta.
 */
public class Rental implements Serializable {

    // Status wypożyczenia
    public enum RentalStatus {
        ACTIVE, RETURNED, OVERDUE
    }

    private Customer customer;
    private Ski ski;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private RentalStatus status;
    private String notes;

    /**
     * Tworzy nowe wypożyczenie z domyślnym statusem ACTIVE.
     * @param customer klient
     * @param ski narty
     * @param dateFrom data rozpoczęcia
     * @param dateTo data zakończenia
     * @param notes uwagi
     */
    public Rental(Customer customer, Ski ski, LocalDateTime dateFrom, LocalDateTime dateTo, String notes) {
        this.customer = customer;
        this.ski = ski;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = RentalStatus.ACTIVE;
        this.notes = notes;
    }

    /**
     * @return klient
     */
    public Customer getCustomer() {
        return customer;
    }
    /**
     * @return narty
     */
    public Ski getSki() {
        return ski;
    }
    /**
     * @return data od
     */
    public LocalDateTime getDateFrom() {
        return dateFrom;
    }
    /**
     * @return data do
     */
    public LocalDateTime getDateTo() {
        return dateTo;
    }
    /**
     * @return status wypożyczenia
     */
    public RentalStatus getStatus() {
        return status;
    }
    /**
     * Ustawia nowy status wypożyczenia.
     * @param status nowy status
     */
    public void setStatus(RentalStatus status) {
        this.status = status;
    }
    // @return notatki
    public String getNotes() {
        return notes;
    }
    /**
     * Ustawia notatki do wypożyczenia.
     * @param notes notatki
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    /**
     * @param now aktualny czas
     * @return true jeśli wypożyczenie jest przeterminowane
     */
    public boolean isOverdue(LocalDateTime now) {
        return status == RentalStatus.ACTIVE && dateTo.isBefore(now);
    }

    /**
     * @return opis tekstowy wypożyczenia
     */
    @Override
    public String toString() {
        return String.format("Wypożyczenie: %s -> %s [%s - %s], Status: %s, Uwagi: %s",
                customer, ski, dateFrom, dateTo, status, notes);
    }

    /**
     * Porównuje wypożyczenia po kliencie, nartach i datach.
     * @param o obiekt
     * @return true jeśli pola się zgadzają
     */
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

    /**
     * @return hash na podstawie danych wypożyczenia
     */
    @Override
    public int hashCode() {
        return Objects.hash(customer, ski, dateFrom, dateTo);
    }
}
