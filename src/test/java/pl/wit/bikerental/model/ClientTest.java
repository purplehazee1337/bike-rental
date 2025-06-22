package pl.wit.bikerental.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Krzysztof Mickiewicz
 * @version 1.0
 * @since 2025-06-22
 */
class ClientTest {

    @BeforeEach
    void resetClientIdCountTest() {
        Client.setClientIdCount(0);
    }

    @Test
    void clientAutoIdTest() {
        Client client = new Client("Krzysztof", "Mickiewicz", "123456789", "krzysztof@example.com");
        assertEquals("C1", client.getId());
        assertEquals("Krzysztof", client.getFirstName());
        assertEquals("Mickiewicz", client.getLastName());
        assertEquals("123456789", client.getPhoneNumber());
        assertEquals("krzysztof@example.com", client.getEmail());
    }

    @Test
    void clientKnownIdTest() {
        Client client = new Client("CID42", "Anna", "Nowak", "987654321", "anna@nowak.pl");
        assertEquals("CID42", client.getId());
        assertEquals("Anna", client.getFirstName());
        assertEquals("Nowak", client.getLastName());
        assertEquals("987654321", client.getPhoneNumber());
        assertEquals("anna@nowak.pl", client.getEmail());
    }

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