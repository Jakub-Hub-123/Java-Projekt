package projekt4.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa reprezentująca typ nart.
 */
public class SkiType implements Serializable {
    private String name;
    private String description;

    public SkiType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkiType)) return false;
        SkiType skiType = (SkiType) o;
        return Objects.equals(name, skiType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
