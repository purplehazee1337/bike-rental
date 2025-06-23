package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.service.Service;
import java.util.List;

/**
 * Dialog window allowing the user to mark a rental as completed (returned).
 * The user selects a rental by ID from a dropdown list and confirms the return.
 */
public class ReturnRentalForm extends JDialog {
	
	/** Dropdown list for selecting the rental ID to be marked as returned. */
    private JComboBox<String> rentalIdComboBox;

    /**
     * Constructs a modal dialog for returning a rental.
     * Allows the user to select a rental by ID and mark it as completed.
     *
     * @param parent   The parent frame to which this dialog is modal.
     * @param rentals  The list of existing rentals in the system.
     */
    public ReturnRentalForm(JFrame parent, List<Rental> rentals) {
        super(parent, "Zwróć wypożyczenie", true);
        setSize(300, 160);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Form layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID wypożyczenia:"), gbc);

        gbc.gridx = 1;
        rentalIdComboBox = new JComboBox<>();
        for (Rental r : rentals) {
            rentalIdComboBox.addItem(r.getId());
        }
        panel.add(rentalIdComboBox, gbc);

        // Return button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton returnButton = new JButton("Zwróć");
        panel.add(returnButton, gbc);

        // Return logic
        returnButton.addActionListener(e -> {
        	try {
                String id = (String) rentalIdComboBox.getSelectedItem();
                
                Service.completeRental(rentals, id);
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