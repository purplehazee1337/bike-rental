package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;
import java.util.List;

/**
 * Dialog window that allows the user to edit an existing bike's data based on its ID.
 * The form enables modification of bike attributes such as type, brand, model,
 * wheel size, price per hour, and description.
 */
public class EditBikeForm extends JDialog {
	
	/**
     * Constructs a modal dialog for editing a bike's properties.
     * Provides dropdown to select the bike by ID and fields to update its information.
     * Uses the service layer to persist changes.
     *
     * @param parent The parent JFrame that owns this dialog.
     * @param bikes  The list of existing bikes to be updated.
     * @param types  The list of available bike types for selection.
     */
    public EditBikeForm(JFrame parent, List<Bike> bikes, List<Types> types) {
        super(parent, "Edytuj rower", true);
        setSize(380, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ComboBoxes and input fields
        JComboBox<String> idCombo = new JComboBox<>();
        for (Bike b : bikes) {
            idCombo.addItem(b.getId());
        }
        JComboBox<Types> typeCombo = new JComboBox<>();
        for (Types t : types) {
            typeCombo.addItem(t);
        }
        JTextField brandField = new JTextField(20);
        JTextField modelField = new JTextField(20);
        JTextField wheelSizeField = new JTextField(20);
        JTextField pricePerH = new JTextField(20);
        JTextArea bikeDescArea = new JTextArea(3, 20);

        // Form layout
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID roweru:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Typ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(typeCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Marka:"), gbc);
        gbc.gridx = 1;
        formPanel.add(brandField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1;
        formPanel.add(modelField, gbc); 

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Rozmiar koła:"), gbc);
        gbc.gridx = 1;
        formPanel.add(wheelSizeField, gbc);
        
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Cena za godzine:"), gbc);
        gbc.gridx = 1;
        formPanel.add(pricePerH, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Opis roweru:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(bikeDescArea), gbc);

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
	            Types type = (Types) typeCombo.getSelectedItem();
	            String brand = brandField.getText().trim();
	            String model = modelField.getText().trim();
	            String wheelSize = wheelSizeField.getText().trim();
	            int price = Integer.parseInt(pricePerH.getText().trim());
	            String description = bikeDescArea.getText().trim();
	            
	            if (brand.isEmpty() || model.isEmpty() || wheelSize.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	
	            Service.editBikeById(bikes, id, brand, model, wheelSize, description, price);
	            Service.findBikeById(bikes, id).setType(type);
	            
	            ((MainFrame) parent).refreshTables(); // Refresh table data
	            ((MainFrame) parent).switchCard("rowery");
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
