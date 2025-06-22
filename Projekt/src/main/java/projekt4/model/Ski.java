package projekt4.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa reprezentująca pojedyncze narty.
 */
public class Ski implements Serializable {
    /**
     * Typ nart
     */
    private SkiType type;
    /**
     * Marka
     */
    private String brand;
    /**
     * Model
     */
    private String model;
    /**
     * Typ wiązań
     */
    private String bindings;
    /**
     * Długość w cm
     */
    private int length;
    /**
     * Czy narty są wypożyczone
     */
    private boolean isRented;

    /**
     * Konstruktor pełny.
     * @param type typ nart
     * @param brand marka
     * @param model model
     * @param bindings wiązania
     * @param length długość
     */
    public Ski(SkiType type, String brand, String model, String bindings, int length) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.bindings = bindings;
        this.length = length;
        this.isRented = false;
    }

    /**
     * @return typ nart
     */
    public SkiType getType() {
        return type;
    }
    /**
     * @param type nowy typ nart
     */
    public void setType(SkiType type) {
        this.type = type;
    }
    /**
     * @return marka
     */
    public String getBrand() {
        return brand;
    }
    /**
     * @param brand nowa marka
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    /**
     * @return model
     */
    public String getModel() {
        return model;
    }
    /**
     * @return model nowy model
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * @return wiazania
     */
    public String getBindings() {
        return bindings;
    }
    /**
     * @param bindings nowe wiązania
     */
    public void setBindings(String bindings) {
        this.bindings = bindings;
    }
    /**
     * @return długość nart
     */
    public int getLength() {
        return length;
    }
    /**
     * @param length nowa długość nart
     */
    public void setLength(int length) {
        this.length = length;
    }
    /**
     * @return true jeśli narty są wypozyczone
     */
    public boolean isRented() {
        return isRented;
    }
    /**
     * @param rented stan wypożyczenia
     */
    public void setRented(boolean rented) {
        isRented = rented;
    }

    /**
     * @return opis tekstowy nart
     */
    @Override
    public String toString() {
        return String.format("%s %s (%d cm) [%s] - %s",
                brand, model, length, bindings, isRented ? "Wypożyczone" : "Dostępne");
    }

    /**
     * Porównanie nart po typie, marce, modelu, długości i wiązaniach
     * @param o obiekt do porównania
     * @return true jeśli dane się zgadzają
     */
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

    /**
     * @return hashCode na podstawie danych narty
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, brand, model, bindings, length);
    }
}
