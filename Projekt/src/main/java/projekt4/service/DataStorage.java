package projekt4.service;

import java.io.*;

/**
 * Obsługa zapisu i odczytu danych do pliku binarnego.
 */
public class DataStorage {

    /**
     * Zapisuje obiekt RentalManager do pliku binarnego.
     * @param manager instancja do zapisania
     * @param filePath ścieżka do pliku
     * @throws IOException gdy wystąpi błąd IO
     */
    public static void saveToFile(RentalManager manager, String filePath) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
             ObjectOutputStream oos = new ObjectOutputStream(dos)) {
            oos.writeObject(manager);
        }
    }

    /**
     * Wczytuje obiekt RentalManager z pliku binarnego.
     * @param filePath ścieżka do pliku
     * @return wczytana instancja RentalManager
     * @throws IOException jeśli wystąpi błąd IO
     * @throws ClassNotFoundException jeśli nie można odnaleźć klasy
     */
    public static RentalManager loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
             ObjectInputStream ois = new ObjectInputStream(dis)) {
            return (RentalManager) ois.readObject();
        }
    }
}
