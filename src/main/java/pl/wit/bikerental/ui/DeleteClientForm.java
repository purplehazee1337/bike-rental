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
 * 
 * @author Dominik Pękala
 */
public class DeleteClientForm extends JDialog {
	
	/** Constructs a dialog for deleting a client from the system. */
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

        /** client ID Combo Box */
        JComboBox<String> idCombo = new JComboBox<>();
        for (Client c : clients) {
            idCombo.addItem(c.getId());
        }
        
        /** Form layout */
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Wybierz ID klienta:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        /** Bottom buttons */
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Usuń");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        /** Actions performed by buttons (deleting new client) */
        deleteButton.addActionListener(e -> {
        	try {
        		
        		String id = (String) idCombo.getSelectedItem();
	            
	            Service.removeClientById(clients, rentals, id);
	            
	            ((MainFrame) parent).refreshTables(); // Refresh table data
	            ((MainFrame) parent).switchCard("klienci");
	            dispose(); // Close the form
	            
        	} catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, "Wprowadzono niepoprawne dane.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        /** Closing the form without saving. */
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
