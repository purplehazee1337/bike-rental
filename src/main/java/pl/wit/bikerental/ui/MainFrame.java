package pl.wit.bikerental.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.wit.bikerental.model.Bike;
import pl.wit.bikerental.model.Client;
import pl.wit.bikerental.model.Rental;
import pl.wit.bikerental.model.Types;
import pl.wit.bikerental.reporting.Raports;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel leftCardPanel;
    private JPanel centerCardPanel;

    private List<Bike> bikes;
    private List<Client> clients;
    private List<Rental> rentals;
    private List<Types> types;

    private JTable bikeTable;
    private JTable clientTable;
    private JTable rentalTable;
    private JTable typesTable;

    public MainFrame(List<Bike> bikes, List<Client> clients, List<Rental> rentals, List<Types> types) {
        this.bikes = bikes;
        this.clients = clients;
        this.rentals = rentals;
        this.types = types;

        setTitle("Wypożyczalnia rowerów");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Top navigation buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton bikesButton = createButton("Rowery");
        JButton clientsButton = createButton("Klienci");
        JButton rentalsButton = createButton("Wypożyczenia");
        JButton typesButton = createButton("Typy rowerów");

        topPanel.add(bikesButton);
        topPanel.add(clientsButton);
        topPanel.add(rentalsButton);
        topPanel.add(typesButton);

        // Side panels for action buttons
        leftCardPanel = new JPanel(new CardLayout());
        leftCardPanel.setPreferredSize(new Dimension(260, getHeight()));

        leftCardPanel.add(createBikeButtonPanel(), "rowery");
        leftCardPanel.add(createClientButtonPanel(), "klienci");
        leftCardPanel.add(createRentalButtonPanel(), "wypozyczenia");
        leftCardPanel.add(createTypesButtonPanel(), "typy");

        // Central panel with tables
        centerCardPanel = new JPanel(new CardLayout());
        bikeTable = createBikeTable();
        clientTable = createClientTable();
        rentalTable = createRentalTable();
        typesTable = createTypesTable();

        centerCardPanel.add(new JScrollPane(bikeTable), "rowery");
        centerCardPanel.add(new JScrollPane(clientTable), "klienci");
        centerCardPanel.add(new JScrollPane(rentalTable), "wypozyczenia");
        centerCardPanel.add(new JScrollPane(typesTable), "typy");

        // Action listeners for switching views
        bikesButton.addActionListener(e -> switchCard("rowery"));
        clientsButton.addActionListener(e -> switchCard("klienci"));
        rentalsButton.addActionListener(e -> switchCard("wypozyczenia"));
        typesButton.addActionListener(e -> switchCard("typy"));

        add(topPanel, BorderLayout.NORTH);
        add(leftCardPanel, BorderLayout.WEST);
        add(centerCardPanel, BorderLayout.CENTER);
    }
   
    

    public void switchCard(String name) {
        ((CardLayout) leftCardPanel.getLayout()).show(leftCardPanel, name);
        ((CardLayout) centerCardPanel.getLayout()).show(centerCardPanel, name);
    }
    
    private JPanel createBikeButtonPanel() {
        JPanel panel = createVerticalButtonPanel(
            "Nowy rower",
            "Edytuj rower",
            "Usuń rower",
            "Pokaż dostępne rowery",
            "Pokaż rowery na wypożyczeniu",
            "Pokaż rowery niezwrócone na czas"
        );

        final JFrame parent = this;

        Map<String, Runnable> actions = new HashMap<>();
        actions.put("Nowy rower", () -> new AddBikeForm(parent, this.bikes, this.types).setVisible(true));
        actions.put("Edytuj rower", () -> new EditBikeForm(parent, this.bikes, this.types).setVisible(true));
        actions.put("Usuń rower", () -> new DeleteBikeForm(parent, this.bikes, this.rentals).setVisible(true));
        
        actions.put("Pokaż dostępne rowery", () -> createRaportFrame(Raports.unrentedBikes(bikes), "Dostępne rowery"));
        actions.put("Pokaż rowery na wypożyczeniu", () -> createRaportFrame(Raports.currentlyRentedBikes(rentals), "Wypożycznone rowery"));
        actions.put("Pokaż rowery niezwrócone na czas", () -> createRaportFrame(Raports.overtimeRentedBikes(rentals), "Rowery niezwrócone na czas"));

        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                Runnable action = actions.get(button.getText());
                if (action != null) {
                    button.addActionListener(e -> action.run());
                }
            }
        }

        return panel;
    }
    
    private JPanel createClientButtonPanel() {
        JPanel panel = createVerticalButtonPanel(
            "Nowy klient",
            "Edytuj klienta",
            "Usuń klienta"
        );

        final JFrame parent = this;

        Map<String, Runnable> actions = new HashMap<>();
        actions.put("Nowy klient", () -> new AddClientForm(parent, this.clients).setVisible(true));
        actions.put("Edytuj klienta", () -> new EditClientForm(parent, this.clients).setVisible(true));
        actions.put("Usuń klienta", () -> new DeleteClientForm(parent, this.clients, this.rentals).setVisible(true));

        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                Runnable action = actions.get(button.getText());
                if (action != null) {
                    button.addActionListener(e -> action.run());
                }
            }
        }

        return panel;
    }

    private JPanel createRentalButtonPanel() {
        JPanel panel = createVerticalButtonPanel(
            "Nowe wypożyczenie",
            "Zwróć wypożyczenie"
        );

        final JFrame parent = this;

        Map<String, Runnable> actions = new HashMap<>();
        actions.put("Nowe wypożyczenie", () -> new AddRentalForm(parent, this.bikes, this.clients, this.rentals).setVisible(true));
        actions.put("Zwróć wypożyczenie", () -> new ReturnRentalForm(parent, this.rentals).setVisible(true));

        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                Runnable action = actions.get(button.getText());
                if (action != null) {
                    button.addActionListener(e -> action.run());
                }
            }
        }

        return panel;
    }
    
    private JPanel createTypesButtonPanel() {
        JPanel panel = createVerticalButtonPanel(
        		"Nowy typ roweru",
                "Edytuj typ roweru",
                "Usuń typ roweru"
        );

        final JFrame parent = this;

        Map<String, Runnable> actions = new HashMap<>();
        actions.put("Nowy typ roweru", () -> new AddTypeForm(parent, this.types).setVisible(true));
        actions.put("Edytuj typ roweru", () -> new EditTypeForm(parent, this.types).setVisible(true));
        actions.put("Usuń typ roweru", () -> new DeleteTypeForm(parent, this.types, this.bikes).setVisible(true));

        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                Runnable action = actions.get(button.getText());
                if (action != null) {
                    button.addActionListener(e -> action.run());
                }
            }
        }

        return panel;
    }

    private JPanel createVerticalButtonPanel(String... buttonNames) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        for (String name : buttonNames) {
            panel.add(createButton(name));
            panel.add(Box.createVerticalStrut(10));
        }
        return panel;
    }

    private JTable createBikeTable() {
        String[] cols = {"ID", "Typ", "Marka", "Model", "Rozmiar koła", "Opis", "Cena za godzine", "Stan"};
        Object[][] data = new Object[bikes.size()][cols.length];

        for (int i = 0; i < bikes.size(); i++) {
            Bike b = bikes.get(i);
            data[i][0] = b.getId();
            data[i][1] = b.getType().getName();
            data[i][2] = b.getBrand();
            data[i][3] = b.getModel();
            data[i][4] = b.getWheelSize();
            data[i][5] = b.getDescription();
            data[i][6] = b.getPricePerH();
            data[i][7] = b.isRented() ? "Wypożyczony" : "Dostępny";
        }

        return new JTable(data, cols);
    }

    private JTable createClientTable() {
        String[] cols = {"ID", "Imię", "Nazwisko", "Telefon", "E-mail"};
        Object[][] data = new Object[clients.size()][cols.length];

        for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            data[i][0] = c.getId();
            data[i][1] = c.getFirstName();
            data[i][2] = c.getLastName();
            data[i][3] = c.getPhoneNumber();
            data[i][4] = c.getEmail();
        }

        return new JTable(data, cols);
    }

    private JTable createRentalTable() {
    	
        String[] cols = {"ID", "Klient", "Rower", "Od", "Do", "Stan", "Data zwrotu"};
        Object[][] data = new Object[rentals.size()][cols.length];

        for (int i = 0; i < rentals.size(); i++) {
            Rental r = rentals.get(i);
            data[i][0] = r.getId();
            data[i][1] = r.getClient().getFirstName() + " " + r.getClient().getLastName();
            data[i][2] = r.getBike().getModel();
            data[i][3] = r.getStart().toString();
            data[i][4] = r.getPlannedEnd().toString();
            data[i][5] = r.getActualReturnDate() == null ? "Wypożyczony" : "Zwrócony";
            data[i][6] = r.getActualReturnDate() == null ? "" : r.getActualReturnDate().toString();
        }

        return new JTable(data, cols);
    }
    
    private JTable createTypesTable() {
    	String[] cols = {"ID", "Nazwa", "Opis"};
        Object[][] data = new Object[types.size()][cols.length];

        for (int i = 0; i < types.size(); i++) {
            Types t = types.get(i);
            data[i][0] = t.getId();
            data[i][1] = t.getName();
            data[i][2] = t.getDescription();
        }

        return new JTable(data, cols);
    }

    private void createRaportFrame(List<Bike> filteredBikes, String frameName) {
        String[] cols = {"ID", "Typ", "Marka", "Model", "Rozmiar koła", "Opis", "Cena za godzine", "Stan"};
        Object[][] data = new Object[filteredBikes.size()][cols.length];

        for (int i = 0; i < filteredBikes.size(); i++) {
            Bike b = filteredBikes.get(i);
            data[i][0] = b.getId();
            data[i][1] = b.getType().getName();
            data[i][2] = b.getBrand();
            data[i][3] = b.getModel();
            data[i][4] = b.getWheelSize();
            data[i][5] = b.getDescription();
            data[i][6] = b.getPricePerH();
            data[i][7] = b.isRented() ? "Wypożyczony" : "Dostępny";
        }

        JTable bikeTable = new JTable(data, cols);
        JScrollPane scrollPane = new JScrollPane(bikeTable);

        JFrame frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.add(scrollPane);
        frame.setVisible(true);
    }
    
    public void refreshTables() {
        // Remove old tables
        centerCardPanel.removeAll();

        // Recreate tables with updated data
        bikeTable = createBikeTable();
        clientTable = createClientTable();
        rentalTable = createRentalTable();
        typesTable = createTypesTable();

        // Add to center panel
        centerCardPanel.add(new JScrollPane(bikeTable), "rowery");
        centerCardPanel.add(new JScrollPane(clientTable), "klienci");
        centerCardPanel.add(new JScrollPane(rentalTable), "wypozyczenia");
        centerCardPanel.add(new JScrollPane(typesTable), "typy");

        // Revalidate and repaint to update UI
        centerCardPanel.revalidate();
        centerCardPanel.repaint();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        Dimension size = new Dimension(240, 40);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    
    
}
