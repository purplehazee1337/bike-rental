package pl.wit.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;
import pl.wit.bikerental.storage.DataBundle;
import pl.wit.bikerental.storage.Database;

class AppTests {
	
	@BeforeAll
	public static void setup() {
		Database.setBasePath("./src/test/data/");
    }
	
	@AfterAll
    public static void cleanup() {
        File dir = new File("./src/test/data/");
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

	@Test
	void appTest() throws Exception {
	    // --- Load all data ---
	    DataBundle loadedBundle = Database.readAll();

	    // --- Data ---
	    List<Types> types = loadedBundle.types != null ? loadedBundle.types : new ArrayList<>();
	    List<Bike> bikes = loadedBundle.bikes != null ? loadedBundle.bikes : new ArrayList<>();
	    List<Client> clients = loadedBundle.clients != null ? loadedBundle.clients : new ArrayList<>();
	    List<Rental> rentals = loadedBundle.rentals != null ? loadedBundle.rentals : new ArrayList<>();

	    // --- Ensure we have at least one type, bike, and client ---
	    if (types.isEmpty()) {
	        Service.addType(types, "Górski", "Rower do jazdy po górach");
	    }
	    assertFalse(types.isEmpty(), "Types list should not be empty after adding type");

	    if (clients.isEmpty()) {
	        Service.addClient(clients, "Jan", "Kowalski", "123456789", "jan.kowalski@example.com");
	    }
	    assertFalse(clients.isEmpty(), "Clients list should not be empty after adding client");

	    if (bikes.isEmpty()) {
	        Service.addBike(bikes, types.get(0), "Kross", "Hexagon", "27.5\"", "Solidny rower MTB", 15);
	    }
	    assertFalse(bikes.isEmpty(), "Bikes list should not be empty after adding bike");

	    // --- Create a rental ---
	    LocalDateTime today = LocalDateTime.now();
	    LocalDateTime plannedEnd = LocalDateTime.now().plusDays(3);
	    Service.newRental(rentals, bikes, clients, bikes.get(0).getId(), clients.get(0).getId(), today, plannedEnd);

	    assertFalse(rentals.isEmpty(), "Rentals list should not be empty after creating a new rental");

	    Rental rental = rentals.get(0);
	    assertEquals(bikes.get(0).getId(), rental.getBike().getId(), "Rental bike ID should match the bike rented");
	    assertEquals(clients.get(0).getId(), rental.getClient().getId(), "Rental client ID should match the client");
	    assertFalse(rental.isReturned(), "New rental should not be marked as returned");

	    // The rented bike should be marked as rented
	    assertTrue(bikes.get(0).isRented(), "Bike should be marked as rented after creating rental");

	    // --- Save and print ---
	    DataBundle bundleToSave = new DataBundle(types, bikes, clients, rentals);
	    Database.printAll(bundleToSave);
	    Database.saveAll(bundleToSave);

	    // --- Complete rental to test ---
	    if (!rentals.isEmpty()) {
	        String rentalId = rentals.get(0).getId();
	        Service.completeRental(rentals, rentalId);
	    }

	    assertTrue(rentals.get(0).isReturned(), "Rental should be marked as returned after completion");
	    assertFalse(bikes.get(0).isRented(), "Bike should not be rented after rental is completed");

	    // --- Final state ---
	    bundleToSave = new DataBundle(types, bikes, clients, rentals);
	    Database.printAll(bundleToSave);
	    Database.saveAll(bundleToSave);
	}
}
