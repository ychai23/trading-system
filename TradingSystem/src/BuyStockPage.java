import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

// Customer User Interface - buy stock from stock market
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
                Market market = null;
                try {
                    market = Market.getInstance();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());

                // Create a panel to hold the market table
                JPanel marketPanel = market.displayMarket(false);

                // Add the market panel to the main panel
                mainPanel.add(marketPanel, BorderLayout.CENTER);

                JFrame frame = new JFrame("Market View");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(marketPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });


        confirmBuyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // complete the purchase for that stock
                try {
                    int stockid = Integer.parseInt(stockIDTextField.getText());
                    int stockQuantity = Integer.parseInt(stockQuantityTextField.getText());

                    if (stockQuantity <= 0){
                        JOptionPane.showMessageDialog(null, "Please enter valid positive integer.", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else{
                        if (!cs.buyStock(stockid, stockQuantity)){
                            JOptionPane.showMessageDialog(null,
                                    "Purchase Transaction Failed",
                                    "Buy Failed",
                                    JOptionPane.WARNING_MESSAGE);
                        } else{
                            JOptionPane.showMessageDialog(null,
                                    "Purchase Transaction Success",
                                    "Buy Success", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (NoDataFoundException er) {
                    JOptionPane.showMessageDialog(null, "No available stock found.", "Warning", JOptionPane.WARNING_MESSAGE);
                } catch (NumberFormatException en){
                   JOptionPane.showMessageDialog(null, "Please enter valid integer.", "Warning", JOptionPane.WARNING_MESSAGE);
                 }
//            throw new RuntimeException(e);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}