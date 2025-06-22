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
 * Panel graficzny do obsługi wypożyczeń nart.
 * Umożliwia tworzenie nowych wypożyczeń, ich zwracanie oraz usuwanie.
 */
public class RentalPanel extends JPanel {
    /**
     * Manager danych wypożyczalni
     */
    private final RentalManager rentalManager;
    /**
     * Model listy wypożyczeń używany przez JList
     */
    private final DefaultListModel<Rental> rentalListModel;
    /**
     * Komponent graficzny wyświetlający listę wypożyczeń
     */
    private final JList<Rental> rentalJList;

    /**
     * Tworzy nowy panel wypożyczeń.
     *
     * @param rentalManager obiekt zarządzający danymi wypożyczalni
     */
    public RentalPanel(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.rentalListModel = new DefaultListModel<>();
        this.rentalJList = new JList<>(rentalListModel);

        setLayout(new BorderLayout());
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(rentalJList), BorderLayout.CENTER);

        refreshList();
    }

    /**
     * Tworzy pasek narzędziowy z przyciskami do zarządzania wypożyczeniami.
     *
     * @return panel z przyciskami
     */
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

    /**
     * Obsługuje akcję wypożyczenia nart.
     *
     * @param e zdarzenie akcji
     */
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

    /**
     * Obsługuje akcję zwrotu wypożyczenia.
     *
     * @param e zdarzenie akcji
     */
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

    /**
     * Obsługuje akcję usunięcia wypożyczenia.
     *
     * @param e zdarzenie akcji
     */
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

    /**
     * Odświeża listę wypożyczeń w panelu.
     */
    private void refreshList() {
        rentalListModel.clear();
        List<Rental> allRentals = rentalManager.getRentals();
        allRentals.forEach(rentalListModel::addElement);
    }
}