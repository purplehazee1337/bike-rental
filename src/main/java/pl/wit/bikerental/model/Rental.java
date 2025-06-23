package pl.wit.bikerental.model;

import java.time.LocalDateTime;

/**
 * Represents a bike rental record, including client, bike, rental time, and return status.
 * Tracks planned and actual return times to calculate rental details.
 * 
 * @author Wojciech Jechowski
 */
public class Rental {
    /** Tracks the next available rental ID. */
    private static int rentalIdCount = 0;

    /** Unique identifier for the rental. */
    private final String id;
    /** The client who made the rental. */
    private Client client;
    /** The bike being rented. */
    private Bike bike;
    /** Date and time the rental started. */
    private LocalDateTime start;
    /** Planned return date and time. */
    private LocalDateTime plannedEnd;
    /** Actual return date and time (can be null if not yet returned). */
    private LocalDateTime actualReturnDate;
    /** Indicates whether the bike has been returned. */
    private boolean isReturned;

    /** Constructor for new rentals (ID auto-generated). */
    public Rental(Client client, Bike bike, LocalDateTime start, LocalDateTime plannedEnd) {
        this.id = "R" + (++rentalIdCount);
        this.client = client;
        this.bike = bike;
        this.start = start;
        this.plannedEnd = plannedEnd;
        this.actualReturnDate = null;
        this.isReturned = false;
    }

    /** Constructor for loading rentals from file (with known ID). */
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

    /** Sets the rental ID counter (used when loading from file). */
    public static void setRentalIdCount(int count) {
        rentalIdCount = count;
    }

    /** Returns the current rental ID counter. */
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
