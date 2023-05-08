import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;


// Customer User Interface - sell stock from stock market
public class SellStockPage extends JFrame{
    private Database db;
    private CustomerService cs;
    private JButton viewOwnedStockButton = new JButton(" View ALL Owned Stock");
    private JLabel stockIDLabel = new JLabel("Enter stockID to sell");
    private JTextField stockIDTextField = new JTextField();
    private JLabel stockQuantityLabel = new JLabel("Enter quantity");
    private JTextField stockQuantityTextField = new JTextField();
    private JButton confirmSellButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");

    public SellStockPage(CustomerService cs) {
        this.db = Database.getInstance();
        this.cs = cs;
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
                    UserOwnedStocksPage stockPage = new UserOwnedStocksPage(db, cs.getCustomer().getId());
                    stockPage.displayUserStocks();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        confirmSellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // complete the sell for that stock
                try {
                    if (!cs.sellStock(Integer.parseInt(stockIDTextField.getText()), Integer.parseInt(stockQuantityTextField.getText()))){
                        JOptionPane.showMessageDialog(null,
                                "Sell Transaction Failed",
                                "Sell Failed",
                                JOptionPane.WARNING_MESSAGE);
                    } else{
                        JOptionPane.showMessageDialog(null,
                                "Stock Sold Success",
                                "Sell Success", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }catch (NoDataFoundException er) {
                    JOptionPane.showMessageDialog(null, "No available stock found.", "Warning", JOptionPane.WARNING_MESSAGE);
                }catch (NumberFormatException en){
                    JOptionPane.showMessageDialog(null, "Please enter valid integer.", "Warning", JOptionPane.WARNING_MESSAGE);
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