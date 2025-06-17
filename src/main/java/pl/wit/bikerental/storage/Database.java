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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;

public class Database {
	private static String basePath = "./src/main/resources/data/";
	private static final Logger dbLog = LogManager.getLogger(Database.class);
	
	public static void saveBikes(List<Bike> bikes, List<Types> types, String fileName) {
	    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(basePath + fileName))) {
	        dos.writeInt(Bike.getIdCount());
	        dos.writeInt(bikes.size());

	        for (Bike bike : bikes) {
	            dos.writeUTF(bike.getId());

	            // Find index of bike's type in types list
	            int typeIndex = types.indexOf(bike.getType());
	            dos.writeInt(typeIndex); // Save index, not full object

	            dos.writeUTF(bike.getMarka());
	            dos.writeUTF(bike.getModel());
	            dos.writeUTF(bike.getRozmiarKola());
	            dos.writeUTF(bike.getOpis());
	            dos.writeInt(bike.getPricePerH());
	            dos.writeBoolean(bike.isRented());
	        }

	        dbLog.debug("Bikes saved.");
	    } catch (IOException e) {
	        dbLog.error(e);
	    }
	}
	
	public static List<Bike> readBikes(String fileName, List<Types> types) {
	    List<Bike> bikes = new ArrayList<>();
	    try (DataInputStream dis = new DataInputStream(new FileInputStream(basePath + fileName))) {
	        int idCount = dis.readInt();
	        Bike.setIdCount(idCount);

	        int count = dis.readInt();
	        for (int i = 0; i < count; i++) {
	            String id = dis.readUTF();
	            int typeIndex = dis.readInt(); // index in types list
	            Types type = types.get(typeIndex);

	            String marka = dis.readUTF();
	            String model = dis.readUTF();
	            String rozmiarKola = dis.readUTF();
	            String opis = dis.readUTF();
	            int pricePerH = dis.readInt();
	            boolean isRented = dis.readBoolean();

	            bikes.add(new Bike(id, type, marka, model, rozmiarKola, opis, pricePerH, isRented));
	        }

	        dbLog.debug("Bikes loaded.");
	    } catch (IOException e) {
	        dbLog.error(e);
	    }
	    return bikes;
	}
	
	public static void saveTypes(List<Types> types, String fileName) {
	    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(basePath + fileName))) {
	        dos.writeInt(types.size());
	        for (Types type : types) {
	            dos.writeUTF(type.getNazwaTypu());
	            dos.writeUTF(type.getOpisTypuRoweru());
	        }
	        dbLog.debug("BikeType list saved.");
	    } catch (IOException e) {
	        dbLog.error(e);
	    }
	}

	public static List<Types> readTypes(String fileName) {
	    List<Types> types = new ArrayList<>();
	    try (DataInputStream dis = new DataInputStream(new FileInputStream(basePath + fileName))) {
	        int count = dis.readInt();
	        for (int i = 0; i < count; i++) {
	            String nazwa = dis.readUTF();
	            String opis = dis.readUTF();
	            types.add(new Types(nazwa, opis));
	        }
	        dbLog.debug("BikeType list loaded.");
	    } catch (IOException e) {
	        dbLog.error(e);
	    }
	    return types;
	}
	
	public static void saveClients(List<Client> clients, String fileName) {
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

	        dbLog.debug("Client data saved to " + basePath + fileName);
	    } catch (IOException e) {
	        dbLog.error(e);
	    }
	}
	
	public static List<Client> readClients(String fileName) {
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

	        dbLog.debug("Client data read from " + basePath + fileName);
	    } catch (IOException e) {
	        dbLog.error(e);
	    }

	    return clients;
	}
	
	public static void saveRentals(List<Rental> rentals, List<Client> clients, List<Bike> bikes, String fileName) {
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

	            if (rental.getEnd() != null) {
	                dos.writeBoolean(true);
	                dos.writeLong(rental.getEnd().toEpochSecond(ZoneOffset.UTC));
	            } else {
	                dos.writeBoolean(false);
	            }

	            dos.writeBoolean(rental.isReturned());
	        }

	        dbLog.debug("Rental data saved.");
	    } catch (IOException e) {
	        dbLog.error(e);
	    }
	}
	
	public static List<Rental> readRentals(String fileName, List<Client> clients, List<Bike> bikes) {
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

	            LocalDateTime end = null;
	            if (dis.readBoolean()) {
	                end = LocalDateTime.ofEpochSecond(dis.readLong(), 0, ZoneOffset.UTC);
	            }

	            boolean isReturned = dis.readBoolean();

	            rentals.add(new Rental(id, client, bike, start, end, isReturned));
	        }

	        dbLog.debug("Rental data loaded.");
	    } catch (IOException e) {
	        dbLog.error(e);
	    }
	    return rentals;
	}
}
