package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;

import java.util.List;

/**
 * Dialog window that allows the user to delete a bike type based on its ID.
 * Displays a dropdown of all available type IDs and provides delete and cancel buttons.
 * 
 * @author Dominik Pękala
 */
public class DeleteTypeForm extends JDialog {
	
	/** Constructs a modal dialog for removing a bike type from the system. */
    public DeleteTypeForm(JFrame parent, List<Types> types, List<Bike> bikes) {
        super(parent, "Usuń typ roweru", true);
        setSize(200, 160);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        /** Form layout */
        JComboBox<String> idCombo = new JComboBox<>();
        for (Types t : types) {
            idCombo.addItem(t.getId());
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Wybierz ID typu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        /** Bottom buttons */
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Usuń");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        /** Actions performed by buttons (deleting a bike type) */
        deleteButton.addActionListener(e -> {
        	try {
        		
        		String id = (String) idCombo.getSelectedItem();
	            
	            Service.removeTypeById(types, id, bikes);
	            
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
