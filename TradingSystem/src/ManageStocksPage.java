import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;



// Manager User Interface - view/edit stock information
public class ManageStocksPage extends JFrame {
    private ManagerService ms;
    private Market market;
    private JTable table;
    private JButton addStockButton = new JButton("Add Stock");
    // cancel button
    private JButton cancelButton = new JButton("Cancel");
    private JTextField idField = new JTextField(10);
    private JButton viewStockButton = new JButton("View Stock");

    public ManageStocksPage() throws SQLException {
        this.market = Market.getInstance();
        this.ms = ManagerService.getInstance();

        setTitle("Manage Stocks");
        setSize(600, 400);

        // Create a panel to hold the JTable component
        JPanel panel = market.displayMarket(true);


        // Add the panel to the JFrame
        add(panel);

        // Add action listener to back button
        cancelButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        // Add action listener to add stock button
        addStockButton.addActionListener(e -> {
            // Open the AddStockPage
            try {
                AddStockPage addStockPage = new AddStockPage();
                addStockPage.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            setVisible(false);
        });
        viewStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String symbol = idField.getText();
                Stock stock = ms.getStockFromID(ms.getStockId(symbol));
                if(stock == null){
                    JOptionPane.showMessageDialog(null, "Stock not found");
                    return;
                }
                StockPage stockPage = new StockPage(stock);
                stockPage.setVisible(true);
                setVisible(false);
            }
        });

        // Create a panel to hold the buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 3));
        inputPanel.add(new JLabel("Enter Stock Symbol:"));
        inputPanel.add(idField);
        inputPanel.add(viewStockButton);
        inputPanel.add(addStockButton);
        inputPanel.add(cancelButton);
        panel.add(inputPanel, BorderLayout.SOUTH);
    }

}
