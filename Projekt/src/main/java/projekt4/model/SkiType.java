package projekt4.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa reprezentująca typ nart.
 */
public class SkiType implements Serializable {
    /**
     * Nazwa typu
     */
    private String name;
    /**
     * Opis typu
     */
    private String description;

    /**
     * Konstruktor typów
     * @param name nazwa
     * @param description opis
     */
    public SkiType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return nazwa typu
     */
    public String getName() {
        return name;
    }

    /**
     * @param name nowa nazwa typu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return opis typu
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description nowy opis typu
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return reprezentacja tekstowa
     */
    @Override
    public String toString() {
        return name + " - " + description;
    }

    /**
     * Porównanie po nazwie
     * @param o obiekt
     * @return true jeśli nazwy są równe
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkiType)) return false;
        SkiType skiType = (SkiType) o;
        return Objects.equals(name, skiType.name);
    }

    /**
     * @return hash na podstawie nazwy
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
