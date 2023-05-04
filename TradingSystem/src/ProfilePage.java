import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ProfilePage extends JFrame{
    private CustomerService cs;
    private JLabel currentCashLabel = new JLabel();
    private JButton backButton = new JButton("Back");
    public ProfilePage(CustomerService cs) {
        this.cs = cs;
        setTitle("Profile Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        add(currentCashLabel);
        currentCashLabel.setText("Current Balance: " + String.valueOf(this.cs.getBalance()));
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}