package pl.wit.bikerental.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Krzysztof Mickiewicz
 * @version 1.0
 * @since 2025-06-15
 */

class BikeTest {

    @BeforeEach
    void resetBikeIdCounter() {
        Bike.setIdCount(0);
    }

    @Test
    void BikeUnknownIdTest() {
        Types gorski = new Types("Górski", "Rower do jazdy w trudnym terenie");
        Bike bike = new Bike(gorski, "Kross", "Level 5.0", "29", "Aluminiowa rama, amortyzator", 25);

        assertEquals("B1", bike.getId());
        assertEquals("Górski", bike.getType().getName());
        assertEquals("Kross", bike.getBrand());
        assertEquals("Level 5.0", bike.getModel());
        assertEquals("29", bike.getWheelSize());
        assertEquals("Aluminiowa rama, amortyzator", bike.getDescription());
        assertEquals(25, bike.getPricePerH());
        assertFalse(bike.isRented());
    }

    @Test
    void BikeKnownIdTest() {
        Types miejski = new Types("Miejski", "Do jazdy po mieście");
        Bike bike = new Bike("B99", miejski, "Romet", "City Bike", "28", "Koszyk, błotniki", 18, true);

        assertEquals("B99", bike.getId());
        assertEquals("Miejski", bike.getType().getName());
        assertEquals("Romet", bike.getBrand());
        assertEquals("City Bike", bike.getModel());
        assertEquals("28", bike.getWheelSize());
        assertEquals("Koszyk, błotniki", bike.getDescription());
        assertEquals(18, bike.getPricePerH());
        assertTrue(bike.isRented());
    }

    @Test
    void SettersTest() {
        Types szosowy = new Types("Szosowy", "Lekki rower do jazdy po asfalcie");
        Bike bike = new Bike(szosowy, "Specialized", "Allez", "28", "Szosa startowa", 30);

        Types trekkingowy = new Types("Trekkingowy", "Rower do długich tras");
        bike.setType(trekkingowy);
        bike.setBrand("Trek");
        bike.setModel("FX 3");
        bike.setWheelSize("28");
        bike.setDescription("Zmieniony opis");
        bike.setPricePerH(35);
        bike.setRented(true);

        assertEquals("Trekkingowy", bike.getType().getName());
        assertEquals("Trek", bike.getBrand());
        assertEquals("FX 3", bike.getModel());
        assertEquals("28", bike.getWheelSize());
        assertEquals("Zmieniony opis", bike.getDescription());
        assertEquals(35, bike.getPricePerH());
        assertTrue(bike.isRented());
    }

    @Test
    void IdCountTest() {
        assertEquals(0, Bike.getIdCount());

        new Bike(new Types("Górski", ""), "Test", "1", "26", "", 10);
        new Bike(new Types("Górski", ""), "Test", "2", "26", "", 10);

        assertEquals(2, Bike.getIdCount());
    }
}