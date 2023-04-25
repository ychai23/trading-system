import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRegister extends JFrame {
    private Database db;
    private JLabel fnameLabel = new JLabel("First Name:");
    private JTextField fnameTextField = new JTextField();
    private JLabel lnameLabel = new JLabel("Last Name:");
    private JTextField lnameTextField = new JTextField();
    private JLabel emailLabel = new JLabel("Email:");
    private JTextField emailTextField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordTextField = new JTextField();
    private JCheckBox managerCheckBox = new JCheckBox("Register as Manager");
    private JButton addButton = new JButton("Add User");

    public UserRegister() throws SQLException {
        this.db = Database.getInstance();
        setTitle("Register a new user");
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
        add(managerCheckBox);
        add(addButton);

        // initially enable the manager checkbox
        managerCheckBox.setEnabled(true);

        if (db.managerExists()) {
            System.out.println("Manager is there");
            // if a Manager has been registered, disable the manager checkbox
            managerCheckBox.setEnabled(false);
        } 

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fname = fnameTextField.getText();
                String lname = lnameTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(addButton); // Get the current frame


                if(managerCheckBox.isSelected()){
                    Manager manager = new Manager(fname, lname, email, password, "Manager");
                    if(manager.addUser()){
                        currentFrame.dispose();
                        MainPage mp = new MainPage();
                        mp.setVisible(true);
                    }

                }
                else{
                    Customer customer = new Customer(fname, lname, email, password, "Customer");
                    if(customer.addUser()){
                        currentFrame.dispose();
                        UserLoginPage loginPage = new UserLoginPage();
                        loginPage.setVisible(true);
                    }
                }
            }
        });
    }
}
