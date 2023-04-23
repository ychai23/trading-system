import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageUsersPage extends JFrame {
    private Database db;
    private JTable table;
    private JButton backButton = new JButton("Back");

    public ManageUsersPage() throws SQLException {
        this.db = Database.getInstance();
        setTitle("Manage Users");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        db.showUsers();

        // Add action listener to back button
        backButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
    }
}
