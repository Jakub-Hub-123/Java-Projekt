package projekt4.gui;

import projekt4.model.Customer;
import projekt4.service.RentalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Panel GUI odpowiedzialny za zarządzanie klientami w aplikacji wypożyczalni nart.
 * Pozwala na dodawanie, edytowanie oraz usuwanie klientów.
 */
public class CustomerPanel extends JPanel {

    /** Obiekt zarządzający danymi wypożyczalni. */
    private final RentalManager rentalManager;

    /** Model listy klientów, wykorzystywany do odświeżania widoku. */
    private final DefaultListModel<Customer> customerListModel;

    /** Graficzna lista klientów wyświetlana użytkownikowi. */
    private final JList<Customer> customerJList;

    /**
     * Konstruktor inicjalizujący panel klienta.
     *
     * @param rentalManager obiekt zarządzający danymi aplikacji
     */
    public CustomerPanel(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.customerListModel = new DefaultListModel<>();
        this.customerJList = new JList<>(customerListModel);

        setLayout(new BorderLayout());
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(customerJList), BorderLayout.CENTER);

        refreshList();
    }

    /**
     * Tworzy pasek narzędzi z przyciskami dodaj/edytuj/usuń.
     *
     * @return panel z przyciskami
     */
    private JPanel createToolbar() {
        JPanel panel = new JPanel();

        JButton addBtn = new JButton("Dodaj");
        JButton editBtn = new JButton("Edytuj");
        JButton deleteBtn = new JButton("Usuń");

        addBtn.addActionListener(this::onAdd);
        editBtn.addActionListener(this::onEdit);
        deleteBtn.addActionListener(this::onDelete);

        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);

        return panel;
    }

    /**
     * Obsługa dodawania nowego klienta – wyświetla formularz i zapisuje dane.
     *
     * @param e zdarzenie kliknięcia przycisku
     */
    private void onAdd(ActionEvent e) {
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField docNumberField = new JTextField();
        JTextField notesField = new JTextField();

        Object[] fields = {
                "Imię:", firstNameField,
                "Nazwisko:", lastNameField,
                "Nr dokumentu:", docNumberField,
                "Opis/uwagi:", notesField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Dodaj klienta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Customer customer = new Customer(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    docNumberField.getText(),
                    notesField.getText()
            );
            rentalManager.addCustomer(customer);
            refreshList();
        }
    }

    /**
     * Obsługa edycji wybranego klienta – umożliwia modyfikację danych.
     *
     * @param e zdarzenie kliknięcia przycisku
     */
    private void onEdit(ActionEvent e) {
        Customer selected = customerJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Zaznacz klienta do edycji.");
            return;
        }

        JTextField firstNameField = new JTextField(selected.getFirstName());
        JTextField lastNameField = new JTextField(selected.getLastName());
        JTextField docNumberField = new JTextField(selected.getDocumentNumber());
        JTextField notesField = new JTextField(selected.getNotes());

        Object[] fields = {
                "Imię:", firstNameField,
                "Nazwisko:", lastNameField,
                "Nr dokumentu:", docNumberField,
                "Opis/uwagi:", notesField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Edytuj klienta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selected.setFirstName(firstNameField.getText());
            selected.setLastName(lastNameField.getText());
            selected.setDocumentNumber(docNumberField.getText());
            selected.setNotes(notesField.getText());
            refreshList();
        }
    }

    /**
     * Obsługa usuwania wybranego klienta z listy.
     *
     * @param e zdarzenie kliknięcia przycisku
     */
    private void onDelete(ActionEvent e) {
        Customer selected = customerJList.getSelectedValue();
        if (selected != null) {
            rentalManager.removeCustomer(selected);
            refreshList();
        } else {
            JOptionPane.showMessageDialog(this, "Zaznacz klienta do usunięcia.");
        }
    }

    /**
     * Odświeża listę klientów wyświetlaną w panelu.
     */
    private void refreshList() {
        customerListModel.clear();
        List<Customer> customers = rentalManager.getCustomers();
        customers.forEach(customerListModel::addElement);
    }
}