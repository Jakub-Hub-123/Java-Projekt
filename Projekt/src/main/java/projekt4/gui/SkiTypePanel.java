package projekt4.gui;

import projekt4.model.SkiType;
import projekt4.service.RentalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Panel GUI do zarządzania typami nart.
 */
public class SkiTypePanel extends JPanel {

    private final RentalManager rentalManager;
    private final DefaultListModel<SkiType> skiTypeListModel;
    private final JList<SkiType> skiTypeJList;

    public SkiTypePanel(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.skiTypeListModel = new DefaultListModel<>();
        this.skiTypeJList = new JList<>(skiTypeListModel);

        setLayout(new BorderLayout());
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(skiTypeJList), BorderLayout.CENTER);

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
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();

        Object[] fields = {
                "Nazwa:", nameField,
                "Opis:", descField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Dodaj typ nart", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            SkiType newType = new SkiType(nameField.getText(), descField.getText());
            rentalManager.addSkiType(newType);
            refreshList();
        }
    }

    private void onEdit(ActionEvent e) {
        SkiType selected = skiTypeJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Zaznacz typ nart do edycji.");
            return;
        }

        JTextField nameField = new JTextField(selected.getName());
        JTextField descField = new JTextField(selected.getDescription());

        Object[] fields = {
                "Nazwa:", nameField,
                "Opis:", descField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Edytuj typ nart", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            selected.setName(nameField.getText());
            selected.setDescription(descField.getText());
            refreshList();
        }
    }

    private void onDelete(ActionEvent e) {
        SkiType selected = skiTypeJList.getSelectedValue();
        if (selected != null) {
            rentalManager.removeSkiType(selected);
            refreshList();
        } else {
            JOptionPane.showMessageDialog(this, "Zaznacz typ nart do usunięcia.");
        }
    }

    private void refreshList() {
        skiTypeListModel.clear();
        List<SkiType> types = rentalManager.getSkiTypes();
        types.forEach(skiTypeListModel::addElement);
    }
}
