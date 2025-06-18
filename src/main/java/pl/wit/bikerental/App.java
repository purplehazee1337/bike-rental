package pl.wit.bikerental;


import java.time.LocalDateTime;
import java.util.List;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.storage.DataBundle;
import pl.wit.bikerental.storage.Database;


public class App {
	public static void main(String[] args) {
	    // --- Sample data ---
	    List<Types> types = List.of(
	        new Types("MTB", "Mountain Bike"),
	        new Types("City", "Urban use")
	    );

	    List<Bike> bikes = List.of(
	        new Bike("B1", types.get(0), "Trek", "X", "29\"", "desc", 25, false),
	        new Bike("B2", types.get(1), "Giant", "CityRide", "28\"", "desc", 20, true)
	    );

	    List<Client> clients = List.of(
	        new Client("John", "Doe", "123456789", "john@example.com"),
	        new Client("Jane", "Smith", "987654321", "jane@example.com")
	    );

	    List<Rental> rentals = List.of(
	        new Rental(clients.get(0), bikes.get(0), LocalDateTime.now())
	    );

	    DataBundle bundleToSave = new DataBundle(types, bikes, clients, rentals);

	    // --- Save all data ---
	    Database.saveAll(bundleToSave);

	    // --- Load all data ---
	    DataBundle loadedBundle = Database.readAll();

	    // --- Print all loaded data ---
	    Database.printAll(loadedBundle);
	}

}
