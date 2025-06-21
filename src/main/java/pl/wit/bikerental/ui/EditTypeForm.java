package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

public class EditTypeForm extends JDialog {
    public EditTypeForm(JFrame parent) {
        super(parent, "Edytuj typ roweru", true);
        setSize(380, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JComboBox<Integer> idCombo = new JComboBox<>();
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

        updateButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Typ roweru został edytowany."));
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
