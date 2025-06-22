package pl.wit.bikerental.ui;

import javax.swing.*;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.service.Service;
import java.util.List;

import java.awt.*;

public class AddBikeForm extends JDialog {
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

        JComboBox<Types> typeCombo = new JComboBox<>();
        for (Types t : types) {
            typeCombo.addItem(t);
        }
        JTextField brandField = new JTextField(20);
        JTextField modelField = new JTextField(20);
        JTextField wheelSizeField = new JTextField(20);
        JTextField pricePerH = new JTextField(20);
        JTextArea bikeDescArea = new JTextArea(3, 20);

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

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        addButton.addActionListener(e -> {
        	try {
	            Types type = (Types) typeCombo.getSelectedItem();
	            String brand = brandField.getText().trim();
	            String model = modelField.getText().trim();
	            String wheelSize = wheelSizeField.getText().trim();
	            int price = Integer.parseInt(pricePerH.getText().trim());
	            String description = bikeDescArea.getText().trim();
	            
	            if (brand.isEmpty() || model.isEmpty() || wheelSize.isEmpty()) {
	                throw new IllegalArgumentException();
	            }
	
	            Service.addBike(bikes, type, brand, model, wheelSize, description, price);
	            
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
