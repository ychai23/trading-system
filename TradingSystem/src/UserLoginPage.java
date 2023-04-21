import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class UserLoginPage extends JFrame {
    private Database db;
    private JLabel emailLabel = new JLabel("Email:");
    private JTextField emailTextField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordTextField = new JTextField();
    private JButton loginButton = new JButton("Login");

    public UserLoginPage(Database db) {
        this.db = db;
        setTitle("Trading System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(emailLabel);
        add(emailTextField);
        add(passwordLabel);
        add(passwordTextField);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = passwordTextField.getText();
                System.out.println(email);
                System.out.println(password);
                // Add user to the database
                try {
                    boolean success = db.checkUser(email, password);
                    if (success) {
                        System.out.println("User successfully logged in!");
                        UserMainPage ump = new UserMainPage(db);
                        ump.setVisible(true);
                    } else{
                        // prompt to re-enter
                        System.out.println("Error login to database.");
                        JOptionPane.showMessageDialog(null,
                                "User not found / Wrong Password.",
                                "NOT FOUND",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error login to database: " + ex.getMessage());
                }
            }
        });
    }
}
