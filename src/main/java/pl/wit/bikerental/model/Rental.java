package pl.wit.bikerental.model;

import java.time.LocalDateTime;

public class Rental {
    private static int rentalIdCount = 0;

    private final String id;
    private Client client;
    private Bike bike;
    private LocalDateTime start;
    private LocalDateTime plannedEnd;
    private LocalDateTime actualReturnDate;
    private boolean isReturned;

    public Rental(Client client, Bike bike, LocalDateTime start, LocalDateTime plannedEnd) {
        this.id = "R" + (++rentalIdCount);
        this.client = client;
        this.bike = bike;
        this.start = start;
        this.plannedEnd = plannedEnd;
        this.actualReturnDate = null;
        this.isReturned = false;
    }

    public Rental(String id, Client client, Bike bike, LocalDateTime start, LocalDateTime plannedEnd,
                  LocalDateTime actualReturnDate, boolean isReturned) {
        this.id = id;
        this.client = client;
        this.bike = bike;
        this.start = start;
        this.plannedEnd = plannedEnd;
        this.actualReturnDate = actualReturnDate;
        this.isReturned = isReturned;
    }

    public static void setRentalIdCount(int count) {
        rentalIdCount = count;
    }

    public static int getRentalIdCount() {
        return rentalIdCount;
    }

    public String getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(LocalDateTime plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }
}
