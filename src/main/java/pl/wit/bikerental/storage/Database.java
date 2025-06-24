package pl.wit.bikerental.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;

/**
 * Handles reading and saving data for the bike rental system.
 * 
 * @author Wojciech Jechowski
 */
public class Database {
    /** Base path for data files. */
    private static String basePath = "./";

    /** Sets the base path for data files. */
    public static void setBasePath(String basePath) {
        Database.basePath = basePath;
    }

    /** Reads all data (types, clients, bikes, rentals) from files. */
    public static DataBundle readAll() {
        List<Types> types = readTypes("types.dat");
        List<Client> clients = readClients("clients.dat");
        List<Bike> bikes = readBikes("bikes.dat", types);
        List<Rental> rentals = readRentals("rentals.dat", clients, bikes);
        System.out.println("Data loaded.");
        return new DataBundle(types, bikes, clients, rentals);
    }

    /** Saves all data (types, clients, bikes, rentals) to files. */
    public static void saveAll(DataBundle bundle) {
        saveTypes(bundle.types, "types.dat");
        saveBikes(bundle.bikes, bundle.types, "bikes.dat");
        saveClients(bundle.clients, "clients.dat");
        saveRentals(bundle.rentals, bundle.clients, bundle.bikes, "rentals.dat");
        System.out.println("Data saved.");
    }

    /** Prints all loaded data to the console. */
    public static void printAll(DataBundle bundle) {
        System.out.println("\nLoaded Types:");
        bundle.types.forEach(type -> System.out.println(" - " + type.getName()));

        System.out.println("\nLoaded Bikes:");
        bundle.bikes.forEach(bike ->
            System.out.println(" - " + bike.getModel() + " (" + bike.getType().getName() + ") | Rented: " + (bike.isRented() ? "Yes" : "No"))
        );

        System.out.println("\nLoaded Clients:");
        bundle.clients.forEach(client ->
            System.out.println(" - " + client.getFirstName() + " " + client.getLastName())
        );

        System.out.println("\nLoaded Rentals:");
        bundle.rentals.forEach(rental ->
            System.out.println(" - " + rental.getId() + ": " +
                rental.getClient().getFirstName() + " rented " +
                rental.getBike().getModel() + " on " + rental.getStart() +
                " | Returned: " + (rental.isReturned() ? "Yes" : "No"))
        );
    }

    /** Saves the list of bikes to a file. */
    private static void saveBikes(List<Bike> bikes, List<Types> types, String fileName) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(basePath + fileName))) {
            dos.writeInt(Bike.getIdCount());
            dos.writeInt(bikes.size());

            for (Bike bike : bikes) {
                dos.writeUTF(bike.getId());

                int typeIndex = types.indexOf(bike.getType());
                dos.writeInt(typeIndex);

                dos.writeUTF(bike.getBrand());
                dos.writeUTF(bike.getModel());
                dos.writeUTF(bike.getWheelSize());
                dos.writeUTF(bike.getDescription());
                dos.writeInt(bike.getPricePerH());
                dos.writeBoolean(bike.isRented());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /** Reads the list of bikes from a file. */
    private static List<Bike> readBikes(String fileName, List<Types> types) {
        List<Bike> bikes = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(basePath + fileName))) {
            int idCount = dis.readInt();
            Bike.setIdCount(idCount);

            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                String id = dis.readUTF();
                int typeIndex = dis.readInt();
                Types type = types.get(typeIndex);

                String marka = dis.readUTF();
                String model = dis.readUTF();
                String rozmiarKola = dis.readUTF();
                String opis = dis.readUTF();
                int pricePerH = dis.readInt();
                boolean isRented = dis.readBoolean();

                bikes.add(new Bike(id, type, marka, model, rozmiarKola, opis, pricePerH, isRented));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return bikes;
    }

    /** Saves the list of types to a file. */
    private static void saveTypes(List<Types> types, String fileName) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(basePath + fileName))) {
            dos.writeInt(types.size());
            for (Types type : types) {
                dos.writeUTF(type.getName());
                dos.writeUTF(type.getDescription());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /** Reads the list of types from a file. */
    private static List<Types> readTypes(String fileName) {
        List<Types> types = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(basePath + fileName))) {
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                String nazwa = dis.readUTF();
                String opis = dis.readUTF();
                types.add(new Types(nazwa, opis));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return types;
    }

    /** Saves the list of clients to a file. */
    private static void saveClients(List<Client> clients, String fileName) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(basePath + fileName))) {
            dos.writeInt(Client.getClientIdCount());
            dos.writeInt(clients.size());

            for (Client c : clients) {
                dos.writeUTF(c.getId());
                dos.writeUTF(c.getFirstName());
                dos.writeUTF(c.getLastName());
                dos.writeUTF(c.getPhoneNumber());
                dos.writeUTF(c.getEmail());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /** Reads the list of clients from a file. */
    private static List<Client> readClients(String fileName) {
        List<Client> clients = new ArrayList<>();

        try (DataInputStream dis = new DataInputStream(new FileInputStream(basePath + fileName))) {
            int idCount = dis.readInt();
            Client.setClientIdCount(idCount);

            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                String id = dis.readUTF();
                String firstName = dis.readUTF();
                String lastName = dis.readUTF();
                String phone = dis.readUTF();
                String email = dis.readUTF();

                clients.add(new Client(id, firstName, lastName, phone, email));
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return clients;
    }

    /** Saves the list of rentals to a file. */
    private static void saveRentals(List<Rental> rentals, List<Client> clients, List<Bike> bikes, String fileName) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(basePath + fileName))) {
            dos.writeInt(Rental.getRentalIdCount());
            dos.writeInt(rentals.size());

            for (Rental rental : rentals) {
                dos.writeUTF(rental.getId());

                int clientIndex = clients.indexOf(rental.getClient());
                int bikeIndex = bikes.indexOf(rental.getBike());

                dos.writeInt(clientIndex);
                dos.writeInt(bikeIndex);

                dos.writeLong(rental.getStart().toEpochSecond(ZoneOffset.UTC));

                if (rental.getPlannedEnd() != null) {
                    dos.writeBoolean(true);
                    dos.writeLong(rental.getPlannedEnd().toEpochSecond(ZoneOffset.UTC));
                } else {
                    dos.writeBoolean(false);
                }

                if (rental.getActualReturnDate() != null) {
                    dos.writeBoolean(true);
                    dos.writeLong(rental.getActualReturnDate().toEpochSecond(ZoneOffset.UTC));
                } else {
                    dos.writeBoolean(false);
                }

                dos.writeBoolean(rental.isReturned());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /** Reads the list of rentals from a file. */
    private static List<Rental> readRentals(String fileName, List<Client> clients, List<Bike> bikes) {
        List<Rental> rentals = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(basePath + fileName))) {
            int idCount = dis.readInt();
            Rental.setRentalIdCount(idCount);

            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                String id = dis.readUTF();

                int clientIndex = dis.readInt();
                int bikeIndex = dis.readInt();

                Client client = clients.get(clientIndex);
                Bike bike = bikes.get(bikeIndex);

                LocalDateTime start = LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC);

                LocalDateTime plannedEnd = null;
                if (dis.readBoolean()) {
                    plannedEnd = LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC);
                }

                LocalDateTime actualReturnDate = null;
                if (dis.readBoolean()) {
                    actualReturnDate = LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC);
                }

                boolean isReturned = dis.readBoolean();

                rentals.add(new Rental(id, client, bike, start, plannedEnd, actualReturnDate, isReturned));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return rentals;
    }
}
