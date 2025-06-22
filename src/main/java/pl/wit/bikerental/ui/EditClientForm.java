package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.service.Service;
import java.util.List;

/**
 * Dialog window that allows editing of an existing client's data based on their ID.
 * The form provides input fields for the client's personal information such as
 * first name, last name, phone number, and email.
 */
public class EditClientForm extends JDialog {
	
	/**
     * Constructs a modal dialog used to update client information.
     * Allows selecting a client by ID and editing their attributes.
     * Changes are persisted using the service layer.
     *
     * @param parent  The parent JFrame that owns this dialog.
     * @param clients The list of existing clients to be updated.
     */
    public EditClientForm(JFrame parent, List<Client> clients) {
        super(parent, "Edytuj klienta", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // ComboBoxes and input fields
        JComboBox<String> idCombo = new JComboBox<>();
        for (Client c : clients) {
            idCombo.addItem(c.getId());
        }
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        // Form layout
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID klienta:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Imię:"), gbc);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Nazwisko:"), gbc);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Zmień");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        // Update logic
        updateButton.addActionListener(e -> {
        	try {
        		String id = (String) idCombo.getSelectedItem();
	            String firstName = firstNameField.getText().trim();
	            String lastName = lastNameField.getText().trim();
	            String phoneNumber = phoneField.getText().trim();
	            String email = emailField.getText().trim();
	            
	            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	            
	            Service.editClientById(clients, id, firstName, lastName, phoneNumber, email);
	            
	            ((MainFrame) parent).refreshTables(); // Refresh table data
	            dispose(); // Close the form
	            
        	} catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, "Wprowadzono niepoprawne dane.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Close form without changes
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
