package pl.wit.bikerental.model;

public class Types {
    private static int typeIdCount = 0;

    private final String id;
    private String name;
    private String description;

    // Constructor for new type (auto-generate ID)
    public Types(String name, String opis) {
        this.id = "TYP" + (++typeIdCount);
        this.name = name;
        this.description = opis;
    }

    // Constructor for loading from file (known ID)
    public Types(String id, String nazwa, String opis) {
        this.id = id;
        this.name = nazwa;
        this.description = opis;
    }
    
    public String toString() {
        return this.name; // Or whatever field represents the name
    }

	public static int getTypeIdCount() {
		return typeIdCount;
	}

	public static void setTypeIdCount(int typeIdCount) {
		Types.typeIdCount = typeIdCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String nazwaTypu) {
		this.name = nazwaTypu;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String opisTypuRoweru) {
		this.description = opisTypuRoweru;
	}

	public String getId() {
		return id;
	}

}
