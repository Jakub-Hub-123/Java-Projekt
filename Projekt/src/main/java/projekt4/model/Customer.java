package projekt4.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa reprezentująca klienta wypożyczalni nart.
 */
public class Customer implements Serializable {
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String notes;

    public Customer(String firstName, String lastName, String documentNumber, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.notes = notes;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("%s %s [%s]", firstName, lastName, documentNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(documentNumber, customer.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNumber);
    }
}
