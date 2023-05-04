import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfilePage extends JFrame {
    private CustomerService cs;
    private JLabel emailLabel = new JLabel("Email:");
    private JTextField emailTextField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordTextField = new JTextField();
    private JButton confirmUpdateButton = new JButton("Confirm Update");
    private JButton cancelButton = new JButton("Cancel");

    public EditProfilePage(CustomerService cs) {
        this.cs = cs;
        setTitle("Edit your profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));
        add(emailLabel);
        add(emailTextField);
        add(passwordLabel);
        add(passwordTextField);
        add(confirmUpdateButton);
        add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to main page
                setVisible(false);
            }
        });
        confirmUpdateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = passwordTextField.getText();

                // make sure the email is valid
                if (email.equals("") || !email.contains("@")) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid email.",
                            "INVALID EMAIL",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if (password.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Password cannot be empty",
                            "INVALID PASSWORD",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else {
                    cs.updateEmail(email);
                    cs.updatePW(password);
                    System.out.println("Successfully edited profile");
                    setVisible(false);
                }
            }
        });
    }
}