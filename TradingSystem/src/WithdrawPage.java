import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class WithdrawPage extends JFrame{
    private CustomerService cs;
    private JLabel currentCashLabel = new JLabel();
    private JLabel amountLabel = new JLabel("Enter amount to withdraw");
    private JTextField amountTextField = new JTextField();
    private JButton withdrawButton = new JButton("Confirm Withdraw");
    private JButton cancelButton = new JButton("Cancel");
    public WithdrawPage(CustomerService cs) {
        this.cs = cs;
        setTitle("Withdraw Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(currentCashLabel);
        currentCashLabel.setText("Current Balance: " + String.valueOf(this.cs.getBalance()));
        add(amountLabel);
        add(amountTextField);
        add(withdrawButton);
        add(cancelButton);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // confirm withdraw and withdraws
                double money = Double.parseDouble(amountTextField.getText());
                if (money < 0){
                    JOptionPane.showMessageDialog(null,
                            "Please enter valid amount of money",
                            "INVALID AMOUNT",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                cs.withdraw(Double.parseDouble(amountTextField.getText()));
                currentCashLabel.setText("Current Balance: " + cs.getBalance());
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}