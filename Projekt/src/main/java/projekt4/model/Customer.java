package projekt4.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa reprezentująca klienta wypożyczalni nart.
 */
public class Customer implements Serializable {
    /**
     * Imię klienta
     */
    private String firstName;
    /**
     * Nazwisko klienta
     */
    private String lastName;
    /**
     * Numer dokumentu klienta (unikalny)
     */
    private String documentNumber;
    /**
     * Dodatkowe uwagi lub opis
     */
    private String notes;

    /**
     * Tworzy nowego klienta.
     * @param firstName imię klienta
     * @param lastName nazwisko klienta
     * @param documentNumber numer dokumentu tożsamości klienta
     */
    public Customer(String firstName, String lastName, String documentNumber, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.notes = notes;
    }

    /**
     * Zwraca imię klienta.
     * @return imię
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Ustawia imię klienta.
     * @param firstName nowe imię
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Zwraca nazwisko klienta.
     * @return nazwisko
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Ustawia nazwisko klienta.
     * @param lastName nowe nazwisko
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Zwraca numer dokumentu tożsamości.
     * @return numer dokumentu
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Ustawia numer dokumentu tożsamości.
     * @param documentNumber nowy numer dokumentu
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    /**
     * @return dodatkowe uwagi
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes nowe uwagi
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return tekstowa reprezentacja klienta
     */
    @Override
    public String toString() {
        return String.format("%s %s [%s]", firstName, lastName, documentNumber);
    }

    /**
     * Porównuje klientów po numerze dokumentu.
     * @param o obiekt do porównania
     * @return true jeśli numery dokumentów są równe
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(documentNumber, customer.documentNumber);
    }

    /**
     * @return hash na podstawie numeru dokumentu
     */
    @Override
    public int hashCode() {
        return Objects.hash(documentNumber);
    }
}
