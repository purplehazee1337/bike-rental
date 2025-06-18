package pl.wit.bikerental.storage;

import java.util.List;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;

public class DataBundle {
    public List<Types> types;
    public List<Bike> bikes;
    public List<Client> clients;
    public List<Rental> rentals;

    public DataBundle(List<Types> types, List<Bike> bikes, List<Client> clients, List<Rental> rentals) {
        this.types = types;
        this.bikes = bikes;
        this.clients = clients;
        this.rentals = rentals;
    }
}