package pl.wit.bikerental.storage;

import org.junit.jupiter.api.*;
import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Database class (saveAll, readAll, printAll).
 *
 * @author Krzysztof Mickiewicz
 */
class DatabaseTest {

    private final String testPath = "./src/test/resources/data/";
    private final String typesFile = testPath + "types.dat";
    private final String bikesFile = testPath + "bikes.dat";
    private final String clientsFile = testPath + "clients.dat";
    private final String rentalsFile = testPath + "rentals.dat";

    @BeforeEach
    void setUp() {
        new File(testPath).mkdirs();
        Database.setBasePath(testPath);
        Bike.setIdCount(0);
        Types.setTypeIdCount(0);
        Client.setClientIdCount(0);
        Rental.setRentalIdCount(0);
    }

    @AfterEach
    void tearDown() {
        // Clean up files after test
        new File(typesFile).delete();
        new File(bikesFile).delete();
        new File(clientsFile).delete();
        new File(rentalsFile).delete();
    }

    @Test
    void saveAndLoadTypesAndBikesTest() {
        Types gorski = new Types("Górski", "Do jazdy w górach");
        Types miejski = new Types("Miejski", "Do jazdy po mieście");

        List<Types> types = List.of(gorski, miejski);
        List<Bike> bikes = new ArrayList<>();
        bikes.add(new Bike(gorski, "Kross", "Level 5.0", "29", "Aluminiowa rama", 25));
        bikes.add(new Bike(miejski, "Romet", "City", "28", "Koszyk", 18));

        DataBundle bundle = new DataBundle(types, bikes, new ArrayList<>(), new ArrayList<>());
        Database.saveAll(bundle);

        DataBundle loaded = Database.readAll();

        assertEquals(2, loaded.types.size());
        assertEquals(2, loaded.bikes.size());
        assertEquals("Górski", loaded.types.get(0).getName());
        assertEquals("Do jazdy w górach", loaded.types.get(0).getDescription());
        assertEquals("Kross", loaded.bikes.get(0).getBrand());
    }

    @Test
    void saveAndLoadClientsAndRentalsTest() {
        Types typ = new Types("Trek", "Opis");
        List<Types> types = List.of(typ);

        Bike bike = new Bike(typ, "Marin", "Bobcat", "29", "Sport", 20);
        List<Bike> bikes = List.of(bike);

        Client client = new Client("Jan", "Kowalski", "123456789", "jan@kowalski.com");
        List<Client> clients = List.of(client);

        LocalDateTime now = LocalDateTime.now();
        Rental rental = new Rental(client, bike, now, now.plusHours(5));
        List<Rental> rentals = List.of(rental);

        DataBundle bundle = new DataBundle(types, bikes, clients, rentals);
        Database.saveAll(bundle);

        DataBundle loaded = Database.readAll();
        assertEquals(1, loaded.clients.size());
        assertEquals(1, loaded.rentals.size());
        assertEquals("Jan", loaded.clients.get(0).getFirstName());
        assertEquals("Marin", loaded.bikes.get(0).getBrand());
        assertEquals("Trek", loaded.types.get(0).getName());
        assertEquals("Bobcat", loaded.rentals.get(0).getBike().getModel());
        assertEquals("Jan", loaded.rentals.get(0).getClient().getFirstName());
    }

    @Test
    void printAllDoesNotThrowTest() {
        Types typ = new Types("Testowy", "T");
        Bike bike = new Bike(typ, "Brand", "Model", "27.5", "Desc", 10);
        Client client = new Client("Imie", "Nazwisko", "000000000", "mail@mail.com");
        Rental rental = new Rental(client, bike, LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        DataBundle bundle = new DataBundle(
                List.of(typ),
                List.of(bike),
                List.of(client),
                List.of(rental)
        );

        assertDoesNotThrow(() -> Database.printAll(bundle));
    }
}