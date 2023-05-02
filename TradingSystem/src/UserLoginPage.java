import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class UserLoginPage extends JFrame {
    private Database db;
    private JLabel emailLabel = new JLabel("Email:");
    private JTextField emailTextField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordTextField = new JTextField();
    private JButton loginButton = new JButton("Login");
    private JButton cancelButton = new JButton("Cancel");

    public UserLoginPage() {
        this.db = Database.getInstance();
        setTitle("Log in to Your Account");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        add(emailLabel);
        add(emailTextField);
        add(passwordLabel);
        add(passwordTextField);
        add(loginButton);
        add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to main page
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
                setVisible(false);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = passwordTextField.getText();
                System.out.println(email);
                System.out.println(password);
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(loginButton); // Get the current frame

                // Add user to the database
                try {
                    boolean success = db.checkUserInDB(email, password);
                    if (success) {
                        System.out.println("User successfully logged in!");
                        currentFrame.dispose();
                        // If user role is manager, redirect to manager page
                        if (db.getUserRole(email).equals("Manager")) {
                            ManagerMainPage mmp = new ManagerMainPage();
                            mmp.setVisible(true);
                        }
                        else{
                            Customer c = db.getCustomer(email);
                            CustomerMainPage cmp = new CustomerMainPage(c);
                            cmp.setVisible(true);
                        }

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
