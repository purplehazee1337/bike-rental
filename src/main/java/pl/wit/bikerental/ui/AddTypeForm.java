package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;

import java.util.List;

public class AddTypeForm extends JDialog {
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

        JTextField nameField = new JTextField(20);
        JTextArea descArea = new JTextArea(3, 20);

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

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        addButton.addActionListener(e -> {
        	try {
	            String name = nameField.getText().trim();
	            String description = descArea.getText().trim();
	
	            if (name.isEmpty() || description.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	            
	            Service.addType(types, name, description);
	            
	            ((MainFrame) parent).refreshTables(); // refresh data
	            dispose(); // zamknij formularz po dodaniu
	            
        	} catch(Exception error) {
        		JOptionPane.showMessageDialog(parent, "Niepoprawne dane.");
        	}
        });
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
