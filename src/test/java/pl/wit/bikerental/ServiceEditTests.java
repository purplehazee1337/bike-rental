package pl.wit.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;

class ServiceEditTests {

    private List<Bike> bikes;
    private List<Client> clients;
    private List<Rental> rentals;
    private List<Types> types;

    private Bike testBike;
    private Client testClient;
    private Rental testRental;
    private Types testType;

    @BeforeEach
    void setUp() {
        types = new ArrayList<>();
        bikes = new ArrayList<>();
        clients = new ArrayList<>();
        rentals = new ArrayList<>();

        // Setup type
        testType = new Types("MTB", "Mountain bike");
        types.add(testType);

        // Setup bike
        testBike = new Bike(testType, "Specialized", "Rockhopper", "29\"", "Nice bike", 20);
        bikes.add(testBike);

        // Setup client
        testClient = new Client("Anna", "Nowak", "987654321", "anna.nowak@example.com");
        clients.add(testClient);

        // Setup rental
        testRental = new Rental(testClient, testBike, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        rentals.add(testRental);
    }

    @Test
    void testEditBikeById() {
        boolean result = Service.editBikeById(bikes, testBike.getId(), "Trek", "Marlin 7", "27.5\"", "Updated bike", 25);
        assertTrue(result);

        Bike editedBike = Service.findBikeById(bikes, testBike.getId());
        assertEquals("Trek", editedBike.getBrand());
        assertEquals("Marlin 7", editedBike.getModel());
        assertEquals("27.5\"", editedBike.getWheelSize());
        assertEquals("Updated bike", editedBike.getDescription());
        assertEquals(25, editedBike.getPricePerH());
    }

    @Test
    void testEditClientById() {
        boolean result = Service.editClientById(clients, testClient.getId(), "Anna Maria", "Nowak-Kowalska", "123123123", "anna.m.nowak@example.com");
        assertTrue(result);

        Client editedClient = Service.findClientById(clients, testClient.getId());
        assertEquals("Anna Maria", editedClient.getFirstName());
        assertEquals("Nowak-Kowalska", editedClient.getLastName());
        assertEquals("123123123", editedClient.getPhoneNumber());
        assertEquals("anna.m.nowak@example.com", editedClient.getEmail());
    }

    @Test
    void testEditRentalById() {
        LocalDateTime newStart = LocalDateTime.now().minusDays(1);
        LocalDateTime newPlannedEnd = LocalDateTime.now().plusDays(5);

        boolean result = Service.editRentalById(rentals, testRental.getId(), newStart, newPlannedEnd);
        assertTrue(result);

        Rental editedRental = Service.findRentalById(rentals, testRental.getId());
        assertEquals(newStart, editedRental.getStart());
        assertEquals(newPlannedEnd, editedRental.getPlannedEnd());
    }

    @Test
    void testEditTypeById() {
        boolean result = Service.editTypeById(types, testType.getId(), "Road Bike", "Bike for road racing");
        assertTrue(result);

        Types editedType = Service.findTypeById(types, testType.getId());
        assertEquals("Road Bike", editedType.getName());
        assertEquals("Bike for road racing", editedType.getDescription());
    }

    @Test
    void testEditNonExistingBike() {
        boolean result = Service.editBikeById(bikes, "nonexistent", "Brand", "Model", "26\"", "desc", 10);
        assertFalse(result);
    }

    @Test
    void testEditNonExistingClient() {
        boolean result = Service.editClientById(clients, "nonexistent", "First", "Last", "000", "email@example.com");
        assertFalse(result);
    }

    @Test
    void testEditNonExistingRental() {
        boolean result = Service.editRentalById(rentals, "nonexistent", LocalDateTime.now(), LocalDateTime.now());
        assertFalse(result);
    }

    @Test
    void testEditNonExistingType() {
        boolean result = Service.editTypeById(types, "nonexistent", "Name", "desc");
        assertFalse(result);
    }
}
