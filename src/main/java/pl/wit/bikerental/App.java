package pl.wit.bikerental;


import java.time.LocalDateTime;
import java.util.List;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.storage.Database;


public class App {
	public static void main(String[] args) {
	    // --- Types ---
	    List<Types> types = List.of(
	        new Types("MTB", "Mountain Bike"),
	        new Types("City", "Urban use")
	    );
	    Database.saveTypes(types, "types.dat");

	    // --- Bikes ---
	    List<Bike> bikes = List.of(
	        new Bike("B1", types.get(0), "Trek", "X", "29\"", "desc", 25, false),
	        new Bike("B2", types.get(1), "Giant", "CityRide", "28\"", "desc", 20, true)
	    );
	    Database.saveBikes(bikes, types, "bikes.dat");

	    // --- Clients ---
	    List<Client> clients = List.of(
	        new Client("John", "Doe", "123456789", "john@example.com"),
	        new Client("Jane", "Smith", "987654321", "jane@example.com")
	    );
	    Database.saveClients(clients, "clients.dat");

	    // --- Rentals ---
	    List<Rental> rentals = List.of(
	        new Rental(clients.get(0), bikes.get(0), LocalDateTime.now())
	    );
	    Database.saveRentals(rentals, clients, bikes, "rentals.dat");

	    // --- LOAD Everything back ---
	    List<Types> loadedTypes = Database.readTypes("types.dat");
	    List<Client> loadedClients = Database.readClients("clients.dat");
	    List<Bike> loadedBikes = Database.readBikes("bikes.dat", loadedTypes);
	    List<Rental> loadedRentals = Database.readRentals("rentals.dat", loadedClients, loadedBikes);
	    
	    

	    // --- OUTPUT ---

	    System.out.println("\nLoaded Types:");
	    loadedTypes.forEach(type -> System.out.println(" - " + type.getNazwaTypu()));

	    System.out.println("\nLoaded Bikes:");
	    loadedBikes.forEach(bike -> System.out.println(" - " + bike.getModel() + " (" + bike.getType().getNazwaTypu() + ")"));

	    System.out.println("\nLoaded Clients:");
	    loadedClients.forEach(client -> System.out.println(" - " + client.getFirstName() + " " + client.getLastName()));

	    System.out.println("\nLoaded Rentals:");
	    loadedRentals.forEach(rental -> System.out.println(" - " + rental.getId() + ": " +
	        rental.getClient().getFirstName() + " rented " +
	        rental.getBike().getModel() + " on " + rental.getStart()));
	}

}
