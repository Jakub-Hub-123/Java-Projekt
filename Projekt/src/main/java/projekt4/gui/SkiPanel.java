package projekt4.gui;

import projekt4.model.Ski;
import projekt4.model.SkiType;
import projekt4.service.RentalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Panel do zarządzania nartami w systemie wypożyczalni.
 * Umożliwia dodawanie, edytowanie oraz usuwanie nart.
 */
public class SkiPanel extends JPanel {

    private final RentalManager rentalManager;
    private final DefaultListModel<Ski> skiListModel;
    private final JList<Ski> skiJList;

    /**
     * Tworzy panel do zarządzania nartami.
     * @param rentalManager menedżer wypożyczeń zawierający listę nart
     */
    public SkiPanel(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.skiListModel = new DefaultListModel<>();
        this.skiJList = new JList<>(skiListModel);

        setLayout(new BorderLayout());
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(skiJList), BorderLayout.CENTER);

        refreshList();
    }

    /**
     * Tworzy pasek narzędzi z przyciskami akcji.
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
     * Obsługuje dodawanie nowej pary nart.
     */
    private void onAdd(ActionEvent e) {
        if (rentalManager.getSkiTypes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Najpierw dodaj typ nart.");
            return;
        }

        JComboBox<SkiType> typeBox = new JComboBox<>(rentalManager.getSkiTypes().toArray(new SkiType[0]));
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField bindingsField = new JTextField();
        JTextField lengthField = new JTextField();

        Object[] fields = {
                "Typ:", typeBox,
                "Marka:", brandField,
                "Model:", modelField,
                "Wiązania:", bindingsField,
                "Długość (cm):", lengthField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Dodaj narty", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int length = Integer.parseInt(lengthField.getText());
                Ski ski = new Ski((SkiType) typeBox.getSelectedItem(), brandField.getText(), modelField.getText(),
                        bindingsField.getText(), length);
                rentalManager.addSki(ski);
                refreshList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Niepoprawna długość nart.");
            }
        }
    }

    /**
     * Obsługuje edycję zaznaczonej pary nart.
     */
    private void onEdit(ActionEvent e) {
        Ski selected = skiJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Zaznacz narty do edycji.");
            return;
        }

        JComboBox<SkiType> typeBox = new JComboBox<>(rentalManager.getSkiTypes().toArray(new SkiType[0]));
        typeBox.setSelectedItem(selected.getType());

        JTextField brandField = new JTextField(selected.getBrand());
        JTextField modelField = new JTextField(selected.getModel());
        JTextField bindingsField = new JTextField(selected.getBindings());
        JTextField lengthField = new JTextField(String.valueOf(selected.getLength()));

        Object[] fields = {
                "Typ:", typeBox,
                "Marka:", brandField,
                "Model:", modelField,
                "Wiązania:", bindingsField,
                "Długość (cm):", lengthField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Edytuj narty", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int length = Integer.parseInt(lengthField.getText());
                selected.setType((SkiType) typeBox.getSelectedItem());
                selected.setBrand(brandField.getText());
                selected.setModel(modelField.getText());
                selected.setBindings(bindingsField.getText());
                selected.setLength(length);
                refreshList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Niepoprawna długość nart.");
            }
        }
    }

    /**
     * Obsługuje usuwanie zaznaczonej pary nart.
     */
    private void onDelete(ActionEvent e) {
        Ski selected = skiJList.getSelectedValue();
        if (selected != null) {
            rentalManager.removeSki(selected);
            refreshList();
        } else {
            JOptionPane.showMessageDialog(this, "Zaznacz narty do usunięcia.");
        }
    }

    /**
     * Odświeża listę nart wyświetlanych w panelu.
     */
    private void refreshList() {
        skiListModel.clear();
        List<Ski> skis = rentalManager.getSkis();
        skis.forEach(skiListModel::addElement);
    }
}