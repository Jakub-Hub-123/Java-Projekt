package projekt4.gui;

import projekt4.model.Customer;
import projekt4.service.RentalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Panel GUI do zarządzania klientami wypożyczalni.
 */
public class CustomerPanel extends JPanel {

    private final RentalManager rentalManager;
    private final DefaultListModel<Customer> customerListModel;
    private final JList<Customer> customerJList;

    public CustomerPanel(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.customerListModel = new DefaultListModel<>();
        this.customerJList = new JList<>(customerListModel);

        setLayout(new BorderLayout());
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(customerJList), BorderLayout.CENTER);

        refreshList();
    }

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

    private void onDelete(ActionEvent e) {
        Customer selected = customerJList.getSelectedValue();
        if (selected != null) {
            rentalManager.removeCustomer(selected);
            refreshList();
        } else {
            JOptionPane.showMessageDialog(this, "Zaznacz klienta do usunięcia.");
        }
    }

    private void refreshList() {
        customerListModel.clear();
        List<Customer> customers = rentalManager.getCustomers();
        customers.forEach(customerListModel::addElement);
    }
}
