package pl.wit.bikerental;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;
import pl.wit.bikerental.storage.DataBundle;
import pl.wit.bikerental.storage.Database;
import pl.wit.bikerental.ui.MainFrame;

/**
 * Main application class that loads data, initializes default entities,
 * launches the UI, and saves data on shutdown.
 * 
 * @author Wojciech Jechowski, Krzysztof Mickiewicz, Dominik Pękala
 */
public class App {
	/** Application entry point. */
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
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(bikes, clients, rentals, types);
            frame.setVisible(true);
        });
        

        // --- Final state ---
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DataBundle bundleToSave = new DataBundle(types, bikes, clients, rentals);
            Database.saveAll(bundleToSave);
        }));
    }
}
