package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;

import java.util.List;

/**
 * Dialog form for creating and adding a new bike type to the system.
 * The user can enter a type name and a description,
 * which will be validated and added to the provided types list.
 */
public class AddTypeForm extends JDialog {
	
	/**
     * Constructs the dialog window used for entering a new bike type.
     * Initializes the form layout, input fields and button actions.
     *
     * @param parent the parent JFrame that owns this dialog
     * @param types  the list of all existing bike types to which the new type will be added
     */
    public AddTypeForm(JFrame parent, List<Types> types) {
        super(parent, "Nowy typ roweru", true);
        setSize(350, 240);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // UI components for input
        JTextField nameField = new JTextField(20);
        JTextArea descArea = new JTextArea(3, 20);

        // Form layout for labels and fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nazwa typu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Opis typu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(descArea), gbc);

        // Bottom buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        /**
         * Action listener that processes form submission.
         * It checks if fields are filled, then adds a new type using the service layer.
         * Refreshes the main table view and closes the dialog.
         */
        addButton.addActionListener(e -> {
        	try {
	            String name = nameField.getText().trim();
	            String description = descArea.getText().trim();
	
	            if (name.isEmpty() || description.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	            
	            Service.addType(types, name, description);
	            
	            ((MainFrame) parent).refreshTables(); // Refresh table data
	            ((MainFrame) parent).switchCard("typy");
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
