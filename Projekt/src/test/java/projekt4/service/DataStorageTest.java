package projekt4.service;

import org.junit.jupiter.api.Test;
import projekt4.model.Customer;
import projekt4.model.Ski;
import projekt4.model.SkiType;
import projekt4.service.DataStorage;
import projekt4.service.RentalManager;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class DataStorageTest {
    /**
     * Test zapisu i odczytu obiektu RentalManager z pliku
     */
    @Test
    public void saveAndLoadRentalManagerTest() throws Exception {
        RentalManager original = new RentalManager();

        /**
         * Dodanie przykładowych danych
          */
        original.addSkiType(new SkiType("Szosowe", "Do jazdy poza trasami"));
        original.addCustomer(new Customer("Jakub", "Nowak", "ID123", "bez uwag"));
        original.addSki(new Ski(original.getSkiTypes().get(0), "Head", "Supershape", "Telemarkowe", 170));

        /**
         * Ścieżka do pliku testowego
          */
        String filePath = "testdata.bin";

        /**
         * Zapis danych do pliku
          */
        DataStorage.saveToFile(original, filePath);

        /**
         * Odczyt danych z pliku
          */
        RentalManager loaded = DataStorage.loadFromFile(filePath);

        /**
         * Sprawdzanie, czy dane się zgadzają po wczytaniu
          */
        assertEquals(1, loaded.getCustomers().size());
        assertEquals(1, loaded.getSkiTypes().size());
        assertEquals(1, loaded.getSkis().size());

        /**
         * Usunięcie pliku testowego
          */
        new File(filePath).delete();
    }
}
