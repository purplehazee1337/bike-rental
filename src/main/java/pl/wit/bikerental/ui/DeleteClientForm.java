package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.service.Service;
import java.util.List;

public class DeleteClientForm extends JDialog {
    public DeleteClientForm(JFrame parent, List<Client> clients, List<Rental> rentals) {
        super(parent, "Usuń klienta", true);
        setSize(200, 160);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JComboBox<String> idCombo = new JComboBox<>();
        for (Client c : clients) {
            idCombo.addItem(c.getId());
        }
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Wybierz ID klienta:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Usuń");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        deleteButton.addActionListener(e -> {
        	try {
        		
        		String id = (String) idCombo.getSelectedItem();
	            
	            Service.removeClientById(clients, rentals, id);
	            
	            ((MainFrame) parent).refreshTables(); // refresh data
	            dispose(); // zamknij formularz po usunięciu
	            
        	} catch(Exception error) {
        		JOptionPane.showMessageDialog(parent, "Błąd.");
        	}
        });
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
