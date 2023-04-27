import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddStockPage extends JFrame {
    private ManagerService ms;
    private JTextField nameField = new JTextField(20);
    private JTextField priceField = new JTextField(10);
    private JTextField symbolField = new JTextField(10);
    private JButton addButton = new JButton("Add Stock");

    public AddStockPage() throws SQLException {
        this.ms = ManagerService.getInstance();

        setTitle("Add Stock");
        setSize(400, 150);

        // Create a panel to hold the input fields and button
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Symbol:"));
        panel.add(symbolField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        // Add the panel to the JFrame
        add(panel, BorderLayout.CENTER);

        // Add action listener to add button
        addButton.addActionListener(e -> {
            // Get the values from the input fields
            String name = nameField.getText().trim();
            String symbol = symbolField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());

            // Add the new stock to the database
            Stock stock = new Stock(name,symbol,price);
            ms.addStockToMarket(stock);

            // Display a success message and close the window
            JOptionPane.showMessageDialog(this, "Stock added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            dispose();
        });
        add(addButton, BorderLayout.SOUTH);
    }
}
