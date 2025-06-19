package pl.wit.bikerental.model;

public class Client {
    private static int clientIdCount = 0;

    private final String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    // Constructor for new clients (ID auto-generated)
    public Client(String firstName, String lastName, String phoneNumber, String email) {
        this.id = "C" + (++clientIdCount);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Constructor for loading from file (with known ID)
    public Client(String id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public static void setClientIdCount(int count) {
        clientIdCount = count;
    }

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
