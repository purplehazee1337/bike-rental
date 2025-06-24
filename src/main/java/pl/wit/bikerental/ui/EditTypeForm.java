package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;

import java.util.List;

/**
 * Dialog window that allows editing of an existing bike type by specifying its ID.
 * Provides input fields for the type's name and description.
 * 
 * @author Dominik Pękala
 */
public class EditTypeForm extends JDialog {
	
	/** Constructs a dialog for editing the attributes of a bike type. */
    public EditTypeForm(JFrame parent, List<Types> types) {
        super(parent, "Edytuj typ roweru", true);
        setSize(380, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        /** UI components for input */
        JComboBox<String> idCombo = new JComboBox<>();
        for (Types t : types) {
            idCombo.addItem(t.getId());
        }
        JTextField nameField = new JTextField(20);
        JTextArea descArea = new JTextArea(3, 20);

        /** Form layout */
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID typu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nazwa typu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Opis typu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(descArea), gbc);

        /** Bottom buttons */
        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Zmień");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        /** Actions performed by buttons (editing existing bike type) */
        updateButton.addActionListener(e -> {
        	try {
        		
        		String id = (String) idCombo.getSelectedItem();
	            String name = nameField.getText().trim();
	            String description = descArea.getText().trim();
	
	            if (name.isEmpty() || description.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	            
	            Service.editTypeById(types, id, name, description);
	            
	            ((MainFrame) parent).refreshTables(); // Refresh table data
	            ((MainFrame) parent).switchCard("typy");
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
