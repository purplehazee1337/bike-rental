package pl.wit.bikerental.model;

public class Bike {
    private static int idCount = 0; // tracks next ID
    private final String id;
    private Types type;
    private String brand;
    private String model;
    private String wheelSize;
    private String description;
    private int pricePerH;
    private boolean isRented;

    // Constructor for new bikes (ID auto-generated)
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

    // Constructor for loading from file (ID must be passed)
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