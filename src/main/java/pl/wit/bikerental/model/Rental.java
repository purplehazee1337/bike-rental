package pl.wit.bikerental.model;

import java.time.LocalDateTime;

public class Rental {
    private static int rentalIdCount = 0;

    private final String id;
    private Client client;
    private Bike bike;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean isReturned;

    public Rental(Client client, Bike bike, LocalDateTime start) {
        this.id = "R" + (++rentalIdCount);
        this.client = client;
        this.bike = bike;
        this.start = start;
        this.end = null;
        this.isReturned = false;
    }

    public Rental(String id, Client client, Bike bike, LocalDateTime start, LocalDateTime end, boolean isReturned) {
        this.id = id;
        this.client = client;
        this.bike = bike;
        this.start = start;
        this.end = end;
        this.isReturned = isReturned;
    }

    public static void setRentalIdCount(int count) {
        rentalIdCount = count;
    }

    public static int getRentalIdCount() {
        return rentalIdCount;
    }

    public String getId() { return id; }
    public Client getClient() { return client; }
    public Bike getBike() { return bike; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public boolean isReturned() { return isReturned; }
}
