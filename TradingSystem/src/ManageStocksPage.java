import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class ManageStocksPage extends JFrame {
    private Database db;
    private JTable table;
    private JButton backButton = new JButton("Back");
    private JButton addStockButton = new JButton("Add Stock");

    public ManageStocksPage() throws SQLException {
        this.db = Database.getInstance();

        setTitle("Manage Stocks");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the JTable component
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        try {
            List<Stock> stockList = this.db.getMarketData();

            // Create a DefaultTableModel object to hold the data
            DefaultTableModel model = new DefaultTableModel();

            // Get the column names from the ResultSet metadata
            int numColumns = 4;
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Symbol");
            model.addColumn("Price");

            for (Stock stock : stockList){
                if (!stock.isActive()){
                    continue;
                }
                Object[] rowData = new Object[numColumns];
                rowData[0] = stock.getID();
                rowData[1] = stock.getName();
                rowData[2] = stock.getSymbol();
                rowData[3] = stock.getPrice();
                model.addRow(rowData);
            }

            // Create the JTable with the DefaultTableModel
            table = new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the panel to the JFrame
        add(panel);

        // Add action listener to back button
        backButton.addActionListener(e -> {
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
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(addStockButton);

        // Add the button panel to the bottom of the JFrame
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
