package pl.wit.bikerental.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.wit.bikerental.model.Bike;

/**
 * The {@code Db} class provides utility methods for persisting and retrieving
 * {@link Bike} objects to and from a binary file.
 * <p>
 * Bike data is stored using Java's {@code DataOutputStream} and read using
 * {@code DataInputStream}, allowing for simple binary serialization.
 * Each bike is saved with its name, hourly price, and rental status.
 * </p>
 * 
 * <p>This class will contain other data retrieving and saving methods.</p>
 * 
 * <p>Usage example:</p>
 * <pre>{@code
 * List<Bike> bikes = Arrays.asList(
 *     new Bike("Trek", 25, false),
 *     new Bike("Cube", 30, true)
 * );
 * Db.saveBikes(bikes, "bikes.dat");
 * List<Bike> loaded = Db.readBikes("bikes.dat");
 * }</pre>
 *
 * <p>
 * Logging is handled via Log4j, and all data is stored relative to the
 * {@code ./src/main/resources/} path.
 * </p>
 *
 * @author Wojciech Jechowski
 * @version 1.0
 * @since 2025-06-15
 */

public class Db {
	private static final Logger dbLog = LogManager.getLogger(Db.class);
	private static String basePath = "./src/main/resources/";
	
	public static void saveBikes(List<Bike> bikes, String fileName) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(basePath + fileName))) {
            dos.writeInt(bikes.size());
            for (Bike bike : bikes) {
                dos.writeUTF(bike.getName());
                dos.writeInt(bike.getPricePerH());
                dos.writeBoolean(bike.getIsRented());
            }
            dbLog.debug("Bike data saved to " + basePath + fileName);
        } catch(IOException e) {
        	dbLog.error(e);
        }
    }

	public static List<Bike> readBikes(String fileName) throws IOException {
        List<Bike> bikes = new ArrayList<>();
        
        try (DataInputStream dis = new DataInputStream(new FileInputStream(basePath + fileName))) {
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                String name = dis.readUTF();
                Integer pricePerH = dis.readInt();
                boolean isRented = dis.readBoolean();
                bikes.add(new Bike(name, pricePerH, isRented));
            }
            dbLog.debug("Bike data read from " + basePath + fileName);
        } catch(IOException e) {
        	dbLog.error(e);
        }
        return bikes;
    }
}
