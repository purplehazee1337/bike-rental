package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

public class DeleteClientForm extends JDialog {
    public DeleteClientForm(JFrame parent) {
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

        JComboBox<Integer> idCombo = new JComboBox<>();

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
            Integer id = (Integer) idCombo.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Klient o ID:" + id + " został usunięty.");
        });

        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
