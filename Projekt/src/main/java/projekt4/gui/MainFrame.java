package projekt4.gui;

import projekt4.service.DataStorage;
import projekt4.service.RentalManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Główne okno aplikacji Swing do obsługi wypożyczalni nart.
 */
public class MainFrame extends JFrame {

    private RentalManager rentalManager;

    // Konstruktor z przekazaniem gotowego managera (np. po wczytaniu z pliku)
    public MainFrame(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        initializeGUI();
    }

    // Konstruktor domyślny – tworzy nowy, pusty manager
    public MainFrame() {
        this(new RentalManager());
    }

    private void initializeGUI() {
        setTitle("Wypożyczalnia Nart");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setJMenuBar(createMenuBar());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Typy nart", new SkiTypePanel(rentalManager));
        tabs.addTab("Narty", new SkiPanel(rentalManager));
        tabs.addTab("Klienci", new CustomerPanel(rentalManager));
        tabs.addTab("Wypożyczenia", new RentalPanel(rentalManager));
        tabs.addTab("Raporty", new ReportPanel(rentalManager));

        getContentPane().add(tabs, BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Plik");

        JMenuItem saveItem = new JMenuItem("Zapisz...");
        JMenuItem loadItem = new JMenuItem("Wczytaj...");

        saveItem.addActionListener(e -> onSave());
        loadItem.addActionListener(e -> onLoad());

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);

        return menuBar;
    }

    private void onSave() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                DataStorage.saveToFile(rentalManager, file.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Dane zapisane pomyślnie.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd zapisu: " + ex.getMessage());
            }
        }
    }

    private void onLoad() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                RentalManager loadedManager = DataStorage.loadFromFile(file.getAbsolutePath());
                dispose(); // zamyka stare okno
                new MainFrame(loadedManager).setVisible(true); // uruchamia nowe z danymi
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd wczytywania: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}