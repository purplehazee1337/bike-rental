package pl.wit.bikerental.storage;

import org.junit.jupiter.api.*;
import pl.wit.bikerental.model.Bike;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Krzysztof Mickiewicz
 * @version 1.0
 * @since 2025-06-15
 */

class DatabaseTest {

    private static final String TEST_FILE_NAME = "bikesTest.dat";
    private static final String TEST_FILE_PATH = "./src/main/resources/" + TEST_FILE_NAME;

    @AfterEach
    void cleanUp() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void SaveAndReadBikesTest() throws IOException {
        // Arrange
        List<Bike> bikesToSave = Arrays.asList(
                new Bike("Trek", 25, false),
                new Bike("Merida", 30, true)
        );

        // Act
        Database.saveBikes(bikesToSave, TEST_FILE_NAME);
        List<Bike> bikesRead = Database.readBikes(TEST_FILE_NAME);

        // Assert
        assertEquals(bikesToSave.size(), bikesRead.size());

        for (int i = 0; i < bikesToSave.size(); i++) {
            Bike expected = bikesToSave.get(i);
            Bike actual = bikesRead.get(i);

            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getPricePerH(), actual.getPricePerH());
            assertEquals(expected.getIsRented(), actual.getIsRented());
        }
    }

    @Test
    void ReadFromNonexistentFileTest() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<Bike> bikes = Database.readBikes("nonexistent_file.dat");
            assertNotNull(bikes); // even if error is logged, it should not return null
        });
    }
}