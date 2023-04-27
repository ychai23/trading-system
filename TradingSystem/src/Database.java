/*  
Database class using Singleton pattern as there is only one connection the database being made
and class is accessed throughout application.

Class handles all database operations.
*/
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    // get user.id from email
    public int getUserID(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT userid FROM Users WHERE `email` = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("userid");
            } else {
                System.out.println("User not found.");
                return -1;
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }
    // make User object from database based on input userid
    public User getUserFromID(int userid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Users WHERE `userid` = ?");
            stmt.setInt(1, userid);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                return new User(fname, lname, email, password, role);
            } else {
                System.out.println("User not found.");
                return null;
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }
    // set user isactive boolean to true and take user email
    public void setUserActive(String email,boolean active) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE Users SET isactive = ? WHERE email = ?");
            stmt.setBoolean(1, active);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } finally {
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
    public boolean addStockToMarket(Stock stock) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO Stocks (name, symbol, price) VALUES (?,?, ?)");
            stmt.setString(1, stock.getName());
            stmt.setString(2, stock.getSymbol());
            stmt.setDouble(3, stock.getPrice());
            stmt.executeUpdate();
            stock.setID(getStockId(stock.getSymbol()));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean updateStockPrice(Stock updatedStock, double price) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE stocks SET price = ? WHERE symbol = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, updatedStock.getPrice());
            stmt.setString(2, updatedStock.getSymbol());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }


    }

    public List<Stock> getMarketData() throws SQLException {
        List<Stock> marketData = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Stocks");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Stock stock = new Stock(rs.getString("name"), rs.getString("symbol"),rs.getDouble("price"));
                stock.setID(rs.getInt("stockid"));
                stock.setSymbol(rs.getString("symbol"));
                stock.setActive(rs.getBoolean("isactive"));
                marketData.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marketData;
    }
    // getUserData
    public List<User> getUserData() throws SQLException {
        // create a list to hold all the user data
        List<User> userData = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users");
        ResultSet rs = stmt.executeQuery()) {
       while (rs.next()) {
           User user = new User(rs.getString("fname"), rs.getString("lname"),rs.getString("email"),rs.getString("password"),rs.getString("role"));
           if(rs.getBoolean("isactive") == true){
               user.setActive();
           }
           else{
               user.setInactive();
           }
           userData.add(user);
       }
   } catch (SQLException e) {
       e.printStackTrace();
   }
   return userData;
    }

    public void deleteTableData(String tableName) throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM " + tableName; // SQL query to delete all data from the table
            stmt.executeUpdate(sql); // execute the SQL query
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get Stock Id from Symbol
    public int getStockId(String symbol) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT stockid FROM Stocks WHERE symbol = ?");
            stmt.setString(1, symbol);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                int stockid = rs.getInt("stockid");
                return stockid;
            } else {
                return -1;
            }
        } finally {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        }
    }

   public boolean blockStock(String symbol) throws SQLException {
       Connection conn = null;
       PreparedStatement stmt = null;

       try {
           conn = getConnection();
           String sql = "UPDATE stocks SET isactive = ? WHERE symbol = ?";
           stmt = conn.prepareStatement(sql);
           stmt.setBoolean(1, false);
           stmt.setString(2, symbol);
           stmt.executeUpdate();
           return true;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       } finally {
           if (stmt != null) {
               stmt.close();
           }
           if (conn != null) {
               conn.close();
           }
       }
   }
}