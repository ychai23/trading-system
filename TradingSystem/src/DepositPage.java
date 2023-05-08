import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Customer User Interface - deposit money into account
public class DepositPage extends JFrame {
    private CustomerService cs;
    private JLabel currentCashLabel = new JLabel();
    private JLabel amountLabel = new JLabel("Enter amount to deposit");
    private JTextField amountTextField = new JTextField();
    private JButton depositButton = new JButton("Confirm Deposit");
    private JButton cancelButton = new JButton("Cancel");

    public DepositPage(CustomerService cs) {
        this.cs = cs;
        setTitle("Deposit Page");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(currentCashLabel);
        currentCashLabel.setText("Current Balance: " + String.valueOf(this.cs.getBalance()));
        add(amountLabel);
        add(amountTextField);
        add(depositButton);
        add(cancelButton);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // confirm deposit and deposit
                try{
                    double money = Double.parseDouble(amountTextField.getText());
                    if (money < 0){
                        JOptionPane.showMessageDialog(null,
                                "Please enter valid amount of money",
                                "INVALID AMOUNT",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    cs.deposit(money);
                    currentCashLabel.setText("Current Balance: " + cs.getBalance());
                }
                catch (NumberFormatException en){
                    JOptionPane.showMessageDialog(null, "Please enter valid double.", "Warning", JOptionPane.WARNING_MESSAGE);
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
