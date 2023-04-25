/*  
Database class using Singleton pattern as there is only one connection the database being made
and class is accessed throughout application.

Class handles all database operations.
*/
import java.sql.*;
public class Database {
    private Connection conn;
    private static Database instance = null;

    private Database(String url, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
    }

    // get instance of database

    public static Database getInstance() {
        if (instance == null) {
            try {
                // change this password to your own
                instance = new Database("jdbc:mysql://localhost/tradingSystem", "root", "saibaba18baba");
    //            TradingSystemGUI gui = new TradingSystemGUI(db);
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
      }


    public Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/tradingSystem", "root", "saibaba18baba");
        return conn;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    // add user to db
    public boolean addUserToDB(String fname, String lname, String email, String password, String role) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO Users (fname, lname, email, password,role) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, role);
            stmt.executeUpdate();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        finally {
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }

    // check if a user in db
    public boolean checkUserInDB(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Users WHERE `email` = ? AND `password` = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                System.out.println("User not found.");
                return false;
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }

    // show all users in db
    public void showUsers() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Users");

            // Retrieve all users from the Users table
            rs = stmt.executeQuery();
            while (rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                System.out.println("First Name: " + fname + ", Email: " + email);
            }

        } finally {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }


    }

    // Method to check whether we have a Manager or not

    public boolean managerExists() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM Users WHERE role = ?");
            stmt.setString(1, "Manager");
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            } else {
                return false;
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }

    // getting the user role with an input of email
    public String getUserRole(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT role FROM Users WHERE email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                return role;
            } else {
                return null;
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }
    

    public void close() throws SQLException {
        conn.close();
    }
}