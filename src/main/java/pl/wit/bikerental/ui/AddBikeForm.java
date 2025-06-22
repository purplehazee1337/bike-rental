package pl.wit.bikerental.ui;

import javax.swing.*;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;
import java.util.List;

import java.awt.*;

/**
 * A dialog window that allows the user to input and create a new Bike entry.
 * It includes fields for selecting a type, entering brand, model, wheel size,
 * rental price per hour, and a short description.
 */
public class AddBikeForm extends JDialog {
	
	/**
     * Constructs a modal form for adding a new Bike instance to the system.
     *
     * @param parent the parent JFrame (main application window)
     * @param bikes  the list of bikes to which the new bike will be added
     * @param types  the list of bike types available for selection
     */
    public AddBikeForm(JFrame parent, List<Bike> bikes, List<Types> types) {
        super(parent, "Nowy rower", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // UI components for input
        JComboBox<Types> typeCombo = new JComboBox<>();
        JTextField brandField = new JTextField(20);
        JTextField modelField = new JTextField(20);
        JTextField wheelSizeField = new JTextField(20);
        JTextField pricePerH = new JTextField(20);
        JTextArea bikeDescArea = new JTextArea(3, 20);
        
        // Fill combo box with available types
        for (Types t : types) {
            typeCombo.addItem(t);
        }
        
        // Row 1: Type
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Typ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(typeCombo, gbc);

        // Row 2: Brand
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Marka:"), gbc);
        gbc.gridx = 1;
        formPanel.add(brandField, gbc);

        // Row 3: Model
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1;
        formPanel.add(modelField, gbc);

        // Row 4: Wheel size
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Rozmiar koła:"), gbc);
        gbc.gridx = 1;
        formPanel.add(wheelSizeField, gbc);
        
        // Row 5: Price per hour
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Cena za godzine:"), gbc);
        gbc.gridx = 1;
        formPanel.add(pricePerH, gbc);

        // Row 6: Description
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Opis roweru:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(bikeDescArea), gbc);

        // Bottom buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        /**
         * Action performed when the "Dodaj" button is clicked.
         * Validates user input and attempts to create and save a new Bike object.
         */
        addButton.addActionListener(e -> {
        	try {
	            Types type = (Types) typeCombo.getSelectedItem();
	            String brand = brandField.getText().trim();
	            String model = modelField.getText().trim();
	            String wheelSize = wheelSizeField.getText().trim();
	            int price = Integer.parseInt(pricePerH.getText().trim());
	            String description = bikeDescArea.getText().trim();
	            
	            if (brand.isEmpty() || model.isEmpty() || wheelSize.isEmpty()) {
	                throw new IllegalArgumentException("Wprowadzono niepoprawne dane.");
	            }
	
	            Service.addBike(bikes, type, brand, model, wheelSize, description, price);
	            
	            ((MainFrame) parent).refreshTables(); // Refresh table after update
	            dispose(); // Close the form
	            
        	} catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, "Wprowadzono niepoprawne dane.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Action performed when the "Anuluj" button is clicked.
         * Closes the form without saving.
         */
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
