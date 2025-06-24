package pl.wit.bikerental.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wit.bikerental.model.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GUI main dialog forms in the application.
 * <p>
 * These tests cover constructor and basic visibility,
 * verifying that each main dialog form can be created and its title is as expected.
 * </p>
 * 
 * @author Krzysztof Mickiewicz
 */
class UiFormsTest {

    private List<Bike> bikes;
    private List<Types> types;
    private List<Client> clients;
    private List<Rental> rentals;
    private MainFrame mainFrame;

    /**
     * Sets up model lists, resets counters, and initializes a default {@link MainFrame} before each test.
     */
    @BeforeEach
    void setUp() {
        Types.setTypeIdCount(0);
        Bike.setIdCount(0);
        Client.setClientIdCount(0);
        Rental.setRentalIdCount(0);

        types = new ArrayList<>();
        bikes = new ArrayList<>();
        clients = new ArrayList<>();
        rentals = new ArrayList<>();

        Types gorski = new Types("Górski", "Rower górski");
        types.add(gorski);
        Client client = new Client("Jan", "Kowalski", "123456789", "jan@example.com");
        clients.add(client);
        Bike bike = new Bike(gorski, "Kross", "Hexagon", "27.5", "MTB", 20);
        bikes.add(bike);
        Rental rental = new Rental(client, bike, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        rentals.add(rental);

        SwingUtilities.invokeLater(() -> mainFrame = new MainFrame(bikes, clients, rentals, types));
    }

    /**
     * Tests the constructor and title of {@link AddBikeForm}.
     */
    @Test
    void addBikeFormConstructorTest() {
        AddBikeForm form = new AddBikeForm(mainFrame, bikes, types);
        assertNotNull(form);
        assertEquals("Nowy rower", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link AddTypeForm}.
     */
    @Test
    void addTypeFormConstructorTest() {
        AddTypeForm form = new AddTypeForm(mainFrame, types);
        assertNotNull(form);
        assertEquals("Nowy typ roweru", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link EditBikeForm}.
     */
    @Test
    void editBikeFormConstructorTest() {
        EditBikeForm form = new EditBikeForm(mainFrame, bikes, types);
        assertNotNull(form);
        assertEquals("Edytuj rower", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link EditTypeForm}.
     */
    @Test
    void editTypeFormConstructorTest() {
        EditTypeForm form = new EditTypeForm(mainFrame, types);
        assertNotNull(form);
        assertEquals("Edytuj typ roweru", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link AddClientForm}.
     */
    @Test
    void addClientFormConstructorTest() {
        AddClientForm form = new AddClientForm(mainFrame, clients);
        assertNotNull(form);
        assertEquals("Nowy klient", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link EditClientForm}.
     */
    @Test
    void editClientFormConstructorTest() {
        EditClientForm form = new EditClientForm(mainFrame, clients);
        assertNotNull(form);
        assertEquals("Edytuj klienta", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link DeleteBikeForm}.
     */
    @Test
    void deleteBikeFormConstructorTest() {
        DeleteBikeForm form = new DeleteBikeForm(mainFrame, bikes, rentals);
        assertNotNull(form);
        assertEquals("Usuń rower", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link DeleteTypeForm}.
     */
    @Test
    void deleteTypeFormConstructorTest() {
        DeleteTypeForm form = new DeleteTypeForm(mainFrame, types, bikes);
        assertNotNull(form);
        assertEquals("Usuń typ roweru", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link DeleteClientForm}.
     */
    @Test
    void deleteClientFormConstructorTest() {
        DeleteClientForm form = new DeleteClientForm(mainFrame, clients, rentals);
        assertNotNull(form);
        assertEquals("Usuń klienta", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link AddRentalForm}.
     */
    @Test
    void addRentalFormConstructorTest() {
        AddRentalForm form = new AddRentalForm(mainFrame, bikes, clients, rentals);
        assertNotNull(form);
        assertEquals("Nowe wypożyczenie", form.getTitle());
    }

    /**
     * Tests the constructor and title of {@link ReturnRentalForm}.
     */
    @Test
    void returnRentalFormConstructorTest() {
        ReturnRentalForm form = new ReturnRentalForm(mainFrame, rentals);
        assertNotNull(form);
        assertEquals("Zwróć wypożyczenie", form.getTitle());
    }

    /**
     * Tests the constructor and title of the main application frame.
     */
    @Test
    void mainFrameConstructorTest() {
        MainFrame frame = new MainFrame(bikes, clients, rentals, types);
        assertNotNull(frame);
        assertEquals("Wypożyczalnia rowerów", frame.getTitle());
    }
}