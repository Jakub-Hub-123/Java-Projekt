import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {
    private final SkiRentalManager manager;
    private final DefaultListModel<User> userListModel = new DefaultListModel<>();

    public UserPanel(SkiRentalManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        JList<User> list = new JList<>(userListModel);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField docNumField = new JTextField();
        JTextField descField = new JTextField();
        JButton addBtn = new JButton("Dodaj");
        JButton editBtn = new JButton("Edytuj");
        JButton deleteBtn = new JButton("Usuń");

        JPanel form = new JPanel(new BorderLayout());
        JPanel fields = new JPanel(new GridLayout(4, 2));
        fields.add(new JLabel("Imię:"));         fields.add(firstNameField);
        fields.add(new JLabel("Nazwisko:"));     fields.add(lastNameField);
        fields.add(new JLabel("Nr dokumentu:")); fields.add(docNumField);
        fields.add(new JLabel("Opis:"));         fields.add(descField);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);

        form.add(fields, BorderLayout.CENTER);
        form.add(buttons, BorderLayout.SOUTH);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String first = firstNameField.getText().trim();
            String last = lastNameField.getText().trim();
            String doc = docNumField.getText().trim();
            String desc = descField.getText().trim();
            if (!first.isEmpty() && !last.isEmpty() && !doc.isEmpty()) {
                User user = new User(first, last, doc, desc);
                manager.addUser(user);
                userListModel.addElement(user);
                firstNameField.setText("");
                lastNameField.setText("");
                docNumField.setText("");
                descField.setText("");
            }
        });

        editBtn.addActionListener(e -> {
            User selected = list.getSelectedValue();
            if (selected != null) {
                selected.setFirstName(firstNameField.getText().trim());
                selected.setLastName(lastNameField.getText().trim());
                selected.setDocumentNumber(docNumField.getText().trim());
                selected.setDescription(descField.getText().trim());
                list.repaint();
            }
        });

        deleteBtn.addActionListener(e -> {
            User selected = list.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Czy na pewno usunąć wybranego użytkownika?", "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeUser(selected);
                    userListModel.removeElement(selected);
                    firstNameField.setText("");
                    lastNameField.setText("");
                    docNumField.setText("");
                    descField.setText("");
                }
            }
        });

        list.addListSelectionListener(e -> {
            User selected = list.getSelectedValue();
            if (selected != null) {
                firstNameField.setText(selected.getFirstName());
                lastNameField.setText(selected.getLastName());
                docNumField.setText(selected.getDocumentNumber());
                descField.setText(selected.getDescription());
            }
        });

        refresh();
    }

    public void refresh() {
        userListModel.clear();
        for (User u : manager.getAllUsers()) userListModel.addElement(u);
    }
}