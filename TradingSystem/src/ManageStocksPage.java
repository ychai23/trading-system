import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class ManageStocksPage extends JFrame {
    private ManagerService ms;
    private JTable table;
    private JButton addStockButton = new JButton("Add Stock");
    // cancel button
    private JButton cancelButton = new JButton("Cancel");
    private JTextField idField = new JTextField(10);
    private JButton viewStockButton = new JButton("View Stock");

    public ManageStocksPage() throws SQLException {
        this.ms = ManagerService.getInstance();

        setTitle("Manage Stocks");
        setSize(600, 400);

        // Create a panel to hold the JTable component
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        List<Stock> stockList = this.ms.getMarketData();

        // Create a DefaultTableModel object to hold the data
        DefaultTableModel model = new DefaultTableModel();

        // Get the column names from the ResultSet metadata
        int numColumns = 5;
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Symbol");
        model.addColumn("Price");
        model.addColumn("Status");


        for (Stock stock : stockList){
            Object[] rowData = new Object[numColumns];
            rowData[0] = stock.getID();
            rowData[1] = stock.getName();
            rowData[2] = stock.getSymbol();
            rowData[3] = stock.getPrice();
            rowData[4] = stock.isActive();

            model.addRow(rowData);
        }

        // Create the JTable with the DefaultTableModel
        table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

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
