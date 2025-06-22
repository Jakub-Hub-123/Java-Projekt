package projekt4.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa reprezentująca pojedyncze narty.
 */
public class Ski implements Serializable {
    private SkiType type;
    private String brand;
    private String model;
    private String bindings;
    private int length;
    private boolean isRented;

    public Ski(SkiType type, String brand, String model, String bindings, int length) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.bindings = bindings;
        this.length = length;
        this.isRented = false;
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

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d cm) [%s] - %s",
                brand, model, length, bindings, isRented ? "Wypożyczone" : "Dostępne");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ski)) return false;
        Ski ski = (Ski) o;
        return length == ski.length &&
                Objects.equals(brand, ski.brand) &&
                Objects.equals(model, ski.model) &&
                Objects.equals(bindings, ski.bindings) &&
                Objects.equals(type, ski.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, brand, model, bindings, length);
    }
}
