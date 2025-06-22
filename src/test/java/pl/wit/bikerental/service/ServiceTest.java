package pl.wit.bikerental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Service class.
 *
 * @author Krzysztof Mickiewicz
 */
class ServiceTest {

    private List<Types> types;
    private List<Bike> bikes;
    private List<Client> clients;
    private List<Rental> rentals;

    private Types gorski;
    private Types miejski;
    private Client client;
    private Bike bike;

    @BeforeEach
    void setUp() {
        Types.setTypeIdCount(0);
        Bike.setIdCount(0);
        Client.setClientIdCount(0);
        Rental.setRentalIdCount(0);

        types = new ArrayList<>();
        bikes = new ArrayList<>();
        clients = new ArrayList<>();
        rentals = new ArrayList<>();

        gorski = new Types("Górski", "Rower górski");
        miejski = new Types("Miejski", "Rower miejski");
        types.add(gorski);
        types.add(miejski);

        Service.addClient(clients, "Jan", "Kowalski", "123456789", "jan@example.com");
        client = clients.get(0);

        Service.addBike(bikes, gorski, "Kross", "Hexagon", "27.5", "MTB", 20);
        bike = bikes.get(0);
    }

    @Test
    void addTypeTest() {
        int prev = types.size();
        Service.addType(types, "Szosowy", "Do jazdy po asfalcie");
        assertEquals(prev + 1, types.size());
        assertEquals("Szosowy", types.get(types.size() - 1).getName());
    }

    @Test
    void addBikeTest() {
        int prev = bikes.size();
        Service.addBike(bikes, miejski, "Romet", "City", "28", "Miasto", 15);
        assertEquals(prev + 1, bikes.size());
        assertEquals("Romet", bikes.get(bikes.size() - 1).getBrand());
    }

    @Test
    void addClientTest() {
        int prev = clients.size();
        Service.addClient(clients, "Anna", "Nowak", "987654321", "anna@nowak.com");
        assertEquals(prev + 1, clients.size());
        assertEquals("Anna", clients.get(clients.size() - 1).getFirstName());
    }

    @Test
    void newRentalAndCompleteRentalTest() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusHours(2);

        Service.newRental(rentals, bikes, clients, bike.getId(), client.getId(), now, end);

        assertEquals(1, rentals.size());
        assertTrue(bike.isRented());

        Rental rental = rentals.get(0);
        assertEquals(bike, rental.getBike());
        assertEquals(client, rental.getClient());
        assertFalse(rental.isReturned());

        Service.completeRental(rentals, rental.getId());

        assertTrue(rental.isReturned());
        assertFalse(bike.isRented());
        assertNotNull(rental.getActualReturnDate());
    }

    @Test
    void removeBikeByIdTest() throws Exception {
        Bike newBike = new Bike(miejski, "Romet", "City", "28", "Miasto", 15);
        bikes.add(newBike);
        int prev = bikes.size();

        Service.removeBikeById(bikes, newBike.getId(), rentals);

        assertEquals(prev - 1, bikes.size());
        assertFalse(bikes.contains(newBike));
    }

    @Test
    void removeBikeByIdWhenRentedThrowsTest() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusHours(2);

        Service.newRental(rentals, bikes, clients, bike.getId(), client.getId(), now, end);

        Exception ex = assertThrows(Exception.class, () ->
                Service.removeBikeById(bikes, bike.getId(), rentals)
        );
        assertTrue(ex.getMessage().contains("wypożyczony"));
    }

    @Test
    void removeClientByIdTest() throws Exception {
        Client newClient = new Client("Joanna", "Testowa", "111222333", "joanna@test.com");
        clients.add(newClient);
        int prev = clients.size();

        Service.removeClientById(clients, rentals, newClient.getId());

        assertEquals(prev - 1, clients.size());
        assertFalse(clients.contains(newClient));
    }

    @Test
    void removeClientByIdWithActiveRentalThrowsTest() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusHours(2);

        Service.newRental(rentals, bikes, clients, bike.getId(), client.getId(), now, end);

        Exception ex = assertThrows(Exception.class, () ->
                Service.removeClientById(clients, rentals, client.getId())
        );
        assertTrue(ex.getMessage().contains("aktywne wypożyczenie"));
    }

    @Test
    void removeTypeByIdTest() throws Exception {
        Types newType = new Types("Testowy", "Opis");
        types.add(newType);
        int prev = types.size();

        Service.removeTypeById(types, newType.getId(), bikes);

        assertEquals(prev - 1, types.size());
        assertFalse(types.contains(newType));
    }

    @Test
    void removeTypeByIdWhenInUseThrowsTest() {
        Exception ex = assertThrows(Exception.class, () ->
                Service.removeTypeById(types, gorski.getId(), bikes)
        );
        assertTrue(ex.getMessage().contains("w użyciu"));
    }

    @Test
    void editBikeByIdTest() throws Exception {
        Service.editBikeById(bikes, bike.getId(), "Trek", "FX 3", "29", "Opis zmieniony", 25);
        Bike edited = bikes.get(0);

        assertEquals("Trek", edited.getBrand());
        assertEquals("FX 3", edited.getModel());
        assertEquals("29", edited.getWheelSize());
        assertEquals("Opis zmieniony", edited.getDescription());
        assertEquals(25, edited.getPricePerH());
    }

    @Test
    void editClientByIdTest() throws Exception {
        Service.editClientById(clients, client.getId(), "Krzysztof", "Mickiewicz", "222111333", "krzysztof@example.com");
        Client edited = clients.get(0);

        assertEquals("Krzysztof", edited.getFirstName());
        assertEquals("Mickiewicz", edited.getLastName());
        assertEquals("222111333", edited.getPhoneNumber());
        assertEquals("krzysztof@example.com", edited.getEmail());
    }

    @Test
    void editRentalByIdTest() throws Exception {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime plannedEnd = start.plusHours(2);

        Service.newRental(rentals, bikes, clients, bike.getId(), client.getId(), start, plannedEnd);
        Rental rental = rentals.get(0);

        LocalDateTime newStart = start.plusDays(1);
        LocalDateTime newEnd = plannedEnd.plusDays(1);

        Service.editRentalById(rentals, rental.getId(), newStart, newEnd);

        assertEquals(newStart, rental.getStart());
        assertEquals(newEnd, rental.getPlannedEnd());
    }

    @Test
    void editTypeByIdTest() throws Exception {
        Service.editTypeById(types, gorski.getId(), "Nowy typ", "Nowy opis");
        assertEquals("Nowy typ", gorski.getName());
        assertEquals("Nowy opis", gorski.getDescription());
    }

    @Test
    void findersTest() {
        assertEquals(bike, Service.findBikeById(bikes, bike.getId()));
        assertNull(Service.findBikeById(bikes, "NOT_FOUND"));

        assertEquals(client, Service.findClientById(clients, client.getId()));
        assertNull(Service.findClientById(clients, "NOT_FOUND"));

        assertEquals(gorski, Service.findTypeById(types, gorski.getId()));
        assertNull(Service.findTypeById(types, "NOT_FOUND"));

        // Rental finder
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);
        try {
			Service.newRental(rentals, bikes, clients, bike.getId(), client.getId(), start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Rental rental = rentals.get(0);
        assertEquals(rental, Service.findRentalById(rentals, rental.getId()));
        assertNull(Service.findRentalById(rentals, "NOT_FOUND"));
    }
}