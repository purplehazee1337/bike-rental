package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

public class AddBikeForm extends JDialog {
    public AddBikeForm(JFrame parent) {
        super(parent, "Nowy rower", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField nameField = new JTextField(20);
        JComboBox<String> typeCombo = new JComboBox<>();
        JTextField brandField = new JTextField(20);
        JTextField modelField = new JTextField(20);
        JTextField wheelSizeField = new JTextField(20);
        JTextArea bikeDescArea = new JTextArea(3, 20);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nazwa:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

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
        formPanel.add(new JLabel("Opis roweru:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(bikeDescArea), gbc);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Rower został dodany."));
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
