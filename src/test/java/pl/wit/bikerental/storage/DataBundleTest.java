package pl.wit.bikerental.storage;

import org.junit.jupiter.api.Test;
import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DataBundle} class.
 * <p>
 * Verifies correct assignment and storage of types, bikes, clients, and rentals lists in the bundle.
 * </p>
 * 
 * @author Krzysztof Mickiewicz
 */
class DataBundleTest {

    /**
     * Tests the constructor of {@link DataBundle} to ensure all fields are correctly assigned.
     */
    @Test
    void dataBundleConstructorTest() {
        List<Types> types = new ArrayList<>();
        List<Bike> bikes = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();

        Types t = new Types("Górski", "Opis");
        types.add(t);

        Bike b = new Bike(t, "Kross", "Hexagon", "27.5", "Opis roweru", 20);
        bikes.add(b);

        Client c = new Client("Jan", "Kowalski", "123456789", "jan@kowalski.com");
        clients.add(c);

        Rental r = new Rental(c, b, java.time.LocalDateTime.now(), java.time.LocalDateTime.now().plusDays(1));
        rentals.add(r);

        DataBundle bundle = new DataBundle(types, bikes, clients, rentals);

        assertEquals(types, bundle.types);
        assertEquals(bikes, bundle.bikes);
        assertEquals(clients, bundle.clients);
        assertEquals(rentals, bundle.rentals);
    }
}