package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

public class ReturnRentalForm extends JDialog {
    private JComboBox<Integer> rentalIdComboBox;

    public ReturnRentalForm(JFrame parent) {
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
        panel.add(rentalIdComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton returnButton = new JButton("Zwróć");
        panel.add(returnButton, gbc);

        returnButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Wypożyczenie zostało zwrócone");
        });

        add(panel);
    }
}