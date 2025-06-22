import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Reprezentuje wypożyczenie nart.
 */
public class Rental implements Serializable {
    private Ski ski;
    private User user;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private RentalStatus status;
    private String notes;

    public Rental(Ski ski, User user, LocalDateTime startDateTime, LocalDateTime endDateTime, RentalStatus status, String notes) {
        this.ski = ski;
        this.user = user;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.notes = notes;
    }

    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(ski.getId());   // Save only the ID
        out.writeInt(user.getId());  // Save only the ID
        out.writeUTF(startDateTime.toString());
        out.writeUTF(endDateTime.toString());
        out.writeUTF(status.name());
        out.writeUTF(notes);
    }

    public static Rental readFromStream(DataInputStream in, List<Ski> skis, List<User> users) throws IOException {
        int skiId = in.readInt();
        int userId = in.readInt();
        LocalDateTime start = LocalDateTime.parse(in.readUTF());
        LocalDateTime end = LocalDateTime.parse(in.readUTF());
        RentalStatus status = RentalStatus.valueOf(in.readUTF());
        String notes = in.readUTF();

        Ski ski = skis.stream().filter(s -> s.getId() == skiId).findFirst().orElse(null);
        User user = users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

        if (ski == null || user == null) {
            throw new IOException("Nie znaleziono narty lub użytkownika o ID: skiId=" + skiId + ", userId=" + userId);
        }

        return new Rental(ski, user, start, end, status, notes);
    }

    public Ski getSki() {
        return ski;
    }

    public void setSki(Ski ski) {
        this.ski = ski;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Wypożyczenie: " + ski + " dla " + user + " od " + startDateTime + " do " + endDateTime + " Status: " + status;
    }

    public static void readData(DataInputStream in, List<Ski> skis, List<User> users, List<Rental> rentals, List<SkiType> skiTypes) throws IOException {
        // Najpierw typy nart
        int skiTypeCount = in.readInt();
        skiTypes.clear();
        for (int i = 0; i < skiTypeCount; i++) skiTypes.add(SkiType.readFromStream(in));

        // Potem narty
        int skiCount = in.readInt();
        skis.clear();
        for (int i = 0; i < skiCount; i++) skis.add(Ski.readFromStream(in));

        // Potem użytkownicy
        int userCount = in.readInt();
        users.clear();
        for (int i = 0; i < userCount; i++) users.add(User.readFromStream(in));

        // Na końcu wypożyczenia
        int rentalCount = in.readInt();
        rentals.clear();
        for (int i = 0; i < rentalCount; i++) rentals.add(Rental.readFromStream(in, skis, users));
    }
}
