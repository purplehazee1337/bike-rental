package pl.wit.bikerental.storage;

import org.junit.jupiter.api.*;
import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Types;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Krzysztof Mickiewicz
 * @version 1.0
 * @since 2025-06-15
 */

class DatabaseTest {

    private final String testPath = "./src/test/resources/data/";
    private final String typesFile = testPath + "types.dat";
    private final String bikesFile = testPath + "bikes.dat";

    @BeforeEach
    void setUp() {
        // Ustaw testową ścieżkę
        new File(testPath).mkdirs();
        Database.setBasePath(testPath);
        Bike.setIdCount(0);
        Types.setTypeIdCount(0);
    }

    @AfterEach
    void tearDown() {
        // Czyści pliki po teście
        new File(typesFile).delete();
        new File(bikesFile).delete();
    }

    @Test
    void testSaveAndLoadTypesAndBikes() {
        // Przygotuj dane
        Types gorski = new Types("Górski", "Do jazdy w górach");
        Types miejski = new Types("Miejski", "Do jazdy po mieście");

        List<Types> types = List.of(gorski, miejski);
        List<Bike> bikes = new ArrayList<>();
        bikes.add(new Bike(gorski, "Kross", "Level 5.0", "29", "Aluminiowa rama", 25));
        bikes.add(new Bike(miejski, "Romet", "City", "28", "Koszyk", 18));

        DataBundle bundle = new DataBundle(types, bikes, new ArrayList<>(), new ArrayList<>());
        Database.saveAll(bundle);

        // Odczytaj dane
        DataBundle loaded = Database.readAll();

        assertEquals(2, loaded.types.size());
        assertEquals(2, loaded.bikes.size());

        // Sprawdź pierwszy typ
        Types loadedType1 = loaded.types.get(0);
        assertEquals("Górski", loadedType1.getName());
        assertEquals("Do jazdy w górach", loadedType1.getDescription());

        // Sprawdź pierwszy rower
        Bike loadedBike = loaded.bikes.get(0);
        assertEquals("Kross", loadedBike.getBrand());
        assertEquals("Level 5.0", loadedBike.getModel());
        assertEquals("29", loadedBike.getWheelSize());
        assertEquals("Aluminiowa rama", loadedBike.getDescription());
        assertEquals(25, loadedBike.getPricePerH());
        assertEquals("Górski", loadedBike.getType().getName());
    }
}