package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.service.Service;
import java.util.List;

/**
 * Dialog form for creating a new bike rental.
 * Allows the user to choose a client and bike from existing lists,
 * and specify the rental period using LocalDateTime inputs.
 */
public class AddRentalForm extends JDialog {
	
	/** Combo box for selecting a client by ID. */
    private JComboBox<String> clientComboBox;
    
    /** Combo box for selecting a bike by ID. */
    private JComboBox<String> bikeComboBox;
    
    /** Text field for entering the rental start date and time. */
    private JTextField fromDateField;
    
    /** Text field for entering the rental end date and time. */
    private JTextField toDateField;

    /**
     * Constructs the rental form UI and initializes input fields.
     *
     * @param parent   the parent frame that owns this dialog
     * @param bikes    the list of available bikes
     * @param clients  the list of clients
     * @param rentals  the list of current rentals
     */
    public AddRentalForm(JFrame parent, List<Bike> bikes, List<Client> clients, List<Rental> rentals) {
        super(parent, "Nowe wypożyczenie", true);
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Client selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Klient (ID):"), gbc);

        gbc.gridx = 1;
        clientComboBox = new JComboBox<>();
        for (Client c : clients) {
            clientComboBox.addItem(c.getId());
        }
        panel.add(clientComboBox, gbc);

        // Bike selection
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Rower (ID):"), gbc);

        gbc.gridx = 1;
        bikeComboBox = new JComboBox<>();
        for (Bike b : bikes) {
            bikeComboBox.addItem(b.getId());
        }
        panel.add(bikeComboBox, gbc);

        // Start date input
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Od kiedy:"), gbc);

        gbc.gridx = 1;
        fromDateField = new JTextField(10);
        fromDateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        panel.add(fromDateField, gbc);

        // End date input
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Do kiedy:"), gbc);

        gbc.gridx = 1;
        toDateField = new JTextField(10);
        toDateField.setText(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        panel.add(toDateField, gbc);

        // Submit button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Dodaj wypożyczenie");
        panel.add(addButton, gbc);
        
        /**
         * Button listener that validates form input,
         * parses the dates, and submits a new rental to the service.
         */
        addButton.addActionListener(e -> {
    	try {
            String bikeId = (String) bikeComboBox.getSelectedItem();
            String clientId = (String) clientComboBox.getSelectedItem();
            String fromDateStr = fromDateField.getText().trim();
            String toDateStr = toDateField.getText().trim();
            
            if (fromDateStr.isEmpty() || toDateStr.isEmpty()) {
                throw new IllegalArgumentException();
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime fromDate = LocalDateTime.parse(fromDateStr, formatter);
            LocalDateTime toDate = LocalDateTime.parse(toDateStr, formatter);
            
            Service.newRental(rentals, bikes, clients, bikeId, clientId, fromDate, toDate);
            ((MainFrame) parent).refreshTables(); // Refresh table data
            ((MainFrame) parent).switchCard("wypozyczenia");
            dispose(); // Close the form
            
    	} catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(parent, "Wprowadzono niepoprawne dane.", "Missing Data", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
	});
        

        add(panel);
    }
}
