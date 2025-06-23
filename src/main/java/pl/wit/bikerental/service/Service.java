package pl.wit.bikerental.service;

import pl.wit.bikerental.model.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides utility methods for managing bikes, clients, rentals, and types in the bike rental system.
 * This includes operations for finding, editing, adding, and removing objects from collections.
 * 
 * @author Wojciech Jechowski
 */
public class Service {

    // ------------------- FIND METHODS -------------------

    /** Finds a bike by its ID from the given list. */
    public static Bike findBikeById(List<Bike> bikes, String bikeId) {
        for (Bike bike : bikes) {
            if (bike.getId().equals(bikeId)) {
                return bike;
            }
        }
        return null;
    }

    /** Finds a client by their ID from the given list. */
    public static Client findClientById(List<Client> clients, String clientId) {
        for (Client client : clients) {
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    /** Finds a rental by its ID from the given list. */
    public static Rental findRentalById(List<Rental> rentals, String rentalId) {
        for (Rental rental : rentals) {
            if (rental.getId().equals(rentalId)) {
                return rental;
            }
        }
        return null;
    }

    /** Finds a bike type by its ID from the given list. */
    public static Types findTypeById(List<Types> types, String typeId) {
        for (Types type : types) {
            if (type.getId().equals(typeId)) {
                return type;
            }
        }
        return null;
    }

    // ------------------- EDIT METHODS -------------------

    /** Edits a bike's details identified by ID. */
    public static void editBikeById(List<Bike> bikes, String bikeId, String brand, String model,
                                    String wheelSize, String description, int pricePerH) throws Exception {
        Bike bike = findBikeById(bikes, bikeId);
        if (bike == null) {
            System.out.println("Bike with ID " + bikeId + " not found.");
            throw new Exception("Nie znaleziono roweru.");
        }
        bike.setBrand(brand);
        bike.setModel(model);
        bike.setWheelSize(wheelSize);
        bike.setDescription(description);
        bike.setPricePerH(pricePerH);
    }

    /** Edits a client's details identified by ID. */
    public static void editClientById(List<Client> clients, String clientId, String firstName,
                                      String lastName, String phoneNumber, String email) throws Exception {
        Client client = findClientById(clients, clientId);
        if (client == null) {
            System.out.println("Client with ID " + clientId + " not found.");
            throw new Exception("Nie znaleziono klienta.");
        }
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPhoneNumber(phoneNumber);
        client.setEmail(email);
    }

    /** Edits a rental's start and planned end time by ID. */
    public static void editRentalById(List<Rental> rentals, String rentalId, LocalDateTime newStart,
                                      LocalDateTime newPlannedEnd) throws Exception {
        Rental rental = findRentalById(rentals, rentalId);
        if (rental == null) {
            System.out.println("Rental with ID " + rentalId + " not found.");
            throw new Exception("Nie znaleziono wypożyczenia.");
        }
        rental.setStart(newStart);
        rental.setPlannedEnd(newPlannedEnd);
    }

    /** Edits a bike type's name and description by ID. */
    public static void editTypeById(List<Types> types, String typeId, String newName, String newDescription) throws Exception {
        Types type = findTypeById(types, typeId);
        if (type == null) {
            System.out.println("Type with ID " + typeId + " not found.");
            throw new Exception("Nie znaleziono typu roweru.");
        }
        type.setName(newName);
        type.setDescription(newDescription);
    }

    // ------------------- RENTAL -------------------

    /** Creates a new rental if the bike is available and both bike and client exist. */
    public static void newRental(List<Rental> rentals, List<Bike> bikes, List<Client> clients,
                                 String bikeId, String clientId, LocalDateTime start, LocalDateTime plannedEnd) throws Exception {
        Bike bike = findBikeById(bikes, bikeId);
        Client client = findClientById(clients, clientId);

        if (bike == null) {
            System.out.println("Bike with ID " + bikeId + " not found.");
            throw new Exception("Nie znaleziono roweru.");
        }

        if (client == null) {
            System.out.println("Client with ID " + clientId + " not found.");
            throw new Exception("Nie znaleziono klienta.");
        }

        if (bike.isRented()) {
            System.out.println("Bike " + bike.getId() + " is already rented.");
            throw new Exception("Rower jest już wypożyczony.");
        }

        Rental rental = new Rental(client, bike, start, plannedEnd);
        rentals.add(rental);
        bike.setRented(true);
        System.out.println("Rental created successfully: " + rental.getId());
    }

    /** Marks a rental as returned and updates the actual return date. */
    public static void completeRental(List<Rental> rentals, String rentalId) throws Exception {
        Rental rental = findRentalById(rentals, rentalId);
        if (rental != null) {
            if (rental.isReturned()) {
                System.out.println("Rental " + rentalId + " has already been returned.");
                throw new Exception("Wypożyczenie jest już zakończone");
            }

            rental.setReturned(true);
            rental.setActualReturnDate(LocalDateTime.now());
            rental.getBike().setRented(false);

            System.out.println("Rental " + rentalId + " marked as returned.");
        } else {
            System.out.println("Rental " + rentalId + " not found.");
            throw new Exception("Nie znaleziono wypożyczenia.");
        }
    }

    // ------------------- CLIENT -------------------

    /** Adds a new client to the client list. */
    public static void addClient(List<Client> clients, String firstName, String lastName,
                                 String phoneNumber, String email) {
        Client client = new Client(firstName, lastName, phoneNumber, email);
        clients.add(client);
    }

    /** Removes a client by ID if they have no active rentals. */
    public static void removeClientById(List<Client> clients, List<Rental> rentals, String clientId) throws Exception {
        Client client = findClientById(clients, clientId);

        if (client == null) {
            System.out.println("Client with ID " + clientId + " not found.");
            throw new Exception("Nie znaleziono klienta.");
        }

        boolean hasActiveRental = rentals.stream()
            .anyMatch(r -> r.getClient().getId().equals(clientId) && !r.isReturned());

        if (hasActiveRental) {
            System.out.println("Cannot remove client " + clientId + ": they have active rentals.");
            throw new Exception("Nie można usunąć klienta bo posiada aktywne wypożyczenie.");
        }

        clients.remove(client);
        System.out.println("Client " + clientId + " removed successfully.");
    }

    // ------------------- BIKE -------------------

    /** Adds a new bike to the bike list. */
    public static void addBike(List<Bike> bikes, Types type, String marka, String model,
                               String wheelSize, String description, int pricePerH) {
        Bike bike = new Bike(type, marka, model, wheelSize, description, pricePerH);
        bikes.add(bike);
    }

    /** Removes a bike by ID if it is not currently rented. */
    public static void removeBikeById(List<Bike> bikes, String bikeId, List<Rental> rentals) throws Exception {
        Bike bike = findBikeById(bikes, bikeId);
        if (bike == null) {
            System.out.println("Bike not found.");
            throw new Exception("Nie znaleziono roweru.");
        }

        boolean isInUse = rentals.stream()
            .anyMatch(r -> r.getBike().getId().equals(bikeId) && !r.isReturned());

        if (isInUse) {
            System.out.println("Cannot remove bike: it is currently rented.");
            throw new Exception("Nie udało się usunąć roweru bo jest on wypożyczony.");
        }

        bikes.remove(bike);
        System.out.println("Bike removed successfully.");
    }

    // ------------------- TYPES -------------------

    /** Adds a new bike type to the type list. */
    public static void addType(List<Types> types, String name, String description) {
        Types type = new Types(name, description);
        types.add(type);
    }

    /** Removes a bike type by ID if it is not associated with any existing bikes. */
    public static void removeTypeById(List<Types> types, String typeId, List<Bike> bikes) throws Exception {
        Types type = findTypeById(types, typeId);

        if (type == null) {
            System.out.println("type with ID " + typeId + " not found.");
            throw new Exception("Nie znaleziono typu roweru.");
        }

        boolean hasActiveRental = bikes.stream()
            .anyMatch(b -> b.getType().equals(type));

        if (hasActiveRental) {
            System.out.println("Cannot remove type " + typeId + ": it is in use.");
            throw new Exception("Nie można usunąć typu bo jest on w użyciu.");
        }

        types.remove(type);
        System.out.println("Type " + typeId + " removed successfully.");
    }
}
