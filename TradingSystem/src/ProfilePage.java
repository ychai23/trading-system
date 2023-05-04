import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ProfilePage extends JFrame{
    private Database db;
    private CustomerService cs;
    private JLabel currentCashLabel = new JLabel();
    private JButton viewOwnedStockButton = new JButton("Owned Stocks");
    private JButton backButton = new JButton("Back");
    public ProfilePage(CustomerService cs) {
        this.cs = cs;
        this.db = Database.getInstance();
        setTitle("Profile Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        add(currentCashLabel);
        currentCashLabel.setText("Current Balance: " + String.valueOf(this.cs.getBalance()));
        add(viewOwnedStockButton);
        add(backButton);
        

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
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
    }
}