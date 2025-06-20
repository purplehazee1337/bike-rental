package pl.wit.bikerental;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;
import pl.wit.bikerental.storage.DataBundle;
import pl.wit.bikerental.storage.Database;

public class App {
    public static void main(String[] args) {
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

        if (clients.isEmpty()) {
            Service.addClient(clients, "Jan", "Kowalski", "123456789", "jan.kowalski@example.com");
        }

        if (bikes.isEmpty()) {
            Service.addBike(bikes, types.get(0), "Kross", "Hexagon", "27.5\"", "Solidny rower MTB", 15);
        }

        // --- Create a rental ---
        LocalDateTime plannedEnd = LocalDateTime.now().plusDays(3);
        Service.newRental(rentals, bikes, clients, bikes.get(0).getId(), clients.get(0).getId(), plannedEnd);

        // --- Save and print ---
        DataBundle bundleToSave = new DataBundle(types, bikes, clients, rentals);
        Database.printAll(bundleToSave);
        Database.saveAll(bundleToSave);

        // --- Complete rental to test ---
        if (!rentals.isEmpty()) {
            String rentalId = rentals.get(0).getId();
            Service.completeRental(rentals, rentalId);
        }

        // --- Final state ---
        bundleToSave = new DataBundle(types, bikes, clients, rentals);
        Database.printAll(bundleToSave);
        Database.saveAll(bundleToSave);
    }
}
