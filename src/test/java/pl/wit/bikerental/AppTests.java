package pl.wit.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;
import pl.wit.bikerental.storage.DataBundle;
import pl.wit.bikerental.storage.Database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for application initialization and persistence logic.
 * <p>
 * This test simulates the initialization logic of the application (excluding GUI/Swing component),
 * verifying the correct setup of in-memory data, persistence to files, and recovery from storage.
 * It ensures that types, bikes, and clients are correctly created if missing, and validates rental creation and completion.
 * </p>
 * 
 * @author Krzysztof Mickiewicz
 */
class AppTest {

    /**
     * Path to the directory used for storing test data files.
     */
    private final String testPath = "./src/test/resources/data/";

    /**
     * Sets up the test environment before each test by:
     * <ul>
     *     <li>Ensuring the test data directory exists.</li>
     *     <li>Setting the database base path to the test directory.</li>
     *     <li>Resetting static ID counters for all domain entities.</li>
     *     <li>Cleaning up any pre-existing data files to ensure test isolation.</li>
     * </ul>
     */
    @BeforeEach
    void setUp() {
        new File(testPath).mkdirs();
        Database.setBasePath(testPath);
        Bike.setIdCount(0);
        Types.setTypeIdCount(0);
        Client.setClientIdCount(0);
        Rental.setRentalIdCount(0);

        // Clean up data files before test
        for (String fname : new String[]{"types.dat", "bikes.dat", "clients.dat", "rentals.dat"}) {
            File file = new File(testPath + fname);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Tests the initialization logic of the App.
     * <p>
     * This includes:
     * <ul>
     *     <li>Reading from empty database and ensuring required entities are created if missing.</li>
     *     <li>Persisting and reloading the data bundle.</li>
     *     <li>Creating a rental and validating correct state changes for bikes and rentals.</li>
     *     <li>Completing a rental and verifying the return state of the bike and rental.</li>
     * </ul>
     */
    @Test
    void appInitializationLogicTest() {
        // Simulate App.main logic (the non-GUI, non-Swing part)
        DataBundle loadedBundle = Database.readAll();

        List<Types> types = loadedBundle.types != null ? loadedBundle.types : new ArrayList<>();
        List<Bike> bikes = loadedBundle.bikes != null ? loadedBundle.bikes : new ArrayList<>();
        List<Client> clients = loadedBundle.clients != null ? loadedBundle.clients : new ArrayList<>();
        List<Rental> rentals = loadedBundle.rentals != null ? loadedBundle.rentals : new ArrayList<>();

        // Ensure one type
        if (types.isEmpty()) {
            Service.addType(types, "Górski", "Rower do jazdy po górach");
        }
        assertFalse(types.isEmpty(), "Types should not be empty after initialization");

        // Ensure one client
        if (clients.isEmpty()) {
            Service.addClient(clients, "Jan", "Kowalski", "123456789", "jan.kowalski@example.com");
        }
        assertFalse(clients.isEmpty(), "Clients should not be empty after initialization");

        // Ensure one bike
        if (bikes.isEmpty()) {
            Service.addBike(bikes, types.get(0), "Kross", "Hexagon", "27.5\"", "Solidny rower MTB", 15);
        }
        assertFalse(bikes.isEmpty(), "Bikes should not be empty after initialization");

        // Save and reload to test persistence
        DataBundle bundleToSave = new DataBundle(types, bikes, clients, rentals);
        Database.saveAll(bundleToSave);
        DataBundle loaded = Database.readAll();

        assertEquals(1, loaded.types.size());
        assertEquals(1, loaded.bikes.size());
        assertEquals(1, loaded.clients.size());
        assertEquals(0, loaded.rentals.size());

        // Add a rental and test
        try {
            Service.newRental(rentals, bikes, clients, bikes.get(0).getId(), clients.get(0).getId(),
                    java.time.LocalDateTime.now(), java.time.LocalDateTime.now().plusDays(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, rentals.size());
        assertTrue(bikes.get(0).isRented());

        // Complete rental and check state
        try {
            Service.completeRental(rentals, rentals.get(0).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(rentals.get(0).isReturned());
        assertFalse(bikes.get(0).isRented());
    }
}