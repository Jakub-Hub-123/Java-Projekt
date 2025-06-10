import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class okienko {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new okienko().createGUI();
        });
    }

    public void createGUI() {
        JFrame frame = new JFrame("Wypożyczalnia nart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(5, 1));

        JTextField nameField = new JTextField();
        JComboBox<String> skiTypeBox = new JComboBox<>(new String[] {
                "Zjazdowe", "Biegowe", "Freeride", "Skiturowe"
        });

        JButton rentButton = new JButton("Wypożycz");
        JLabel infoLabel = new JLabel("");

        frame.add(new JLabel("Podaj imię:"));
        frame.add(nameField);
        frame.add(new JLabel("Wybierz typ nart:"));
        frame.add(skiTypeBox);
        frame.add(rentButton);
        frame.add(infoLabel);

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String skiType = (String) skiTypeBox.getSelectedItem();

                if (name.isEmpty()) {
                    infoLabel.setText("Wpisz swoje imię!");
                } else {
                    infoLabel.setText("✅ " + name + " wypożyczył(a) narty: " + skiType);
                }
            }
        });

        frame.setVisible(true);
    }
}
