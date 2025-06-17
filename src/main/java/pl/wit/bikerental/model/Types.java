package pl.wit.bikerental.model;

public class Types {
    private static int typeIdCount = 0;

    private final String id;
    private String nazwaTypu;
    private String opisTypuRoweru;

    // Constructor for new type (auto-generate ID)
    public Types(String nazwa, String opis) {
        this.id = "TYP" + (++typeIdCount);
        this.nazwaTypu = nazwa;
        this.opisTypuRoweru = opis;
    }

    // Constructor for loading from file (known ID)
    public Types(String id, String nazwa, String opis) {
        this.id = id;
        this.nazwaTypu = nazwa;
        this.opisTypuRoweru = opis;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNazwaTypu() {
        return nazwaTypu;
    }

    public String getOpisTypuRoweru() {
        return opisTypuRoweru;
    }

    // Static counter methods
    public static int getTypeIdCount() {
        return typeIdCount;
    }

    public static void setTypeIdCount(int count) {
        typeIdCount = count;
    }

    @Override
    public String toString() {
        return id + ": " + nazwaTypu + " - " + opisTypuRoweru;
    }
}
