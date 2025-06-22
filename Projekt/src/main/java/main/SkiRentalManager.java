import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Klasa zarządzająca wypożyczalnią nart - CRUD na typach, nartach, użytkownikach i wypożyczeniach.
 */
public class SkiRentalManager{

    private static SkiRentalManager instance;

    private final List<SkiType> skiTypes;
    private final List<Ski> skis;
    private final List<User> users;
    private final List<Rental> rentals;

    private SkiRentalManager() {
        // CopyOnWriteArrayList dla bezpieczeństwa wielowątkowego (proste rozwiązanie)
        this.skiTypes = new CopyOnWriteArrayList<>();
        this.skis = new CopyOnWriteArrayList<>();
        this.users = new CopyOnWriteArrayList<>();
        this.rentals = new CopyOnWriteArrayList<>();
    }

    /**
     * Pobiera instancję klasy SkiRentalManager (singleton).
     * @return instancja SkiRentalManager
     */
    public static synchronized SkiRentalManager getInstance() {
        if (instance == null) {
            instance = new SkiRentalManager();
        }
        return instance;
    }

    // ====== Typy nart ======

    public void addSkiType(SkiType skiType) {
        Objects.requireNonNull(skiType, "skiType nie może być null");
        skiTypes.add(skiType);
    }

    public void removeSkiType(SkiType skiType) {
        skiTypes.remove(skiType);
        // Usunięcie typu powinno też usuwać narty tego typu? Można dodać logikę tutaj, np:
        skis.removeIf(ski -> ski.getType().equals(skiType));
    }

    public List<SkiType> getAllSkiTypes() {
        return Collections.unmodifiableList(skiTypes);
    }

    // ====== Narty ======

    public void addSki(Ski ski) {
        Objects.requireNonNull(ski, "ski nie może być null");
        skis.add(ski);
    }

    public void removeSki(Ski ski) {
        skis.remove(ski);
        // Usuwanie powiązanych wypożyczeń?
        rentals.removeIf(rental -> rental.getSki().equals(ski));
    }

    public List<Ski> getAllSkis() {
        return Collections.unmodifiableList(skis);
    }

    // ====== Użytkownicy ======

    public void addUser(User user) {
        Objects.requireNonNull(user, "user nie może być null");
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
        rentals.removeIf(rental -> rental.getUser().equals(user));
    }

    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    // ====== Wypożyczenia ======

    public void addRental(Rental rental) {
        Objects.requireNonNull(rental, "rental nie może być null");
        rentals.add(rental);
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
    }

    public List<Rental> getAllRentals() {
        return new ArrayList<>(rentals);
    }

    // ====== Raportowanie i wyszukiwanie ======

    /**
     * Zwraca listę nart dostępnych (nie wypożyczonych w zadanym czasie).
     */
    public List<Ski> getAvailableSkis(LocalDateTime from, LocalDateTime to) {
        return skis.stream()
                .filter(ski -> rentals.stream()
                        .filter(rental -> rental.getSki().equals(ski))
                        .noneMatch(rental -> isOverlapping(rental.getStartDateTime(), rental.getEndDateTime(), from, to))
                )
                .collect(Collectors.toList());
    }

    /**
     * Sprawdza czy dwa okresy czasowe się nakładają.
     */
    private boolean isOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    /**
     * Zwraca listę wypożyczeń, które nie zostały zwrócone na czas.
     */
    public List<Rental> getLateReturns(LocalDateTime now) {
        return rentals.stream()
                .filter(rental -> rental.getStatus() == RentalStatus.RENTED)
                .filter(rental -> rental.getEndDateTime().isBefore(now))
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę wypożyczeń aktualnie trwających (status RENTED).
     */
    public List<Rental> getCurrentRentals() {
        return rentals.stream()
                .filter(rental -> rental.getStatus() == RentalStatus.RENTED)
                .collect(Collectors.toList());
    }

    /**
     * Wyszukiwanie użytkowników po fragmencie imienia lub nazwiska (case insensitive).
     */
    public List<User> searchUsers(String query) {
        String q = query.toLowerCase();
        return users.stream()
                .filter(user -> user.getFirstName().toLowerCase().contains(q) || user.getLastName().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    /**
     * Wyszukiwanie nart po fragmencie marki lub modelu (case insensitive).
     */
    public List<Ski> searchSkis(String query) {
        String q = query.toLowerCase();
        return skis.stream()
                .filter(ski -> ski.getBrand().toLowerCase().contains(q) || ski.getModel().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

        /**
     * Oznacza wypożyczenie jako zwrócone, dodając opcjonalne uwagi.
     * @param rental wypożyczenie do zwrotu
     * @param returnNotes uwagi przy zwrocie (może być null lub pusty)
     */
    public void returnRental(Rental rental, String returnNotes) {
        Objects.requireNonNull(rental, "rental nie może być null");
        if (rental.getStatus() != RentalStatus.RENTED) {
            throw new IllegalStateException("Wypożyczenie nie jest w statusie RENTED.");
        }
        rental.setStatus(RentalStatus.RETURNED);
        if (returnNotes != null && !returnNotes.trim().isEmpty()) {
            String existingNotes = rental.getNotes() == null ? "" : rental.getNotes();
            rental.setNotes(existingNotes + " | Zwrot: " + returnNotes.trim());
        }
    }  
    
        // Raport 1: aktualnie dostępne narty (nie wypożyczone teraz)
    public List<Ski> getCurrentlyAvailableSkis() {
        LocalDateTime now = LocalDateTime.now();
        return skis.stream()
                .filter(ski -> rentals.stream()
                    .filter(rental -> rental.getSki().equals(ski))
                    .noneMatch(rental -> rental.getStatus() == RentalStatus.RENTED &&
                            !rental.getStartDateTime().isAfter(now) &&
                            !rental.getEndDateTime().isBefore(now)
                    ))
                .collect(Collectors.toList());
    }

    // Raport 2: niezwrócone narty na czas (czyli wypożyczenia RENTED, których endDateTime jest przeszły)
    public List<Rental> getOverdueRentals() {
        LocalDateTime now = LocalDateTime.now();
        return rentals.stream()
                .filter(rental -> rental.getStatus() == RentalStatus.RENTED)
                .filter(rental -> rental.getEndDateTime().isBefore(now))
                .collect(Collectors.toList());
    }

    // Raport 3: narty będące aktualnie na wypożyczeniu (status RENTED, czas wypożyczenia aktualny)
    public List<Rental> getCurrentlyRentedSkis() {
        LocalDateTime now = LocalDateTime.now();
        return rentals.stream()
                .filter(rental -> rental.getStatus() == RentalStatus.RENTED)
                .filter(rental -> !rental.getStartDateTime().isAfter(now) && !rental.getEndDateTime().isBefore(now))
                .collect(Collectors.toList());
    }

    public void saveToFile(File file) throws IOException {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
        // Typy nart
        out.writeInt(skiTypes.size());
        for (SkiType type : skiTypes) {
            type.writeToStream(out);
        }

        // Narty
        out.writeInt(skis.size());
        for (Ski ski : skis) {
            ski.writeToStream(out);
        }

        // Użytkownicy
        out.writeInt(users.size());
        for (User user : users) {
            user.writeToStream(out);
        }

        // Wypożyczenia
        out.writeInt(rentals.size());
        for (Rental rental : rentals) {
            rental.writeToStream(out);
        }
    }
}

    public void loadFromFile(File file) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            // Typy nart
            int typeCount = in.readInt();
            skiTypes.clear();
            for (int i = 0; i < typeCount; i++) {
                skiTypes.add(SkiType.readFromStream(in));
            }

            // Narty
            int skiCount = in.readInt();
            skis.clear();
            for (int i = 0; i < skiCount; i++) {
                skis.add(Ski.readFromStream(in));
            }

            // Użytkownicy
            int userCount = in.readInt();
            users.clear();
            for (int i = 0; i < userCount; i++) {
                users.add(User.readFromStream(in));
            }

            // Wypożyczenia
            int rentalCount = in.readInt();
            rentals.clear();
            for (int i = 0; i < rentalCount; i++) {
                rentals.add(Rental.readFromStream(in, skis, users));
            }
        }
    }

}

