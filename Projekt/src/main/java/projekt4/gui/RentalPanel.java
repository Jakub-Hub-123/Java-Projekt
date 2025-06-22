package projekt4.gui;

import projekt4.model.Customer;
import projekt4.model.Rental;
import projekt4.model.Ski;
import projekt4.service.RentalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Panel GUI do zarządzania wypożyczeniami i zwrotami nart.
 */
public class RentalPanel extends JPanel {

    private final RentalManager rentalManager;
    private final DefaultListModel<Rental> rentalListModel;
    private final JList<Rental> rentalJList;

    public RentalPanel(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.rentalListModel = new DefaultListModel<>();
        this.rentalJList = new JList<>(rentalListModel);

        setLayout(new BorderLayout());
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(rentalJList), BorderLayout.CENTER);

        refreshList();
    }

    private JPanel createToolbar() {
        JPanel panel = new JPanel();

        JButton rentBtn = new JButton("Wypożycz");
        JButton returnBtn = new JButton("Zwrot");
        JButton deleteBtn = new JButton("Usuń");

        rentBtn.addActionListener(this::onRent);
        returnBtn.addActionListener(this::onReturn);
        deleteBtn.addActionListener(this::onDelete);

        panel.add(rentBtn);
        panel.add(returnBtn);
        panel.add(deleteBtn);

        return panel;
    }

    private void onRent(ActionEvent e) {
        if (rentalManager.getCustomers().isEmpty() || rentalManager.getAvailableSkis().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dodaj klienta i dostępne narty.");
            return;
        }

        JComboBox<Customer> customerBox = new JComboBox<>(rentalManager.getCustomers().toArray(new Customer[0]));
        JComboBox<Ski> skiBox = new JComboBox<>(rentalManager.getAvailableSkis().toArray(new Ski[0]));
        JTextField fromField = new JTextField(LocalDateTime.now().toString());
        JTextField toField = new JTextField(LocalDateTime.now().plusDays(1).toString());
        JTextField notesField = new JTextField();

        Object[] fields = {
                "Klient:", customerBox,
                "Narty:", skiBox,
                "Data od (rrrr-MM-ddTHH:mm):", fromField,
                "Data do (rrrr-MM-ddTHH:mm):", toField,
                "Uwagi:", notesField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Nowe wypożyczenie", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                LocalDateTime from = LocalDateTime.parse(fromField.getText());
                LocalDateTime to = LocalDateTime.parse(toField.getText());

                rentalManager.rentSki(
                        (Customer) customerBox.getSelectedItem(),
                        (Ski) skiBox.getSelectedItem(),
                        from, to,
                        notesField.getText()
                );
                refreshList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd formatu daty.");
            }
        }
    }

    private void onReturn(ActionEvent e) {
        Rental selected = rentalJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Zaznacz wypożyczenie do zwrotu.");
            return;
        }

        if (selected.getStatus() == Rental.RentalStatus.RETURNED) {
            JOptionPane.showMessageDialog(this, "To wypożyczenie zostało już zwrócone.");
            return;
        }

        String note = JOptionPane.showInputDialog(this, "Uwagi do zwrotu:", "Zwrot", JOptionPane.PLAIN_MESSAGE);
        if (note != null) {
            rentalManager.returnSki(selected, note);
            refreshList();
        }
    }

    private void onDelete(ActionEvent e) {
        Rental selected = rentalJList.getSelectedValue();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Czy na pewno usunąć to wypożyczenie?");
            if (confirm == JOptionPane.YES_OPTION) {
                rentalManager.removeRental(selected);
                refreshList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Zaznacz wypożyczenie do usunięcia.");
        }
    }

    private void refreshList() {
        rentalListModel.clear();
        List<Rental> allRentals = rentalManager.getRentals();
        allRentals.forEach(rentalListModel::addElement);
    }
}
