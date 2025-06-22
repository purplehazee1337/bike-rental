package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.service.Service;
import java.util.List;

/**
 * Dialog window that allows the user to delete a client from the system.
 * The user selects a client ID from a dropdown list. If the client has no active rentals,
 * they can be removed from the system.
 */
public class DeleteClientForm extends JDialog {
	
	/**
     * Constructs a modal dialog for deleting a client from the system.
     * It provides a combo box with client IDs and validates removal through the service layer.
     *
     * @param parent  the parent frame of the dialog (usually the main application window)
     * @param clients the current list of all clients in the system
     * @param rentals the list of rentals used to check whether a client can be safely deleted
     */
    public DeleteClientForm(JFrame parent, List<Client> clients, List<Rental> rentals) {
        super(parent, "Usuń klienta", true);
        setSize(200, 160);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Dropdown to choose client ID
        JComboBox<String> idCombo = new JComboBox<>();
        for (Client c : clients) {
            idCombo.addItem(c.getId());
        }
        
        // Form layout
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Wybierz ID klienta:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        // Action buttons
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Usuń");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        deleteButton.addActionListener(e -> {
        	try {
        		
        		String id = (String) idCombo.getSelectedItem();
	            
	            Service.removeClientById(clients, rentals, id);
	            
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
