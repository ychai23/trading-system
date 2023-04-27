import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class SellStockPage extends JFrame{
    private Database db;
    private JButton viewOwnedStockButton = new JButton(" View ALL Owned Stock");
    private JLabel stockIDLabel = new JLabel("Enter stockID to sell");
    private JTextField stockIDTextField = new JTextField();
    private JLabel stockQuantityLabel = new JLabel("Enter quantity");
    private JTextField stockQuantityTextField = new JTextField();
    private JButton confirmSellButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");

    public SellStockPage() {
        this.db = Database.getInstance();
        setTitle("Stock Sell Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(stockIDLabel);
        add(stockIDTextField);
        add(stockQuantityLabel);
        add(stockQuantityTextField);
        add(viewOwnedStockButton);
        add(confirmSellButton);
        add(cancelButton);

        viewOwnedStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // display stocktable owned by user tbd
                try {
                    List<Stock> owned = db.getOwnedStocks(1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        confirmSellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // complete the sell for that stock
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}