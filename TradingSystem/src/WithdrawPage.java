import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class WithdrawPage extends JFrame{
    private Database db;
    private JLabel amountLabel = new JLabel("Enter amount to withdraw");
    private JTextField amountTextField = new JTextField();
    private JButton withdrawButton = new JButton("Confirm Withdraw");
    private JButton cancelButton = new JButton("Cancel");
    public WithdrawPage() {
        this.db = Database.getInstance();
        setTitle("Withdraw Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(amountLabel);
        add(amountTextField);
        add(cancelButton);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // confirm withdraw and withdraws
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}