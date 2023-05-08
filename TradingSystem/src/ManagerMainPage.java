import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/*
* Manager Interface - MainPage for the manager to perform operations include
* View/Edit current stock information
* Approve/Stop users
* Logout
*/
public class ManagerMainPage extends JFrame {
    private Database db;
    private JButton manageUsersButton = new JButton("Manage Users");
    private JButton manageStocksButton = new JButton("Manage Stocks");
    private JButton logoutButton = new JButton("Logout");

    public ManagerMainPage() {
        this.db = Database.getInstance();
        setTitle("Welcome to the Manager page!");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));
        add(manageUsersButton);
        add(manageStocksButton);
        add(logoutButton);


        manageUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to manage users page
                ManageUsersPage mup = null;
                try {
                    mup = new ManageUsersPage();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                mup.setVisible(true);
            }
        });


        manageStocksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to manage stocks page
                ManageStocksPage msp = null;
                try {
                    msp = new ManageStocksPage();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                msp.setVisible(true);
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
