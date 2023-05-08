import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

// Manager User Interface - allows manager to edit/activate/deactivate users
public class UserPage extends JFrame {
    private User user;
    public UserPage(User user) {
        this.user = user;

        // Create JLabels and JTextFields to display user's name and email
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(user.getFname());
        nameField.setEditable(false);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(user.getEmail());
        emailField.setEditable(false);

        // cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // redirect to ManageUsersPage
                ManageUsersPage mup = null;
                try {
                    mup = new ManageUsersPage();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                mup.setVisible(true);
                setVisible(false);
            }
        });

        // Create JButton to deactivate user
        JButton deactivateButton = new JButton("Deactivate User");
        deactivateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user.setInactive();
                // redirect to manage users page
                ManageUsersPage mup = null;
                try {
                    mup = new ManageUsersPage();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                mup.setVisible(true);
 
            }
        });
        // Create JButton to deactivate user
        JButton activateButton = new JButton("Activate User");
        activateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user.setActive();
                // redirect to manage users page
                ManageUsersPage mup = null;
                try {
                    mup = new ManageUsersPage();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                mup.setVisible(true);

            }
        });

        // Create JPanel to hold components
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        if(user.isActive()){
            System.out.println(user.isActive());
            panel.add(deactivateButton);
        }
        else{
            panel.add(activateButton);
        }

        panel.add(cancelButton);

        // Add panel to frame
        this.add(panel);

        // Set frame properties
        this.setTitle("User Page");
        this.setSize(300, 150);
        this.setVisible(true);
    }

    
}
