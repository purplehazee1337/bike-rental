package pl.wit.bikerental.reporting;

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
 * Unit tests for class BikeReport
 */
/**
 * @author Krzysztof Mickiewicz
 *
 */

class BikeReportTest {

    private List<Bike> bikes;
    private List<Rental> rentals;
    private Types gorski;
    private Types miejski;
    private Client client;

    @BeforeEach
    void setUp() {
        Bike.setIdCount(0);
        Rental.setRentalIdCount(0);
        Client.setClientIdCount(0);
        Types.setTypeIdCount(0);

        gorski = new Types("Górski", "Rower górski");
        miejski = new Types("Miejski", "Rower miejski");
        client = new Client("Jan", "Kowalski", "123456789", "jan@example.com");

        bikes = new ArrayList<>();
        bikes.add(new Bike(gorski, "Kross", "Hexagon", "27.5", "MTB", 20)); // B1
        bikes.add(new Bike(miejski, "Romet", "City", "28", "Miasto", 15)); // B2
        bikes.add(new Bike(gorski, "Merida", "Big.Nine", "29", "MTB Pro", 30)); // B3

        rentals = new ArrayList<>();
    }

    @Test
    void unrentedBikesTest() {
        bikes.get(0).setRented(true); // B1 rented
        bikes.get(1).setRented(false); // B2 not rented
        bikes.get(2).setRented(false); // B3 not rented

        List<Bike> unrented = Raports.unrentedBikes(bikes);
        assertEquals(2, unrented.size());
        assertTrue(unrented.contains(bikes.get(1)));
        assertTrue(unrented.contains(bikes.get(2)));
        assertFalse(unrented.contains(bikes.get(0)));
    }

    @Test
    void currentlyRentedTest() {
        // Rental 1: actualReturnDate == null, plannedEnd in future
        LocalDateTime now = LocalDateTime.now();
        Rental rental1 = new Rental(client, bikes.get(0), now.minusDays(1), now.plusDays(1));
        rentals.add(rental1);

        // Rental 2: actualReturnDate == null, plannedEnd in past (overdue)
        Rental rental2 = new Rental(client, bikes.get(1), now.minusDays(3), now.minusDays(1));
        rentals.add(rental2);

        // Rental 3: actualReturnDate != null (returned)
        Rental rental3 = new Rental(client, bikes.get(2), now.minusDays(5), now.minusDays(3));
        rental3.setActualReturnDate(now.minusDays(2));
        rental3.setReturned(true);
        rentals.add(rental3);

        List<Bike> currentlyRented = Raports.currentlyRentedBikes(rentals);

        // Should include both rental1 and rental2 (both actualReturnDate == null)
        assertEquals(2, currentlyRented.size());
        assertTrue(currentlyRented.contains(bikes.get(0)));
        assertTrue(currentlyRented.contains(bikes.get(1)));
        assertFalse(currentlyRented.contains(bikes.get(2)));
    }

    @Test
    void overtimeRentedTest() {
        LocalDateTime now = LocalDateTime.now();
        // Rental 1: overdue (not returned, plannedEnd in the past)
        Rental rental1 = new Rental(client, bikes.get(0), now.minusDays(3), now.minusDays(1));
        rentals.add(rental1);

        // Rental 2: not overdue (not returned, plannedEnd in future)
        Rental rental2 = new Rental(client, bikes.get(1), now, now.plusDays(2));
        rentals.add(rental2);

        // Rental 3: returned (doesn't matter what plannedEnd)
        Rental rental3 = new Rental(client, bikes.get(2), now.minusDays(10), now.minusDays(7));
        rental3.setReturned(true);
        rental3.setActualReturnDate(now.minusDays(6));
        rentals.add(rental3);

        List<Bike> overtime = Raports.overtimeRentedBikes(rentals);

        // Should include only rental1/bikes.get(0)
        assertEquals(1, overtime.size());
        assertTrue(overtime.contains(bikes.get(0)));
        assertFalse(overtime.contains(bikes.get(1)));
        assertFalse(overtime.contains(bikes.get(2)));
    }

    @Test
    void emptyListsTest() {
        assertTrue(Raports.unrentedBikes(new ArrayList<>()).isEmpty());
        assertTrue(Raports.currentlyRentedBikes(new ArrayList<>()).isEmpty());
        assertTrue(Raports.overtimeRentedBikes(new ArrayList<>()).isEmpty());
    }
}