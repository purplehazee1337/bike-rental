package pl.wit.bikerental.model;

/**
 * Represents a type of bike, including its name and description.
 * Used to categorize bikes in the rental system.
 * 
 * @author Wojciech Jechowski
 */
public class Types {
    /** Tracks the next available type ID. */
    private static int typeIdCount = 0;

    /** Unique identifier for the bike type. */
    private final String id;
    /** Name of the bike type (e.g., mountain, road). */
    private String name;
    /** Description of the bike type. */
    private String description;

    /** Constructor for new bike types (ID auto-generated). */
    public Types(String name, String opis) {
        this.id = "TYP" + (++typeIdCount);
        this.name = name;
        this.description = opis;
    }

    /** Constructor for loading bike types from file (with known ID). */
    public Types(String id, String nazwa, String opis) {
        this.id = id;
        this.name = nazwa;
        this.description = opis;
    }

    /** Returns the name of the type as its string representation. */
    public String toString() {
        return this.name;
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
