// Import the necessary packages
import java.sql.*;
        import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Market extends JFrame {
    // Declare the JTable component
    private JTable table;

    private Connection conn = null;

    private static Market instance = null;

    private HashMap<String, Stock> symbolStockMapping = new HashMap<>();

    private Database db;

    Statement stmt = null;

    // Constructor

    private Market() throws SQLException {
        // Set the title of the JFrame
        super("Stock Price Data");
        this.db = Database.getInstance();
        List<Stock> stockList = db.getMarketData();
        for (Stock i : stockList){
            symbolStockMapping.put(i.getSymbol(), i);
        }
    }

    public static Market getInstance() throws SQLException {
        if (instance == null) {
            // change this password to your own
            instance = new Market();
        }
        return instance;
    }


    public JPanel displayMarket(boolean showStatus) {
        JPanel panel = new JPanel(new BorderLayout());

        try {
            List<Stock> stockList = this.db.getMarketData();

            // Create a DefaultTableModel object to hold the data
            DefaultTableModel model = new DefaultTableModel();

            // Get the column names from the ResultSet metadata
            int numColumns = showStatus ? 5 : 4;
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Symbol");
            model.addColumn("Price");
            if (showStatus) {
                model.addColumn("Status");
            }

            // Populate the DefaultTableModel with data from the ResultSet
            for (Stock stock : stockList){
                if(!showStatus && !stock.isActive()){
                    continue;
                }
                Object[] rowData = new Object[numColumns];
                rowData[0] = stock.getID();
                rowData[1] = stock.getName();
                rowData[2] = stock.getSymbol();
                rowData[3] = stock.getPrice();
                if (showStatus) {
                    rowData[4] = stock.isActive() ? "Active" : "Blocked";
                }
                model.addRow(rowData);
            }

            // Create the JTable with the DefaultTableModel
            table = new JTable(model);
            panel.add(new JScrollPane(table), BorderLayout.CENTER);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return panel;
    }


    public void addStock(Stock newStock) throws SQLException {

        if (this.db.addStockToMarket(newStock)){
            System.out.println("Successfully inserted a new stock.");
            symbolStockMapping.put(newStock.getSymbol(), newStock);
        }else{
            System.out.println("Insertion failure.");
        }
    }


    // Import the necessary packages

    public Stock getStockBySymbol(String symbol){
        Stock result = symbolStockMapping.getOrDefault(symbol, null);
        if (result == null){
            System.out.println("There is no such a stock.");
        }
        return result;
    }


    public boolean updateStockPrice(Stock updatedStock, double price) throws SQLException {
        if (updatedStock == null){
            System.out.println("Stock to update is null!");
            return false;
        }
        updatedStock.setPrice(price);
        if (this.db.updateStockPrice(updatedStock, price)){
            System.out.println("Successfully updated a new stock.");
            return true;
        }else{
            System.out.println("Insertion failure.");
            return false;
        }
    }

    public void blockStockBySymbol(String symbol) throws SQLException {
        db.blockStock(symbol);
    }

    public void clearMarket() throws SQLException {
        db.deleteTableData("Stocks");
    }
}
