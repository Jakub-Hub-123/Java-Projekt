import javax.swing.*;
import java.awt.*;

public class SkiPanel extends JPanel {
    private final SkiRentalManager manager;
    private final DefaultListModel<Ski> skiListModel = new DefaultListModel<>();

    public SkiPanel(SkiRentalManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        JList<Ski> list = new JList<>(skiListModel);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JComboBox<SkiType> typeCombo = new JComboBox<>();
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField bindingsField = new JTextField();
        JTextField lengthField = new JTextField();
        JButton addBtn = new JButton("Dodaj");
        JButton editBtn = new JButton("Edytuj");
        JButton deleteBtn = new JButton("Usuń");

        JPanel form = new JPanel(new BorderLayout());
        JPanel fields = new JPanel(new GridLayout(5, 2));
        fields.add(new JLabel("Typ:"));         fields.add(typeCombo);
        fields.add(new JLabel("Marka:"));       fields.add(brandField);
        fields.add(new JLabel("Model:"));       fields.add(modelField);
        fields.add(new JLabel("Wiązania:"));    fields.add(bindingsField);
        fields.add(new JLabel("Długość (cm):"));fields.add(lengthField);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);

        form.add(fields, BorderLayout.CENTER);
        form.add(buttons, BorderLayout.SOUTH);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            SkiType selectedType = (SkiType) typeCombo.getSelectedItem();
            if (selectedType == null) return;
            try {
                int length = Integer.parseInt(lengthField.getText().trim());
                Ski ski = new Ski(selectedType, brandField.getText().trim(), modelField.getText().trim(), bindingsField.getText().trim(), length);
                manager.addSki(ski);
                skiListModel.addElement(ski);
                brandField.setText("");
                modelField.setText("");
                bindingsField.setText("");
                lengthField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Długość musi być liczbą!");
            }
        });

        editBtn.addActionListener(e -> {
            Ski selected = list.getSelectedValue();
            SkiType selectedType = (SkiType) typeCombo.getSelectedItem();
            if (selected != null && selectedType != null) {
                try {
                    selected.setType(selectedType);
                    selected.setBrand(brandField.getText().trim());
                    selected.setModel(modelField.getText().trim());
                    selected.setBindings(bindingsField.getText().trim());
                    selected.setLength(Integer.parseInt(lengthField.getText().trim()));
                    list.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Długość musi być liczbą!");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            Ski selected = list.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Czy na pewno usunąć wybraną nartę?", "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeSki(selected);
                    skiListModel.removeElement(selected);
                    brandField.setText("");
                    modelField.setText("");
                    bindingsField.setText("");
                    lengthField.setText("");
                }
            }
        });

        list.addListSelectionListener(e -> {
            Ski selected = list.getSelectedValue();
            if (selected != null) {
                typeCombo.setSelectedItem(selected.getType());
                brandField.setText(selected.getBrand());
                modelField.setText(selected.getModel());
                bindingsField.setText(selected.getBindings());
                lengthField.setText(String.valueOf(selected.getLength()));
            }
        });

        typeCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                typeCombo.removeAllItems();
                for (SkiType t : manager.getAllSkiTypes()) typeCombo.addItem(t);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });

        refresh();
    }

    public void refresh() {
        skiListModel.clear();
        for (Ski s : manager.getAllSkis()) skiListModel.addElement(s);
    }
}