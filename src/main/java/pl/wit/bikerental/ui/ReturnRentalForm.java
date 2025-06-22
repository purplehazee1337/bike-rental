package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.service.Service;
import java.util.List;

public class ReturnRentalForm extends JDialog {
    private JComboBox<String> rentalIdComboBox;

    public ReturnRentalForm(JFrame parent, List<Rental> rentals) {
        super(parent, "Zwróć wypożyczenie", true);
        setSize(300, 160);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID wypożyczenia:"), gbc);

        gbc.gridx = 1;
        rentalIdComboBox = new JComboBox<>();
        for (Rental r : rentals) {
            rentalIdComboBox.addItem(r.getId());
        }
        panel.add(rentalIdComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton returnButton = new JButton("Zwróć");
        panel.add(returnButton, gbc);

        returnButton.addActionListener(e -> {
        	try {
                String id = (String) rentalIdComboBox.getSelectedItem();
                
                Service.completeRental(rentals, id);
                ((MainFrame) parent).refreshTables(); // refresh data
                dispose(); // zamknij formularz po dodaniu
                
        	} catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(parent, "Wprowadzono niepoprawne dane.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    	});

        add(panel);
    }
}