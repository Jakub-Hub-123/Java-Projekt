// importy potrzebnych bibliotek
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Główna klasa aplikacji Wypożyczalni Nart.
 */
public class Wypozyczalnia {

    // Elementy do wprowadzania danych
    JFrame frame;
    JTextField imie, nazwisko, nrDokumentu;
    JComboBox<String> typNart, markaNart, modelNart, wiazaniaNart, dlugoscNart;
    JTextField dataOd, dataDo, statusNart, uwagi;
    JTextArea textArea;

    // Lista wypożyczeń
    ArrayList<String> wypozyczenia;

    // Pula wątków do zapisu i odczytu
    ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Wypozyczalnia app = new Wypozyczalnia();
        app.createWindow();
    }

    /**
     * Buduje i wyświetla aplikację.
     */
    public void createWindow() {
        frame = new JFrame("Wypożyczalnia Nart");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        wypozyczenia = new ArrayList<>();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // PANEL: Dane klienta
        JPanel clientPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        clientPanel.setBorder(new TitledBorder("Dane klienta"));
        clientPanel.add(new JLabel("Imię:"));
        imie = new JTextField();
        clientPanel.add(imie);
        clientPanel.add(new JLabel("Nazwisko:"));
        nazwisko = new JTextField();
        clientPanel.add(nazwisko);
        clientPanel.add(new JLabel("Nr dokumentu:"));
        nrDokumentu = new JTextField();
        clientPanel.add(nrDokumentu);

        // PANEL: Dane nart
        JPanel skiPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        skiPanel.setBorder(new TitledBorder("Dane nart"));
        skiPanel.add(new JLabel("Typ nart:"));
        typNart = new JComboBox<>(new String[]{"", "Zjazdowe", "Biegowe", "Freeride", "Skiturowe"});
        typNart.setSelectedIndex(0);
        skiPanel.add(typNart);
        skiPanel.add(new JLabel("Marka:"));
        markaNart = new JComboBox<>(new String[]{"", "Atomic", "Fischer", "Rossignol", "Salomon", "Head"});
        markaNart.setSelectedIndex(0);
        skiPanel.add(markaNart);
        skiPanel.add(new JLabel("Model:"));
        modelNart = new JComboBox<>(new String[]{"", "Redster X9", "Ranger 102", "Hero Elite", "QST 99", "Supershape"});
        modelNart.setSelectedIndex(0);
        skiPanel.add(modelNart);
        skiPanel.add(new JLabel("Wiązania:"));
        wiazaniaNart = new JComboBox<>(new String[]{"", "Look NX", "Tyrolia Attack", "Marker Griffon", "Salomon Z12"});
        wiazaniaNart.setSelectedIndex(0);
        skiPanel.add(wiazaniaNart);
        skiPanel.add(new JLabel("Długość (cm):"));
        dlugoscNart = new JComboBox<>(new String[]{"", "140", "150", "160", "170", "180", "190"});
        dlugoscNart.setSelectedIndex(0);
        skiPanel.add(dlugoscNart);

        // PANEL: Szczegóły wypożyczenia
        JPanel rentPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        rentPanel.setBorder(new TitledBorder("Szczegóły wypożyczenia"));
        rentPanel.add(new JLabel("Data od (YYYY-MM-DD):"));
        dataOd = new JTextField();
        rentPanel.add(dataOd);
        rentPanel.add(new JLabel("Data do (YYYY-MM-DD):"));
        dataDo = new JTextField();
        rentPanel.add(dataDo);
        rentPanel.add(new JLabel("Status:"));
        statusNart = new JTextField();
        rentPanel.add(statusNart);
        rentPanel.add(new JLabel("Uwagi:"));
        uwagi = new JTextField();
        rentPanel.add(uwagi);

        // PANEL: Przyciski
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton rentButton = new JButton("Wypożycz");
        rentButton.addActionListener(e -> threadPool.execute(this::rentSki));
        buttonPanel.add(rentButton);
        JButton saveButton = new JButton("Zapisz dane");
        saveButton.addActionListener(e -> threadPool.execute(this::saveToFile));
        buttonPanel.add(saveButton);
        JButton loadButton = new JButton("Wczytaj dane");
        loadButton.addActionListener(e -> threadPool.execute(this::loadFromFile));
        buttonPanel.add(loadButton);
        JButton reportButton = new JButton("Pokaż aktywne wypożyczenia");
        reportButton.addActionListener(e -> showActiveRentals());
        buttonPanel.add(reportButton);

        // Dodanie sekcji do głównego panelu
        mainPanel.add(clientPanel);
        mainPanel.add(skiPanel);
        mainPanel.add(rentPanel);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    /**
     * Tworzy wpis wypożyczenia i dodaje go do listy i pola tekstowego.
     */
    public void rentSki() {
        String name = imie.getText();
        String surname = nazwisko.getText();
        String docID = nrDokumentu.getText();
        String type = (String) typNart.getSelectedItem();
        String brand = (String) markaNart.getSelectedItem();
        String model = (String) modelNart.getSelectedItem();
        String binding = (String) wiazaniaNart.getSelectedItem();
        String length = (String) dlugoscNart.getSelectedItem();
        String dateFrom = dataOd.getText();
        String dateTo = dataDo.getText();
        String status = statusNart.getText();
        String notes = uwagi.getText();

        if (name.isEmpty() || surname.isEmpty() || docID.isEmpty() || dateFrom.isEmpty() || dateTo.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Wypełnij wszystkie wymagane pola.");
            return;
        }

        String entry = name + " " + surname + " (" + docID + ") - Typ: " + type + ", Marka: " + brand + ", Model: " + model +
                ", Wiązania: " + binding + ", Długość: " + length + "cm, Od: " + dateFrom + " Do: " + dateTo +
                ", Status: " + status + ", Uwagi: " + notes;

        wypozyczenia.add(entry);
        textArea.append(entry + "\n");

        imie.setText("");
        nazwisko.setText("");
        nrDokumentu.setText("");
        dataOd.setText("");
        dataDo.setText("");
        statusNart.setText("");
        uwagi.setText("");
    }

    /**
     * Zapisuje wypożyczenia do pliku binarnego (DataOutputStream).
     */
    public void saveToFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("wypozyczenia.bin"))) {
            for (String entry : wypozyczenia) {
                dos.writeUTF(entry);
            }
            JOptionPane.showMessageDialog(frame, "Zapisano dane do pliku.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Błąd zapisu: " + e.getMessage());
        }
    }

    /**
     * Wczytuje wypożyczenia z pliku binarnego (DataInputStream).
     */
    public void loadFromFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("wypozyczenia.bin"))) {
            wypozyczenia.clear();
            textArea.setText("");
            while (dis.available() > 0) {
                String entry = dis.readUTF();
                wypozyczenia.add(entry);
                textArea.append(entry + "\n");
            }
            JOptionPane.showMessageDialog(frame, "Wczytano dane z pliku.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Błąd odczytu: " + e.getMessage());
        }
    }

    /**
     * Wyświetla aktywne wypożyczenia (status różny od "zwrócone").
     */
    public void showActiveRentals() {
        StringBuilder active = new StringBuilder("\n--- AKTYWNE WYPOŻYCZENIA ---\n");
        for (String entry : wypozyczenia) {
            if (!entry.contains("Status: zwrócone")) {
                active.append(entry).append("\n");
            }
        }
        JOptionPane.showMessageDialog(frame, active.toString());
    }
}
