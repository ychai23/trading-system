import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;


// Shared User Interface - allows users to register
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
    private JButton cancelButton = new JButton("Cancel");

    public UserRegister() throws SQLException {
        this.db = Database.getInstance();
        setTitle("Register a new user");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));
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
        add(cancelButton);

        // initially enable the manager checkbox
        managerCheckBox.setEnabled(true);

        if (db.managerExists()) {
            System.out.println("Manager is there");
            // if a Manager has been registered, disable the manager checkbox
            managerCheckBox.setEnabled(false);
        } 

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to main page
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
                setVisible(false);
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // make sure none of the fields are empty
                if (fnameTextField.getText().equals("") || lnameTextField.getText().equals("") || emailTextField.getText().equals("") || passwordTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please fill out all fields.",
                            "EMPTY FIELDS",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // make sure the email is valid
                if (!emailTextField.getText().contains("@")) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid email.",
                            "INVALID EMAIL",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String fname = fnameTextField.getText();
                String lname = lnameTextField.getText();
                String email = emailTextField.getText();
                String password = passwordTextField.getText();

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(addButton); // Get the current frame


                if(managerCheckBox.isSelected()){
                    try {
                        if(db.addUserToDB(fname, lname, email, password, "Manager")){
                            Manager manager = new Manager(fname, lname, email, password, "Manager");
                            currentFrame.dispose();
                            UserLoginPage loginPage = new UserLoginPage();
                            loginPage.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
                else{
                    // registering new customer
                    try {
                        if(!db.managerExists()){
                            // show warning 
                            JOptionPane.showMessageDialog(null,
                                    "Please register as a manager.",
                                    "Manager Does not Exist",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            Customer customer = new Customer(fname, lname, email, password, "Customer",0,0);
                            if(db.addUserToDB(fname, lname, email, password, "Customer")){
                                currentFrame.dispose();
                                UserLoginPage loginPage = new UserLoginPage();
                                loginPage.setVisible(true);
                            }
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
