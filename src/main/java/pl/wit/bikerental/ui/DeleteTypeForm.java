package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;

public class DeleteTypeForm extends JDialog {
    public DeleteTypeForm(JFrame parent) {
        super(parent, "Usuń typ roweru", true);
        setSize(200, 160);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JComboBox<Integer> idCombo = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Wybierz ID typu:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idCombo, gbc);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Usuń");
        JButton cancelButton = new JButton("Anuluj");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        deleteButton.addActionListener(e -> {
            Integer selectedId = (Integer) idCombo.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Typ roweru o ID:" + selectedId + " został usunięty.");
        });

        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
