package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.service.Service;
import java.util.List;

public class AddRentalForm extends JDialog {
    private JComboBox<String> clientComboBox;
    private JComboBox<String> bikeComboBox;
    private JTextField fromDateField;
    private JTextField toDateField;

    public AddRentalForm(JFrame parent, List<Bike> bikes, List<Client> clients, List<Rental> rentals) {
        super(parent, "Nowe wypożyczenie", true);
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Klient (ID):"), gbc);

        gbc.gridx = 1;
        clientComboBox = new JComboBox<>();
        for (Client c : clients) {
            clientComboBox.addItem(c.getId());
        }
        panel.add(clientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Rower (ID):"), gbc);

        gbc.gridx = 1;
        bikeComboBox = new JComboBox<>();
        for (Bike b : bikes) {
            bikeComboBox.addItem(b.getId());
        }
        panel.add(bikeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Od kiedy:"), gbc);

        gbc.gridx = 1;
        fromDateField = new JTextField(10);
        fromDateField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        panel.add(fromDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Do kiedy:"), gbc);

        gbc.gridx = 1;
        toDateField = new JTextField(10);
        toDateField.setText(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        panel.add(toDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Dodaj wypożyczenie");
        panel.add(addButton, gbc);
        
        addButton.addActionListener(e -> {
    	try {
            String bikeId = (String) bikeComboBox.getSelectedItem();
            String clientId = (String) clientComboBox.getSelectedItem();
            String fromDateStr = fromDateField.getText().trim();
            String toDateStr = toDateField.getText().trim();
            
            if (fromDateStr.isEmpty() || toDateStr.isEmpty()) {
                throw new IllegalArgumentException();
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime fromDate = LocalDateTime.parse(fromDateStr, formatter);
            LocalDateTime toDate = LocalDateTime.parse(toDateStr, formatter);
            
            Service.newRental(rentals, bikes, clients, bikeId, clientId, fromDate);
            ((MainFrame) parent).refreshTables(); // refresh data
            dispose(); // zamknij formularz po dodaniu
            
    	} catch(Exception error) {
    		JOptionPane.showMessageDialog(parent, "Błąd.");
    	}
	});
        

        add(panel);
    }
}
