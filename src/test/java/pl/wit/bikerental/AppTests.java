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
 * Unit test for App initialization logic.
 * 
 * @author Krzysztof Mickiewicz
 * @version 1.0
 * @since 2025-06-22
 */
class AppTest {

    private final String testPath = "./src/test/resources/data/";

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