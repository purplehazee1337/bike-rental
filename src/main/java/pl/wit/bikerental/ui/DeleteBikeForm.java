package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.service.Service;
import java.util.List;

/**
 * Dialog window allowing the user to delete a bike from the system.
 * The user selects a bike ID from a dropdown, and upon confirmation,
 * the bike is removed if it is not currently rented.
 */
public class DeleteBikeForm extends JDialog {
	
	/**
     * Constructs a modal dialog for deleting a bike from the system.
     * The bike is selected from a combo box populated with available IDs.
     * After confirmation, the Service class handles the removal.
     *
     * @param parent  the main application window to which this dialog is modal
     * @param bikes   the list of existing bikes
     * @param rentals the list of active or past rentals (used to validate if a bike can be deleted)
     */
    public DeleteBikeForm(JFrame parent, List<Bike> bikes, List<Rental> rentals) {
        super(parent, "Usuń rower", true);
        setSize(200, 160);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Dropdown with bike IDs
        JComboBox<String> idCombo = new JComboBox<>();
        for (Bike b : bikes) {
            idCombo.addItem(b.getId());
        }

        // Form layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Wybierz ID roweru:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Usuń");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        // Delete logic
        deleteButton.addActionListener(e -> {
        	try {
        		
        		String id = (String) idCombo.getSelectedItem();
	            
	            Service.removeBikeById(bikes, id, rentals);
	            
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
