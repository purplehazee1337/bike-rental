package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.service.Service;
import java.util.List;

/**
 * A dialog window allowing the user to input and register a new client.
 * It collects personal information such as first name, last name,
 * phone number, and email address.
 */
public class AddClientForm extends JDialog {
	
	/**
     * Constructs a modal form for creating a new client in the system.
     * The form collects client data and adds it to the provided client list.
     *
     * @param parent  the parent JFrame of this dialog
     * @param clients the list of existing clients to which the new client will be added
     */
    public AddClientForm(JFrame parent, List<Client> clients) {
        super(parent, "Nowy klient", true);
        setSize(400, 260);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // UI components for input
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        // Row 1: First Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Imię:"), gbc);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        // Row 2: Last Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nazwisko:"), gbc);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        // Row 3: Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        // Row 4: Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Bottom buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        /**
         * Adds a new client based on user input after validating the form.
         * Shows error dialogs for invalid or empty input.
         */
        addButton.addActionListener(e -> {
        	try {
	            String firstName = firstNameField.getText().trim();
	            String lastName = lastNameField.getText().trim();
	            String phoneNumber = phoneField.getText().trim();
	            String email = emailField.getText().trim();
	            
	            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	            
	            Service.addClient(clients, firstName, lastName, phoneNumber, email);
	            
	            ((MainFrame) parent).refreshTables(); // Refresh table data
	            ((MainFrame) parent).switchCard("klienci");
	            dispose(); // Close the form
	            
        	} catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, "Wprowadzono niepoprawne dane.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        /**
         * Closes the form without saving any input.
         */
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
