import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Reprezentuje osobę korzystającą z wypożyczalni.
 */
public class User implements Serializable {
    private static int NEXT_ID = 1; // licznik dla nowych użytkowników

    private final int id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String description;

    public User(String firstName, String lastName, String documentNumber, String description) {
        this.id = NEXT_ID++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.description = description;
    }

    // Konstruktor używany przy odczycie z pliku
    public User(int id, String firstName, String lastName, String documentNumber, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.description = description;
        if (id >= NEXT_ID) NEXT_ID = id + 1;
    }

    public int getId() {
        return id;
    }

    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(id); // zapisujemy id
        out.writeUTF(firstName);
        out.writeUTF(lastName);
        out.writeUTF(documentNumber);
        out.writeUTF(description);
    }

    public static User readFromStream(DataInputStream in) throws IOException {
        int id = in.readInt();
        String first = in.readUTF();
        String last = in.readUTF();
        String doc = in.readUTF();
        String desc = in.readUTF();
        return new User(id, first, last, doc, desc);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + documentNumber + ")";
    }
}
