import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Reprezentuje pojedyncze narty.
 */
public class Ski implements Serializable {
    private static int NEXT_ID = 1;

    private final int id;
    private SkiType type;
    private String brand;
    private String model;
    private String bindings;
    private int length;  // długość w cm

    // Konstruktor do tworzenia nowych nart
    public Ski(SkiType type, String brand, String model, String bindings, int length) {
        this.id = NEXT_ID++;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.bindings = bindings;
        this.length = length;
    }

    // Konstruktor do odczytu z pliku
    public Ski(int id, SkiType type, String brand, String model, String bindings, int length) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.bindings = bindings;
        this.length = length;
        if (id >= NEXT_ID) NEXT_ID = id + 1;
    }

    public int getId() {
        return id;
    }

    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(id); // zapisujemy id
        type.writeToStream(out);
        out.writeUTF(brand);
        out.writeUTF(model);
        out.writeUTF(bindings);
        out.writeInt(length);
    }

    public static Ski readFromStream(DataInputStream in) throws IOException {
        int id = in.readInt();
        SkiType type = SkiType.readFromStream(in);
        String brand = in.readUTF();
        String model = in.readUTF();
        String bindings = in.readUTF();
        int length = in.readInt();
        return new Ski(id, type, brand, model, bindings, length);
    }

    public SkiType getType() {
        return type;
    }

    public void setType(SkiType type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBindings() {
        return bindings;
    }

    public void setBindings(String bindings) {
        this.bindings = bindings;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return brand + " " + model + " (" + length + "cm), typ: " + type.getName();
    }
}
