package pl.wit.bikerental.model;

/**
 * Represents a client using the bike rental service.
 * Stores personal information such as name, phone number, and email.
 * 
 * @author Wojciech Jechowski
 */
public class Client {
    /** Tracks the next available client ID. */
    private static int clientIdCount = 0;

    /** Unique identifier for the client. */
    private final String id;
    /** First name of the client. */
    private String firstName;
    /** Last name of the client. */
    private String lastName;
    /** Phone number of the client. */
    private String phoneNumber;
    /** Email address of the client. */
    private String email;

    /** Constructor for new clients (ID auto-generated). */
    public Client(String firstName, String lastName, String phoneNumber, String email) {
        this.id = "C" + (++clientIdCount);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /** Constructor for loading clients from file (with known ID). */
    public Client(String id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /** Sets the client ID counter (used when loading from file). */
    public static void setClientIdCount(int count) {
        clientIdCount = count;
    }

    /** Returns the current client ID counter. */
    public static int getClientIdCount() {
        return clientIdCount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }
}
