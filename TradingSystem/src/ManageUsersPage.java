import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManageUsersPage extends JFrame {
    private Database db;
    private JTable table;
    private JButton backButton = new JButton("Back");
    private JTextField idField = new JTextField(10);
    private JButton viewButton = new JButton("View User");

    public ManageUsersPage() throws SQLException {
        this.db = Database.getInstance();

        setTitle("Manage Users");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // list all of users
        // get list of users from database
            // Create a panel to hold the JTable component
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
    
            try {
                List<User> userList = this.db.getUserData();
    
                // Create a DefaultTableModel object to hold the data
                DefaultTableModel model = new DefaultTableModel();
    
                // Get the column names from the ResultSet metadata
    
                int numColumns = 5;
                model.addColumn("ID");
                model.addColumn("First Name");
                model.addColumn("Last Name");
                model.addColumn("Email");
                model.addColumn("Status");
    
    
                for (User user : userList){
                    Object[] rowData = new Object[numColumns];
                    rowData[0] = db.getUserID(user.getEmail());
                    rowData[1] = user.getFname();
                    rowData[2] = user.getLname();
                    rowData[3] = user.getEmail();
                    rowData[4] = user.isActive();
                    model.addRow(rowData);
                }
    
    
                // Create the JTable with the DefaultTableModel
                table = new JTable(model);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            panel.add(new JScrollPane(table), BorderLayout.CENTER);
    
            // Add the panel to the JFrame
            add(panel);
    
            // Set the size and visibility of the JFrame
            setSize(500, 300);
            setVisible(true);

        // go to user page by having a text box where you can enter the user id
        // Add user input fields to the panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter User ID:"));
        inputPanel.add(idField);
        inputPanel.add(viewButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add action listener to view button
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(idField.getText());
                try {
                    User user = db.getUserFromID(userId);
                    if (user != null) {
                        setVisible(false);
                        dispose();
                        UserPage up = new UserPage(user);
                        up.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(ManageUsersPage.this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // add a new user

        // Add action listener to back button
        backButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
    }
}
