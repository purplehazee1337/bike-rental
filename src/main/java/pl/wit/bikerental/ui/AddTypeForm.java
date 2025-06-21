package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

public class AddTypeForm extends JDialog {
    public AddTypeForm(JFrame parent) {
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

        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Typ roweru został dodany."));
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
