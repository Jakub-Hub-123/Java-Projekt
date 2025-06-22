import javax.swing.*;
import java.awt.*;

public class ReturnPanel extends JPanel {
    private final SkiRentalManager manager;
    private final DefaultListModel<Rental> returnListModel = new DefaultListModel<>();
    private JList<Rental> returnRentalList;
    private JTextField returnNotesField;

    public ReturnPanel(SkiRentalManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        returnRentalList = new JList<>(returnListModel);
        returnRentalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(returnRentalList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5,5));
        returnNotesField = new JTextField();
        JButton returnBtn = new JButton("Zwróć narty");

        bottomPanel.add(new JLabel("Uwagi przy zwrocie:"), BorderLayout.WEST);
        bottomPanel.add(returnNotesField, BorderLayout.CENTER);
        bottomPanel.add(returnBtn, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        returnBtn.addActionListener(e -> {
            Rental selectedRental = returnRentalList.getSelectedValue();
            if (selectedRental == null) {
                JOptionPane.showMessageDialog(this, "Wybierz wypożyczenie do zwrotu.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String notes = returnNotesField.getText();
                manager.returnRental(selectedRental, notes);
                JOptionPane.showMessageDialog(this, "Narty zostały oznaczone jako zwrócone.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                refresh();
                returnNotesField.setText("");
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        refresh();
    }

    public void refresh() {
        returnListModel.clear();
        for (Rental r : manager.getCurrentRentals()) returnListModel.addElement(r);
    }
}