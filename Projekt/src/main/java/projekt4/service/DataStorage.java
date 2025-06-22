package projekt4.service;

import java.io.*;

/**
 * Obsługa zapisu i odczytu danych do pliku binarnego.
 */
public class DataStorage {

    public static void saveToFile(RentalManager manager, String filePath) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
             ObjectOutputStream oos = new ObjectOutputStream(dos)) {
            oos.writeObject(manager);
        }
    }

    public static RentalManager loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
             ObjectInputStream ois = new ObjectInputStream(dis)) {
            return (RentalManager) ois.readObject();
        }
    }
}
