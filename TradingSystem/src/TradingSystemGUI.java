import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class TradingSystemGUI extends JFrame {
    private Database db;
    private JLabel fnameLabel = new JLabel("First Name:");
    private JTextField fnameTextField = new JTextField();
    private JLabel lnameLabel = new JLabel("Last Name:");
    private JTextField lnameTextField = new JTextField();
    private JLabel emailLabel = new JLabel("Email:");
    private JTextField emailTextField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordTextField = new JTextField();
    private JButton addButton = new JButton("Add User");

    public TradingSystemGUI(Database db) {
        this.db =db;
        setTitle("Trading System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(fnameLabel);
        add(fnameTextField);
        add(lnameLabel);
        add(lnameTextField);
        add(emailLabel);
        add(emailTextField);
        add(passwordLabel);
        add(passwordTextField);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fname = fnameTextField.getText();
                String lname = lnameTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();

                // Add user to the database
                try {
                    db.addUser(fname, lname, email, password);
                    System.out.println("User added to database!");
                } catch (SQLException ex) {
                    System.out.println("Error adding user to database: " + ex.getMessage());
                }
            }
        });
    }
}
