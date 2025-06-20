package pl.wit.bikerental.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;


public class MainFrame extends JFrame {

    private JPanel leftCardPanel;
    private JPanel centerCardPanel;

    public MainFrame() {
        setTitle("Wypożyczalnia rowerów");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton bikesButton = createButton("Rowery");
        JButton clientsButton = createButton("Klienci");
        JButton rentalsButton = createButton("Wypożyczenia");

        topPanel.add(bikesButton);
        topPanel.add(clientsButton);
        topPanel.add(rentalsButton);

        leftCardPanel = new JPanel(new CardLayout());
        leftCardPanel.setPreferredSize(new Dimension(260, getHeight()));

        JPanel bikeButtons = new JPanel();
        bikeButtons.setLayout(new BoxLayout(bikeButtons, BoxLayout.Y_AXIS));
        bikeButtons.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        bikeButtons.add(createButton("Nowy rower"));
        bikeButtons.add(Box.createVerticalStrut(10));
        bikeButtons.add(createButton("Pokaż niezwrócone rowery"));
        bikeButtons.add(Box.createVerticalStrut(10));
        bikeButtons.add(createButton("Pokaż rowery na wypożyczeniu"));
        bikeButtons.add(Box.createVerticalStrut(10));
        bikeButtons.add(createButton("Edytuj rower"));
        bikeButtons.add(Box.createVerticalStrut(10));
        bikeButtons.add(createButton("Usuń rower"));
        bikeButtons.add(Box.createVerticalStrut(10));
        bikeButtons.add(createButton("Nowy typ roweru"));
        bikeButtons.add(Box.createVerticalStrut(10));
        bikeButtons.add(createButton("Edytuj typ roweru"));
        bikeButtons.add(Box.createVerticalStrut(10));
        bikeButtons.add(createButton("Usuń typ roweru"));

        JPanel clientButtons = new JPanel();
        clientButtons.setLayout(new BoxLayout(clientButtons, BoxLayout.Y_AXIS));
        clientButtons.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        clientButtons.add(createButton("Nowy klient"));
        clientButtons.add(Box.createVerticalStrut(10));
        clientButtons.add(createButton("Edytuj klienta"));
        clientButtons.add(Box.createVerticalStrut(10));
        clientButtons.add(createButton("Usuń klienta"));

        JPanel rentalButtons = new JPanel();
        rentalButtons.setLayout(new BoxLayout(rentalButtons, BoxLayout.Y_AXIS));
        rentalButtons.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        rentalButtons.add(createButton("Nowe wypożyczenie"));
        rentalButtons.add(Box.createVerticalStrut(10));
        rentalButtons.add(createButton("Zwróć wypożyczenie"));

        leftCardPanel.add(bikeButtons, "rowery");
        leftCardPanel.add(clientButtons, "klienci");
        leftCardPanel.add(rentalButtons, "wypozyczenia");

        centerCardPanel = new JPanel(new CardLayout());

        String[] bikeCols = {"ID", "Nazwa", "Typ", "Marka", "Model", "Rozmiar koła", "Opis", "Cena za h", "Status"};
        Object[][] bikeData = {{"placeholder", "placeholder", "placeholder", "placeholder", "placeholder", "placeholder", "placeholder", "placeholder", "placeholder"}};
        JTable bikeTable = new JTable(bikeData, bikeCols);
        centerCardPanel.add(new JScrollPane(bikeTable), "rowery");

        String[] clientCols = {"ID", "Imię", "Nazwisko", "Telefon", "E-mail"};
        Object[][] clientData = {{"placeholder", "placeholder", "placeholder", "placeholder", "placeholder"}};
        JTable clientTable = new JTable(clientData, clientCols);
        centerCardPanel.add(new JScrollPane(clientTable), "klienci");

        String[] rentalCols = {"ID", "Klient", "Rower", "Od", "Do", "Status"};
        Object[][] rentalData = {{"placeholder", "placeholder", "placeholder", "placeholder", "placeholder", "placeholder"}};
        JTable rentalTable = new JTable(rentalData, rentalCols);
        centerCardPanel.add(new JScrollPane(rentalTable), "wypozyczenia");

        bikesButton.addActionListener(e -> {
            ((CardLayout) leftCardPanel.getLayout()).show(leftCardPanel, "rowery");
            ((CardLayout) centerCardPanel.getLayout()).show(centerCardPanel, "rowery");
        });

        clientsButton.addActionListener(e -> {
            ((CardLayout) leftCardPanel.getLayout()).show(leftCardPanel, "klienci");
            ((CardLayout) centerCardPanel.getLayout()).show(centerCardPanel, "klienci");
        });

        rentalsButton.addActionListener(e -> {
            ((CardLayout) leftCardPanel.getLayout()).show(leftCardPanel, "wypozyczenia");
            ((CardLayout) centerCardPanel.getLayout()).show(centerCardPanel, "wypozyczenia");
        });

        add(topPanel, BorderLayout.NORTH);
        add(leftCardPanel, BorderLayout.WEST);
        add(centerCardPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        Dimension size = new Dimension(240, 40);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
