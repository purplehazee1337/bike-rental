package pl.wit.bikerental.model;

/**
 * The {@code Bike} class represents a rentable bicycle in the bike rental system.
 * It contains essential information such as the bike's name, hourly rental price,
 * and whether it is currently rented.
 *
 * <p>
 * This model is used to store and transport bike data across the application
 * and can be persisted using binary streams or other storage mechanisms.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Bike bike = new Bike("Merida", 20);
 * bike.setIsRented(true);
 * }</pre>
 *
 * @author Wojciech Jechowski
 * @version 1.0
 * @since 2025-06-15
 */
public class Bike {
    private String name;
    private Integer pricePerH;
    private Boolean isRented = false;

    /**
     * Constructs a {@code Bike} with a name and hourly price.
     * The rental status defaults to {@code false}.
     *
     * @param name      the name or model of the bike
     * @param pricePerH the hourly rental price
     */
    public Bike(String name, Integer pricePerH) {
        this.name = name;
        this.pricePerH = pricePerH;
    }

    /**
     * Constructs a {@code Bike} with a name, hourly price, and rental status.
     *
     * @param name      the name or model of the bike
     * @param pricePerH the hourly rental price
     * @param isRented  whether the bike is currently rented
     */
    public Bike(String name, Integer pricePerH, Boolean isRented) {
        this.name = name;
        this.pricePerH = pricePerH;
        this.isRented = isRented;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPricePerH() {
        return pricePerH;
    }

    public void setPricePerH(Integer pricePerH) {
        this.pricePerH = pricePerH;
    }

    public Boolean getIsRented() {
        return isRented;
    }

    public void setIsRented(Boolean isRented) {
        this.isRented = isRented;
    }
}
