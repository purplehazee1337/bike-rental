package pl.wit.bikerental.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Krzysztof Mickiewicz
 * @version 1.0
 * @since 2025-06-15
 */

class BikeTest {

    @Test
    void BikeTest1() {
        Bike bike = new Bike("Merida", 20);
        assertEquals("Merida", bike.getName());
        assertEquals(20, bike.getPricePerH());
        assertFalse(bike.getIsRented(), "Bike should not be rented by default");
    }

    @Test
    void BikeTest2() {
        Bike bike = new Bike("Trek", 25, true);
        assertEquals("Trek", bike.getName());
        assertEquals(25, bike.getPricePerH());
        assertTrue(bike.getIsRented(), "Bike should be rented");
    }

    @Test
    void SetNameTest() {
        Bike bike = new Bike("OldName", 15);
        bike.setName("NewName");
        assertEquals("NewName", bike.getName());
    }

    @Test
    void SetPricePerHTest() {
        Bike bike = new Bike("Bike", 10);
        bike.setPricePerH(30);
        assertEquals(30, bike.getPricePerH());
    }

    @Test
    void SetIsRentedTest() {
        Bike bike = new Bike("Bike", 10);
        bike.setIsRented(true);
        assertTrue(bike.getIsRented());
        bike.setIsRented(false);
        assertFalse(bike.getIsRented());
    }
}
