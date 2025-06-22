import javax.swing.*;

import java.io.IOException;

public class SkiRentalApp extends JFrame {

    private final SkiRentalManager manager;

    // Referencje do paneli, jeśli chcesz je odświeżać po wczytaniu pliku
    private SkiTypePanel skiTypePanel;
    private SkiPanel skiPanel;
    private UserPanel userPanel;
    private RentalPanel rentalPanel;
    private ReturnPanel returnPanel;
    private ReportsPanel reportsPanel;

    public SkiRentalApp() {
        super("Wypożyczalnia Nart");

        manager = SkiRentalManager.getInstance();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Plik");

        JMenuItem saveItem = new JMenuItem("Zapisz dane...");
        JMenuItem loadItem = new JMenuItem("Wczytaj dane...");

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        saveItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    manager.saveToFile(chooser.getSelectedFile());
                    JOptionPane.showMessageDialog(this, "Dane zapisane pomyślnie.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Błąd zapisu: " + ex.getMessage());
                }
            }
        });

        loadItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    manager.loadFromFile(chooser.getSelectedFile());
                    JOptionPane.showMessageDialog(this, "Dane wczytane pomyślnie.");
                    refreshAllPanels();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Błąd odczytu: " + ex.getMessage());
                }
            }
        });

        JTabbedPane tabbedPane = new JTabbedPane();

        skiTypePanel = new SkiTypePanel(manager);
        skiPanel = new SkiPanel(manager);
        userPanel = new UserPanel(manager);
        rentalPanel = new RentalPanel(manager);
        returnPanel = new ReturnPanel(manager);
        reportsPanel = new ReportsPanel(manager);

        tabbedPane.add("Typy Nart", skiTypePanel);
        tabbedPane.add("Narty", skiPanel);
        tabbedPane.add("Użytkownicy", userPanel);
        tabbedPane.add("Wypożyczenia", rentalPanel);
        tabbedPane.add("Zwrot nart", returnPanel);
        tabbedPane.add("Raporty", reportsPanel);

        add(tabbedPane);
    }

    // Odśwież wszystkie panele po wczytaniu pliku
    private void refreshAllPanels() {
        skiTypePanel.refresh();
        skiPanel.refresh();
        userPanel.refresh();
        rentalPanel.refresh();
        returnPanel.refresh();
        reportsPanel.refresh(); // jeśli masz refresh w ReportsPanel, wywołaj go tutaj
    }
}