package pl.wit.bikerental.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Client} class.
 * <p>
 * Tests include automatic and manual ID assignment, field accessors and mutators, and the static client ID counter.
 * </p>
 * 
 * @author Krzysztof Mickiewicz
 */
class ClientTest {

    /**
     * Resets the client ID counter before each test to ensure test independence.
     */
    @BeforeEach
    void resetClientIdCountTest() {
        Client.setClientIdCount(0);
    }

    /**
     * Tests creation of a {@link Client} object with an automatically generated ID and verifies all fields.
     */
    @Test
    void clientAutoIdTest() {
        Client client = new Client("Krzysztof", "Mickiewicz", "123456789", "krzysztof@example.com");
        assertEquals("C1", client.getId());
        assertEquals("Krzysztof", client.getFirstName());
        assertEquals("Mickiewicz", client.getLastName());
        assertEquals("123456789", client.getPhoneNumber());
        assertEquals("krzysztof@example.com", client.getEmail());
    }

    /**
     * Tests creation of a {@link Client} object with a manually provided ID and verifies all fields.
     */
    @Test
    void clientKnownIdTest() {
        Client client = new Client("CID42", "Anna", "Nowak", "987654321", "anna@nowak.pl");
        assertEquals("CID42", client.getId());
        assertEquals("Anna", client.getFirstName());
        assertEquals("Nowak", client.getLastName());
        assertEquals("987654321", client.getPhoneNumber());
        assertEquals("anna@nowak.pl", client.getEmail());
    }

    /**
     * Tests all setter methods for the {@link Client} class by updating all fields and verifying their values.
     */
    @Test
    void settersTest() {
        Client client = new Client("Jan", "Kowalski", "111222333", "jan@kowalski.com");
        client.setFirstName("Adam");
        client.setLastName("Nowy");
        client.setPhoneNumber("555666777");
        client.setEmail("adam.nowy@domain.com");

        assertEquals("Adam", client.getFirstName());
        assertEquals("Nowy", client.getLastName());
        assertEquals("555666777", client.getPhoneNumber());
        assertEquals("adam.nowy@domain.com", client.getEmail());
    }

    /**
     * Tests the static client ID counter, ensuring it increments with new instances and can be set directly.
     */
    @Test
    void clientIdCountTest() {
        assertEquals(0, Client.getClientIdCount());
        new Client("A", "B", "1", "a@b.com");
        new Client("C", "D", "2", "c@d.com");
        assertEquals(2, Client.getClientIdCount());
        Client.setClientIdCount(10);
        assertEquals(10, Client.getClientIdCount());
    }
}