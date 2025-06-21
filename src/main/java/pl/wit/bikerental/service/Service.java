package pl.wit.bikerental.service;

import pl.wit.bikerental.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class Service {
	
	// ------------------- FIND METHODS -------------------

	public static Bike findBikeById(List<Bike> bikes, String bikeId) {
	    for (Bike bike : bikes) {
	        if (bike.getId().equals(bikeId)) {
	            return bike;
	        }
	    }
	    return null;
	}

	public static Client findClientById(List<Client> clients, String clientId) {
	    for (Client client : clients) {
	        if (client.getId().equals(clientId)) {
	            return client;
	        }
	    }
	    return null;
	}

	public static Rental findRentalById(List<Rental> rentals, String rentalId) {
	    for (Rental rental : rentals) {
	        if (rental.getId().equals(rentalId)) {
	            return rental;
	        }
	    }
	    return null;
	}

	public static Types findTypeById(List<Types> types, String typeId) {
	    for (Types type : types) {
	        if (type.getId().equals(typeId)) {
	            return type;
	        }
	    }
	    return null;
	}
	
	// ------------------- EDIT METHODS -------------------

		public static boolean editBikeById(List<Bike> bikes, String bikeId, String brand, String model,
		                                   String wheelSize, String description, int pricePerH) {
		    Bike bike = findBikeById(bikes, bikeId);
		    if (bike == null) {
		        System.out.println("Bike with ID " + bikeId + " not found.");
		        return false;
		    }
		    bike.setBrand(brand);
		    bike.setModel(model);
		    bike.setWheelSize(wheelSize);
		    bike.setDescription(description);
		    bike.setPricePerH(pricePerH);
		    return true;
		}

		public static boolean editClientById(List<Client> clients, String clientId, String firstName,
		                                     String lastName, String phoneNumber, String email) {
		    Client client = findClientById(clients, clientId);
		    if (client == null) {
		        System.out.println("Client with ID " + clientId + " not found.");
		        return false;
		    }
		    client.setFirstName(firstName);
		    client.setLastName(lastName);
		    client.setPhoneNumber(phoneNumber);
		    client.setEmail(email);
		    return true;
		}

		public static boolean editRentalById(List<Rental> rentals, String rentalId, LocalDateTime newStart,
		                                     LocalDateTime newPlannedEnd) {
		    Rental rental = findRentalById(rentals, rentalId);
		    if (rental == null) {
		        System.out.println("Rental with ID " + rentalId + " not found.");
		        return false;
		    }
		    rental.setStart(newStart);
		    rental.setPlannedEnd(newPlannedEnd);
		    return true;
		}

		public static boolean editTypeById(List<Types> types, String typeId, String newName, String newDescription) {
		    Types type = findTypeById(types, typeId);
		    if (type == null) {
		        System.out.println("Type with ID " + typeId + " not found.");
		        return false;
		    }
		    type.setName(newName);
		    type.setDescription(newDescription);
		    return true;
		}


    // ------------------- RENTAL -------------------

	public static void newRental(List<Rental> rentals, List<Bike> bikes, List<Client> clients,
            String bikeId, String clientId, LocalDateTime plannedEnd) {
		Bike bike = findBikeById(bikes, bikeId);
		Client client = findClientById(clients, clientId);
		
		if (bike == null) {
		System.out.println("Bike with ID " + bikeId + " not found.");
		return;
		}
		
		if (client == null) {
		System.out.println("Client with ID " + clientId + " not found.");
		return;
		}
		
		if (bike.isRented()) {
		System.out.println("Bike " + bike.getId() + " is already rented.");
		return;
		}
		
		Rental rental = new Rental(client, bike, plannedEnd, plannedEnd);
		rentals.add(rental);
		bike.setRented(true);
		System.out.println("Rental created successfully: " + rental.getId());
		}


	public static void completeRental(List<Rental> rentals, String rentalId) {
	    Rental rental = findRentalById(rentals, rentalId);
	    if (rental != null) {
	        if (rental.isReturned()) {
	            System.out.println("Rental " + rentalId + " has already been returned.");
	            return;
	        }

	        rental.setReturned(true);
	        rental.setActualReturnDate(LocalDateTime.now());
	        rental.getBike().setRented(false);

	        System.out.println("Rental " + rentalId + " marked as returned.");
	    } else {
	        System.out.println("Rental " + rentalId + " not found.");
	    }
	}

    // ------------------- CLIENT -------------------

    public static void addClient(List<Client> clients, String firstName, String lastName,
                                 String phoneNumber, String email) {
        Client client = new Client(firstName, lastName, phoneNumber, email);
        clients.add(client);
    }

    public static void removeClientById(List<Client> clients, List<Rental> rentals, String clientId) {
        Client client = findClientById(clients, clientId);

        if (client == null) {
            System.out.println("Client with ID " + clientId + " not found.");
            return;
        }

        boolean hasActiveRental = rentals.stream()
            .anyMatch(r -> r.getClient().getId().equals(clientId) && !r.isReturned());

        if (hasActiveRental) {
            System.out.println("Cannot remove client " + clientId + ": they have active rentals.");
            return;
        }

        clients.remove(client);
        System.out.println("Client " + clientId + " removed successfully.");
    }

    // ------------------- BIKE -------------------

    public static void addBike(List<Bike> bikes, Types type, String marka, String model,
                               String wheelSize, String description, int pricePerH) {
        Bike bike = new Bike(type, marka, model, wheelSize, description, pricePerH);
        bikes.add(bike);
    }

    public static void removeBikeById(List<Bike> bikes, String bikeId, List<Rental> rentals) {
        Bike bike = findBikeById(bikes, bikeId);
        if (bike == null) {
            System.out.println("Bike not found.");
            return;
        }

        boolean isInUse = rentals.stream()
            .anyMatch(r -> r.getBike().getId().equals(bikeId) && !r.isReturned());

        if (isInUse) {
            System.out.println("Cannot remove bike: it is currently rented.");
            return;
        }

        bikes.remove(bike);
        System.out.println("Bike removed successfully.");
    }

    // ------------------- TYPES -------------------

    public static void addType(List<Types> types, String name, String description) {
        Types type = new Types(name, description);
        types.add(type);
    }

    public static void removeTypeById(List<Types> types, String typeId) {
        types.removeIf(t -> t.getId().equals(typeId));
    }
}
