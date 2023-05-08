import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// Manager User Interface - add stock to the stock market
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

            try{
                double price = Double.parseDouble(priceField.getText().trim());
                if (price <= 0 || Double.isInfinite(price)){
                    JOptionPane.showMessageDialog(null, "Please enter valid double price.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else{
                    Stock stock = new Stock(name,symbol,price);
                    ms.addStockToMarket(stock);
                }
            }catch (NumberFormatException num){
                JOptionPane.showMessageDialog(null, "Please enter valid double price.", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            // Add the new stock to the database

            // open the manage stocks page
            try {
                ManageStocksPage msp = new ManageStocksPage();
                msp.setVisible(true);
                setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        add(addButton, BorderLayout.SOUTH);
    }
}
