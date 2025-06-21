package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddRentalForm extends JDialog {
    private JComboBox<Integer> clientComboBox;
    private JComboBox<Integer> bikeComboBox;
    private JTextField fromDateField;
    private JTextField toDateField;

    public AddRentalForm(JFrame parent) {
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
        panel.add(clientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Rower (ID):"), gbc);

        gbc.gridx = 1;
        bikeComboBox = new JComboBox<>();
        panel.add(bikeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Od kiedy:"), gbc);

        gbc.gridx = 1;
        fromDateField = new JTextField(10);
        fromDateField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        panel.add(fromDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Do kiedy:"), gbc);

        gbc.gridx = 1;
        toDateField = new JTextField(10);
        toDateField.setText(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE));
        panel.add(toDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Dodaj wypożyczenie");
        panel.add(addButton, gbc);
        
        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Wypożyczenie zostało dodane");
        });

        add(panel);
    }
}
