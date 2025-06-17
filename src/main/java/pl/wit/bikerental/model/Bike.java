package pl.wit.bikerental.model;

public class Bike {
    private static int idCount = 0; // tracks next ID
    private final String id;
    private Types type;
    private String marka;
    private String model;
    private String rozmiarKola;
    private String opis;
    private int pricePerH;
    private boolean isRented;

    // Constructor for new bikes (ID auto-generated)
    public Bike(Types type, String marka, String model, String rozmiarKola, String opis, int pricePerH) {
        this.id = "B" + (++idCount);
        this.type = type;
        this.marka = marka;
        this.model = model;
        this.rozmiarKola = rozmiarKola;
        this.opis = opis;
        this.pricePerH = pricePerH;
        this.isRented = false;
    }

    // Constructor for loading from file (ID must be passed)
    public Bike(String id, Types type, String marka, String model, String rozmiarKola, String opis, int pricePerH, boolean isRented) {
        this.id = id;
        this.type = type;
        this.marka = marka;
        this.model = model;
        this.rozmiarKola = rozmiarKola;
        this.opis = opis;
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

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRozmiarKola() {
		return rozmiarKola;
	}

	public void setRozmiarKola(String rozmiarKola) {
		this.rozmiarKola = rozmiarKola;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
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