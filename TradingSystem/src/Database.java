import java.sql.*;
public class Database {
    private Connection conn;

    public Database(String url, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
    }
    public Connection getConnection() {
        return conn;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
    public void addUser(String fname, String lname, String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO Users (fname, lname, email, password) VALUES (?, ?, ?, ?)");
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }
    public boolean checkUser(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Users WHERE `email` = ? AND `password` = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
        } finally {
            if (stmt != null) {
                stmt.close();
                return true;
            } else {
                if (conn != null) {
                    conn.close();
                } else {
                    System.out.println("Connection to db failed.");
                    return false;
                }
                System.out.println("User not found.");
                return false;
            }
        }
    }
    public void showUsers(){
        try {
            Statement stmt = conn.createStatement();

            // Add a new user to the Users table


            // Retrieve all users from the Users table
            String sql = "SELECT * FROM Users";
            ResultSet rs = this.executeQuery(sql);
            while (rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                System.out.println("Name: " + fname + ", Email: " + email);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void close() throws SQLException {
        conn.close();
    }
}