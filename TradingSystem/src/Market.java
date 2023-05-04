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
    public static void main(String[] args) throws SQLException {
        Market market = Market.getInstance();
        //market.clearMarket();
        //market.addStock("JP", "JPGM", 22.3);
        //market.addStock("Work", "WEIN", 33.2);
        //market.updateStockPrice("JPGM" , 2.3);
        //market.updateStockPrice("WEIN", 0.11);
        market.blockStockBySymbol("WEIN");
        market.displayMarket();

    }
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


    public void displayMarket() {
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



            // Populate the DefaultTableModel with data from the ResultSet
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

        // Set the size and visibility of the JFrame
        setSize(500, 300);
        setVisible(true);
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
