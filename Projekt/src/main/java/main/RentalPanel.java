import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class RentalPanel extends JPanel {
    private final SkiRentalManager manager;
    private final DefaultListModel<Rental> rentalListModel = new DefaultListModel<>();

    public RentalPanel(SkiRentalManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        JList<Rental> rentalList = new JList<>(rentalListModel);
        add(new JScrollPane(rentalList), BorderLayout.CENTER);

        JComboBox<User> userCombo = new JComboBox<>();
        JComboBox<Ski> skiCombo = new JComboBox<>();
        JTextField fromField = new JTextField("2025-06-20T10:00");
        JTextField toField = new JTextField("2025-06-22T18:00");
        JTextField notesField = new JTextField();
        JButton rentBtn = new JButton("Wypożycz");
        JButton deleteBtn = new JButton("Usuń");

        JPanel fields = new JPanel(new GridLayout(5, 2));
        fields.add(new JLabel("Użytkownik:")); fields.add(userCombo);
        fields.add(new JLabel("Narta:")); fields.add(skiCombo);
        fields.add(new JLabel("Od (ISO format):")); fields.add(fromField);
        fields.add(new JLabel("Do (ISO format):")); fields.add(toField);
        fields.add(new JLabel("Uwagi:")); fields.add(notesField);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(rentBtn);
        buttons.add(deleteBtn);

        JPanel form = new JPanel(new BorderLayout());
        form.add(fields, BorderLayout.CENTER);
        form.add(buttons, BorderLayout.SOUTH);
        add(form, BorderLayout.SOUTH);

        rentBtn.addActionListener(e -> {
            User user = (User) userCombo.getSelectedItem();
            Ski ski = (Ski) skiCombo.getSelectedItem();
            String fromText = fromField.getText().trim();
            String toText = toField.getText().trim();
            String notes = notesField.getText().trim();

            try {
                LocalDateTime from = LocalDateTime.parse(fromText);
                LocalDateTime to = LocalDateTime.parse(toText);

                if (user == null || ski == null) {
                    JOptionPane.showMessageDialog(this, "Wybierz użytkownika i nartę.");
                    return;
                }

                Rental rental = new Rental(ski, user, from, to, RentalStatus.RENTED, notes);
                manager.addRental(rental);
                rentalListModel.addElement(rental);

                JOptionPane.showMessageDialog(this, "Dodano wypożyczenie.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Nieprawidłowy format daty. Użyj ISO (np. 2025-06-20T10:00).");
            }
        });

        deleteBtn.addActionListener(e -> {
            Rental selected = rentalList.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Czy na pewno usunąć wybrane wypożyczenie?", "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeRental(selected);
                    rentalListModel.removeElement(selected);
                }
            }
        });

        userCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                userCombo.removeAllItems();
                for (User u : manager.getAllUsers()) userCombo.addItem(u);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });

        skiCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                skiCombo.removeAllItems();
                try {
                    LocalDateTime from = LocalDateTime.parse(fromField.getText().trim());
                    LocalDateTime to = LocalDateTime.parse(toField.getText().trim());
                    for (Ski s : manager.getAvailableSkis(from, to)) skiCombo.addItem(s);
                } catch (Exception ex) {
                    // ignoruj błędy formatu
                }
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });

        refresh();
    }

    public void refresh() {
        rentalListModel.clear();
        for (Rental r : manager.getAllRentals()) rentalListModel.addElement(r);
    }
}