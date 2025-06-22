import javax.swing.*;
import java.awt.*;

public class ReportsPanel extends JPanel {
    private final SkiRentalManager manager;

    // Przenieś modele na pola klasy
    private final DefaultListModel<Ski> availableSkisModel = new DefaultListModel<>();
    private final DefaultListModel<Rental> overdueRentalsModel = new DefaultListModel<>();
    private final DefaultListModel<Rental> currentRentalsModel = new DefaultListModel<>();

    public ReportsPanel(SkiRentalManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        JTabbedPane reportTabs = new JTabbedPane();

        JList<Ski> availableSkisList = new JList<>(availableSkisModel);
        JList<Rental> overdueRentalsList = new JList<>(overdueRentalsModel);
        JList<Rental> currentRentalsList = new JList<>(currentRentalsModel);

        reportTabs.addTab("Dostępne narty", new JScrollPane(availableSkisList));
        reportTabs.addTab("Niezwrócone na czas", new JScrollPane(overdueRentalsList));
        reportTabs.addTab("Aktualnie wypożyczone", new JScrollPane(currentRentalsList));

        add(reportTabs, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Odśwież raporty");
        add(refreshBtn, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refresh());
        refresh(); // Od razu po utworzeniu panelu
    }

    // Publiczna metoda refresh bez parametrów
    public void refresh() {
        availableSkisModel.clear();
        for (Ski ski : manager.getCurrentlyAvailableSkis()) availableSkisModel.addElement(ski);

        overdueRentalsModel.clear();
        for (Rental rental : manager.getOverdueRentals()) overdueRentalsModel.addElement(rental);

        currentRentalsModel.clear();
        for (Rental rental : manager.getCurrentlyRentedSkis()) currentRentalsModel.addElement(rental);
    }
}