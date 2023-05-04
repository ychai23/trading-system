import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class BuyStockPage extends JFrame{
    private CustomerService cs;
    private JButton viewStockButton = new JButton(" View ALL Stock in Market");
    private JLabel stockIDLabel = new JLabel("Enter stockID to buy");
    private JTextField stockIDTextField = new JTextField();
    private JLabel stockQuantityLabel = new JLabel("Enter quantity");
    private JTextField stockQuantityTextField = new JTextField();
    private JButton confirmBuyButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");

    public BuyStockPage(CustomerService cs) {
        this.cs = cs;
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
                try {
                    if (!cs.buyStock(Integer.parseInt(stockIDTextField.getText()), Integer.parseInt(stockQuantityTextField.getText()))){
                        JOptionPane.showMessageDialog(null,
                                "Purchase Transaction Failed",
                                "Buy Failed",
                                JOptionPane.WARNING_MESSAGE);
                    } else{
                        JOptionPane.showMessageDialog(null,
                                "Purchase Transaction Success",
                                "Buy Success", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}