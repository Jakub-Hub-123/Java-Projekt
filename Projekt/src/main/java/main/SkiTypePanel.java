import javax.swing.*;
import java.awt.*;

public class SkiTypePanel extends JPanel {
    private final SkiRentalManager manager;
    private final DefaultListModel<SkiType> skiTypeListModel = new DefaultListModel<>();

    public SkiTypePanel(SkiRentalManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        JList<SkiType> list = new JList<>(skiTypeListModel);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JTextField nameField = new JTextField(10);
        JTextField descField = new JTextField(10);
        JButton addBtn = new JButton("Dodaj");
        JButton editBtn = new JButton("Edytuj");
        JButton deleteBtn = new JButton("Usuń");

        JPanel form = new JPanel(new BorderLayout());
        JPanel fields = new JPanel(new FlowLayout());
        fields.add(new JLabel("Nazwa:"));
        fields.add(nameField);
        fields.add(new JLabel("Opis:"));
        fields.add(descField);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);

        form.add(fields, BorderLayout.CENTER);
        form.add(buttons, BorderLayout.SOUTH);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            if (!name.isEmpty()) {
                SkiType type = new SkiType(name, desc);
                manager.addSkiType(type);
                skiTypeListModel.addElement(type);
                nameField.setText("");
                descField.setText("");
            }
        });

        editBtn.addActionListener(e -> {
            SkiType selected = list.getSelectedValue();
            if (selected != null) {
                selected.setName(nameField.getText().trim());
                selected.setDescription(descField.getText().trim());
                list.repaint();
            }
        });

        deleteBtn.addActionListener(e -> {
            SkiType selected = list.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Czy na pewno usunąć wybrany typ nart?", "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeSkiType(selected);
                    skiTypeListModel.removeElement(selected);
                    nameField.setText("");
                    descField.setText("");
                }
            }
        });

        list.addListSelectionListener(e -> {
            SkiType selected = list.getSelectedValue();
            if (selected != null) {
                nameField.setText(selected.getName());
                descField.setText(selected.getDescription());
            }
        });

        refresh();
    }

    public void refresh() {
        skiTypeListModel.clear();
        for (SkiType t : manager.getAllSkiTypes()) skiTypeListModel.addElement(t);
    }
}