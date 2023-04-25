import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class BuyStockPage extends JFrame{
    private JButton viewStockButton = new JButton(" View ALL Stock in Market");
    private JLabel stockIDLabel = new JLabel("Enter stockID to buy");
    private JTextField stockIDTextField = new JTextField();
    private JLabel stockQuantityLabel = new JLabel("Enter quantity");
    private JTextField stockQuantityTextField = new JTextField();
    private JButton confirmBuyButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");

    public BuyStockPage() {
        setTitle("Stock Purchase Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(stockIDLabel);
        add(stockIDTextField);
        add(stockQuantityLabel);
        add(stockQuantityTextField);
        add(viewStockButton);
        add(confirmBuyButton);
        add(cancelButton);

        viewStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // display stocktable
                Market market = null;
                try {
                    market = Market.getInstance();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                market.displayMarket();
            }
        });

        confirmBuyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // complete the purchase for that stock
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}