package pl.wit.bikerental.storage;

import java.util.List;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;

/**
 * Holds collections of types, bikes, clients, and rentals.
 * 
 * @author Wojciech Jechowski
 */
public class DataBundle {
    /** List of types. */
    public List<Types> types;

    /** List of bikes. */
    public List<Bike> bikes;

    /** List of clients. */
    public List<Client> clients;

    /** List of rentals. */
    public List<Rental> rentals;

    /** Creates a DataBundle with all lists. */
    public DataBundle(List<Types> types, List<Bike> bikes, List<Client> clients, List<Rental> rentals) {
        this.types = types;
        this.bikes = bikes;
        this.clients = clients;
        this.rentals = rentals;
    }
}
