package pl.wit.bikerental.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Rental class.
 */
/**
 * @author Krzysztof Mickiewicz
 *
 */
class RentalTest {

    private Client client;
    private Bike bike;
    private LocalDateTime start;
    private LocalDateTime plannedEnd;

    @BeforeEach
    void setUp() {
        Rental.setRentalIdCount(0);
        Client.setClientIdCount(0);
        Bike.setIdCount(0);
        Types.setTypeIdCount(0);

        Types type = new Types("Górski", "Rower do jazdy po górach");
        bike = new Bike(type, "Kross", "Hexagon", "27.5", "MTB", 20);
        client = new Client("Jan", "Kowalski", "123456789", "jan@example.com");
        start = LocalDateTime.of(2025, 6, 22, 12, 0);
        plannedEnd = LocalDateTime.of(2025, 6, 23, 12, 0);
    }

    @Test
    void rentalAutoIdTest() {
        Rental rental = new Rental(client, bike, start, plannedEnd);
        assertEquals("R1", rental.getId());
        assertEquals(client, rental.getClient());
        assertEquals(bike, rental.getBike());
        assertEquals(start, rental.getStart());
        assertEquals(plannedEnd, rental.getPlannedEnd());
        assertNull(rental.getActualReturnDate());
        assertFalse(rental.isReturned());
    }

    @Test
    void rentalKnownIdTest() {
        LocalDateTime actualReturn = LocalDateTime.of(2025, 6, 24, 10, 0);
        Rental rental = new Rental("R42", client, bike, start, plannedEnd, actualReturn, true);
        assertEquals("R42", rental.getId());
        assertEquals(client, rental.getClient());
        assertEquals(bike, rental.getBike());
        assertEquals(start, rental.getStart());
        assertEquals(plannedEnd, rental.getPlannedEnd());
        assertEquals(actualReturn, rental.getActualReturnDate());
        assertTrue(rental.isReturned());
    }

    @Test
    void rentalSettersTest() {
        Rental rental = new Rental(client, bike, start, plannedEnd);

        // Change client
        Client newClient = new Client("Anna", "Nowak", "111222333", "anna@nowak.pl");
        rental.setClient(newClient);
        assertEquals(newClient, rental.getClient());

        // Change bike
        Types miejski = new Types("Miejski", "Rower do miasta");
        Bike newBike = new Bike(miejski, "Romet", "City", "28", "Miasto", 15);
        rental.setBike(newBike);
        assertEquals(newBike, rental.getBike());

        // Change start and plannedEnd
        LocalDateTime newStart = start.plusDays(1);
        LocalDateTime newEnd = plannedEnd.plusDays(1);
        rental.setStart(newStart);
        rental.setPlannedEnd(newEnd);
        assertEquals(newStart, rental.getStart());
        assertEquals(newEnd, rental.getPlannedEnd());

        // Set actual return date
        LocalDateTime returnDate = newEnd.plusHours(2);
        rental.setActualReturnDate(returnDate);
        assertEquals(returnDate, rental.getActualReturnDate());

        // Set returned
        rental.setReturned(true);
        assertTrue(rental.isReturned());
    }

    @Test
    void rentalIdCountTest() {
        assertEquals(0, Rental.getRentalIdCount());
        new Rental(client, bike, start, plannedEnd);
        new Rental(client, bike, start, plannedEnd);
        assertEquals(2, Rental.getRentalIdCount());
        Rental.setRentalIdCount(5);
        assertEquals(5, Rental.getRentalIdCount());
    }
}