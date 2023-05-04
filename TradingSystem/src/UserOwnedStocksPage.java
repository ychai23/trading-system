import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class UserOwnedStocksPage extends JFrame {
    private List<Stock> stockList;

    private JTable table;

    private int userid;

    private HashMap<Integer, Integer> stockQuantity;
    private Database db;
    public UserOwnedStocksPage(Database db ,int userid) throws SQLException {
        this.db = db;
        stockList = this.db.getOwnedStocks(userid);
        stockQuantity = this.db.getUserStockQuantity(userid);
        this.userid = userid;
    }

    public void displayUserStocks() {
        // Create a panel to hold the JTable component
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        try {

            // Create a DefaultTableModel object to hold the data
            DefaultTableModel model = new DefaultTableModel();

            // Get the column names from the ResultSet metadata

            int numColumns = 6;
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Price");
            model.addColumn("BuyPrice");
            model.addColumn("Quantity");
            model.addColumn("Profit");



            // Populate the DefaultTableModel with data from the ResultSet
            // Ask about whether we can use this instead of ID !!!
            for (int i : stockQuantity.keySet()){
                Stock stock = db.getStockFromID(i);
                if (!stock.isActive()){
                    continue;
                }
                Object[] rowData = new Object[numColumns];
                rowData[0] = stock.getID();
                rowData[1] = stock.getName();
                rowData[2] = stock.getPrice();
                rowData[3] = db.getBalance(this.userid , stock.getID());
                rowData[4] = stockQuantity.get(stock.getID());
                rowData[5] = stockQuantity.get(stock.getID()) * (stock.getPrice() -  db.getBalance(this.userid , stock.getID()));

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
}