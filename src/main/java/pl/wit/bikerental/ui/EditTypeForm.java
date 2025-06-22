package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;

import java.util.List;

public class EditTypeForm extends JDialog {
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

        JComboBox<String> idCombo = new JComboBox<>();
        for (Types t : types) {
            idCombo.addItem(t.getId());
        }
        JTextField nameField = new JTextField(20);
        JTextArea descArea = new JTextArea(3, 20);

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

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Zmień");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        updateButton.addActionListener(e -> {
        	try {
        		
        		String id = (String) idCombo.getSelectedItem();
	            String name = nameField.getText().trim();
	            String description = descArea.getText().trim();
	
	            if (name.isEmpty() || description.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	            
	            Service.editTypeById(types, id, name, description);
	            
	            ((MainFrame) parent).refreshTables(); // refresh data
	            dispose(); // zamknij formularz po edycji
	            
        	} catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, "Wprowadzono niepoprawne dane.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
