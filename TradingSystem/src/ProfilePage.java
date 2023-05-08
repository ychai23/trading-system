import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

// Customer User Interface - view customer's information
public class ProfilePage extends JFrame{
    private Customer c;
    private JLabel firstnameLabel = new JLabel();
    private JLabel lastnameLabel = new JLabel();
    private JLabel currentCashLabel = new JLabel();

    private JLabel unrealizedProfitLable = new JLabel();
    private JLabel realizedProfitLable = new JLabel();
    private JLabel pwLabel = new JLabel();
    private JLabel emailLabel = new JLabel();
    private JButton backButton = new JButton("Back");
    private JButton editButton = new JButton("Edit");
    public ProfilePage(CustomerService cs) throws SQLException {
        this.c = cs.getCustomer();
        if(c.getRealizedProfit() >= 10000){
            JOptionPane.showMessageDialog(null,
                    "You have made over $10,000 in realized profit! You are now allowed to make a derivative trading account!",
                    "Derivative Trading Account",
                    JOptionPane.WARNING_MESSAGE);
        }


        setTitle("Profile Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        add(firstnameLabel);
        firstnameLabel.setText("First Name: " + String.valueOf(this.c.getFname()));
        add(lastnameLabel);
        lastnameLabel.setText("Last Name: " + String.valueOf(this.c.getLname()));
        add(emailLabel);
        emailLabel.setText("Last Name: " + String.valueOf(this.c.getEmail()));
        add(pwLabel);
        pwLabel.setText("Password: " + String.valueOf(this.c.getPassword()));
        add(currentCashLabel);
        currentCashLabel.setText("Current Balance: " + String.valueOf(this.c.getbaseCash()));
        add(unrealizedProfitLable);
        unrealizedProfitLable.setText(("Unrealized Profit:" + cs.totalUnrealizedProfit(c.getId())));
        add(realizedProfitLable);
        realizedProfitLable.setText(("Realized Profit:" + cs.getRealizedProfit()));
        add(backButton);
        add(editButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditProfilePage ep = new EditProfilePage(cs);
                ep.setVisible(true);
                setVisible(false);
            }
        });
    }
}