import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomerMainPage extends JFrame{
    private Database db;
    private JButton buyStockButton = new JButton("Buy Stock");
    private JButton sellStockButton = new JButton("Sell Stock");
    private JButton depositButton = new JButton("Deposit");
    private JButton withdrawButton = new JButton("Withdraw");
    private JButton logoutButton = new JButton("Logout");

    public CustomerMainPage() {
        this.db = Database.getInstance();
        setTitle("Welcome to your trading page!");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 3));
        add(buyStockButton);
        add(sellStockButton);
        add(depositButton);
        add(withdrawButton);
        add(logoutButton);

        buyStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to buystockpage
                BuyStockPage bsp = new BuyStockPage();
                bsp.setVisible(true);
            }
        });

        sellStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to sellstockpage
                SellStockPage ssp = new SellStockPage();
                ssp.setVisible(true);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to despositpage
                DepositPage dp = new DepositPage();
                dp.setVisible(true);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to withdrawpage
                WithdrawPage wp = new WithdrawPage();
                wp.setVisible(true);
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
                MainPage mp = MainPage.getMainPage();
                mp.setVisible(true);
            }
        });
    }
}