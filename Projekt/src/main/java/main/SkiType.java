import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Reprezentuje typ nart, np. "All-mountain", "Freestyle", "Race".
 */
public class SkiType implements Serializable {
    private String name;
    private String description;

    public SkiType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(description);
    }

    public static SkiType readFromStream(DataInputStream in) throws IOException {
        String name = in.readUTF();
        String description = in.readUTF();
        return new SkiType(name, description);
    }

    /** Zwraca nazwę typu nart */
    public String getName() {
        return name;
    }

    /** Ustawia nazwę typu nart */
    public void setName(String name) {
        this.name = name;
    }

    /** Zwraca opis typu nart */
    public String getDescription() {
        return description;
    }

    /** Ustawia opis typu nart */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}
