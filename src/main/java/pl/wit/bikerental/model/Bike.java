package pl.wit.bikerental.model;

/**
 * Represents a bike available for rental.
 * Contains details such as type, brand, model, wheel size, description,
 * price per hour, and rental status.
 * 
 * @author Wojciech Jechowski
 */
public class Bike {
	/** Tracks the next available ID for newly created bikes. */
    private static int idCount = 0;
    /** Unique identifier for the bike. */
    private final String id;
    /** Type of the bike (e.g., mountain, road). */
    private Types type;
    /** Brand of the bike. */
    private String brand;
    /** Model name of the bike. */
    private String model;
    /** Size of the wheels (e.g., 26", 28"). */
    private String wheelSize;
    /** Additional description or notes about the bike. */
    private String description;
    /** Rental price per hour. */
    private int pricePerH;
    /** Indicates whether the bike is currently rented. */
    private boolean isRented;

    /** Constructor for new bikes (ID auto-generated) */
    public Bike(Types type, String brand, String model, String wheelSize, String description, int pricePerH) {
        this.id = "B" + (++idCount);
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.wheelSize = wheelSize;
        this.description = description;
        this.pricePerH = pricePerH;
        this.isRented = false;
    }

    /** Constructor for loading from file (ID must be passed) */
    public Bike(String id, Types type, String marka, String model, String rozmiarKola, String opis, int pricePerH, boolean isRented) {
        this.id = id;
        this.type = type;
        this.brand = marka;
        this.model = model;
        this.wheelSize = rozmiarKola;
        this.description = opis;
        this.pricePerH = pricePerH;
        this.isRented = isRented;
    }

    public static void setIdCount(int count) {
        idCount = count;
    }

    public static int getIdCount() {
        return idCount;
    }

    public String getId() {
        return id;
    }

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String marka) {
		this.brand = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getWheelSize() {
		return wheelSize;
	}

	public void setWheelSize(String rozmiarKola) {
		this.wheelSize = rozmiarKola;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String opis) {
		this.description = opis;
	}

	public int getPricePerH() {
		return pricePerH;
	}

	public void setPricePerH(int pricePerH) {
		this.pricePerH = pricePerH;
	}

	public boolean isRented() {
		return isRented;
	}

	public void setRented(boolean isRented) {
		this.isRented = isRented;
	}

    
}