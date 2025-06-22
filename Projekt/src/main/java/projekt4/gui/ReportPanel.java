package projekt4.gui;

import projekt4.model.Rental;
import projekt4.model.Ski;
import projekt4.service.RentalManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Panel GUI do raportowania stanu nart w wypożyczalni.
 */
public class ReportPanel extends JPanel {

    private final RentalManager rentalManager;
    private final DefaultListModel<String> reportListModel;
    private final JList<String> reportList;

    public ReportPanel(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.reportListModel = new DefaultListModel<>();
        this.reportList = new JList<>(reportListModel);

        setLayout(new BorderLayout());
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(reportList), BorderLayout.CENTER);
    }

    private JPanel createToolbar() {
        JPanel panel = new JPanel();

        JButton availableBtn = new JButton("Dostępne narty");
        JButton rentedBtn = new JButton("Na wypożyczeniu");
        JButton overdueBtn = new JButton("Przeterminowane");

        availableBtn.addActionListener(e -> showAvailableSkis());
        rentedBtn.addActionListener(e -> showActiveRentals());
        overdueBtn.addActionListener(e -> showOverdueRentals());

        panel.add(availableBtn);
        panel.add(rentedBtn);
        panel.add(overdueBtn);

        return panel;
    }

    private void showAvailableSkis() {
        reportListModel.clear();
        List<Ski> available = rentalManager.getAvailableSkis();
        if (available.isEmpty()) {
            reportListModel.addElement("Brak dostępnych nart.");
        } else {
            available.forEach(ski -> reportListModel.addElement(ski.toString()));
        }
    }

    private void showActiveRentals() {
        reportListModel.clear();
        List<Rental> active = rentalManager.getActiveRentals();
        if (active.isEmpty()) {
            reportListModel.addElement("Brak wypożyczonych nart.");
        } else {
            active.forEach(rental -> reportListModel.addElement(rental.toString()));
        }
    }

    private void showOverdueRentals() {
        reportListModel.clear();
        List<Rental> overdue = rentalManager.getOverdueRentals(LocalDateTime.now());
        if (overdue.isEmpty()) {
            reportListModel.addElement("Brak przeterminowanych wypożyczeń.");
        } else {
            overdue.forEach(rental -> reportListModel.addElement(rental.toString()));
        }
    }
}
