import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class UserMainPage extends JFrame{
    private JButton buyStockButton = new JButton("Buy Stock");
    private JButton sellStockButton = new JButton("Sell Stock");
    private JButton depositButton = new JButton("Deposit");
    private JButton logoutButton = new JButton("Logout");

    public UserMainPage(Database db) {
        setTitle("Welcome to your trading page!");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 3));
        add(buyStockButton);
        add(sellStockButton);
        add(depositButton);
        add(logoutButton);

        buyStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to buystockpage
                BuyStockPage bsp = new BuyStockPage(db);
                bsp.setVisible(true);
            }
        });

        sellStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to sellstockpage
                SellStockPage ssp = new SellStockPage(db);
                ssp.setVisible(true);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to despositpage
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // logout
                setVisible(false);
                JOptionPane.showMessageDialog(null,
                        "You have logged Out",
                        "LOGGED Out",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}