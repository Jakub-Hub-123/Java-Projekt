package projekt4.service;

import org.junit.jupiter.api.Test;
import projekt4.model.Customer;
import projekt4.model.Ski;
import projekt4.model.SkiType;
import projekt4.service.DataStorage;
import projekt4.service.RentalManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DataStorageTest {

    @Test
    void testSaveAndLoadRentalManager() throws Exception {
        RentalManager original = new RentalManager();
        original.addSkiType(new SkiType("Freeride", "Do jazdy poza trasami"));
        original.addCustomer(new Customer("Jan", "Kowalski", "ID1", "bez uwag"));
        original.addSki(new Ski(original.getSkiTypes().get(0), "Head", "Supershape", "Tyrolia", 170));

        String filePath = "testdata.bin";
        DataStorage.saveToFile(original, filePath);

        RentalManager loaded = DataStorage.loadFromFile(filePath);

        assertEquals(1, loaded.getCustomers().size());
        assertEquals(1, loaded.getSkiTypes().size());
        assertEquals(1, loaded.getSkis().size());

        // Clean up
        new File(filePath).delete();
    }
}
