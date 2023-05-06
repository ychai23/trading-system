/*  
Database class using Singleton pattern as there is only one connection the database being made
and class is accessed throughout application.

Class handles all database operations.
*/
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
            stmt = conn.prepareStatement("INSERT INTO Users (fname, lname, email, password, role, baseCash,deposit) VALUES (?, ?, ?, ?, ?,?,?)");
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, role);
            stmt.setDouble(6, 0.0);
            stmt.setDouble(7, 0.0);
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
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
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
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // set user isactive boolean to true and take user email
    public void setUserActive(String email, boolean active) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE Users SET isactive = ? WHERE email = ?");
            stmt.setBoolean(1, active);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
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
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
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
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
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
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    public void close() throws SQLException {
        conn.close();
    }

    // add stock to market
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

    // update stock price
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
    public boolean updateStockName(Stock updatedStock, String name) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE stocks SET name = ? WHERE symbol = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
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


    public boolean updateStockSymbol(Stock updatedStock, String symbol) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE stocks SET symbol = ? WHERE name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, symbol);
            stmt.setString(2, updatedStock.getName());
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

    // get market data
    public List<Stock> getMarketData() throws SQLException {
        List<Stock> marketData = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Stocks");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Stock stock = new Stock(rs.getString("name"), rs.getString("symbol"), rs.getDouble("price"));
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
    public List<User> getUsersData() throws SQLException {
        // create a list to hold all the user data
        List<User> userData = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
                if (rs.getBoolean("isactive") == true) {
                    user.setActive();
                } else {
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

    //get Stock from Stock ID
    public Stock getStockFromID(int stockid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Stocks WHERE stockid = ?");
            stmt.setInt(1, stockid);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Stock stock = new Stock(rs.getString("name"), rs.getString("symbol"), rs.getDouble("price"));
                stock.setID(rs.getInt("stockid"));
                stock.setSymbol(rs.getString("symbol"));
                stock.setActive(rs.getBoolean("isactive"));
                return stock;
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
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
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // block stock
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


    // Customer Methods

    // update password
    public boolean updatePW(int userid, String pw) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE Users SET password = ? WHERE userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pw);
            stmt.setInt(2, userid);
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

    // update email
    public boolean updateEmail(int userid, String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE Users SET email = ? WHERE userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setInt(2, userid);
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

    // getting the user role with an input of email
    public Customer getCustomer(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Users WHERE `email` = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {

                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String password = rs.getString("password");
                String role = rs.getString("role");
                double baseCash = rs.getDouble("baseCash");
                double deposit = rs.getDouble("deposit");

                return new Customer(fname, lname, email, password, role, baseCash, deposit);
            } else {
                System.out.println("User not found.");
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // get basCash
    public double getBaseCash(int customerid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT baseCash FROM Users WHERE userid = ?");
            stmt.setInt(1, customerid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                double baseCash = rs.getDouble("baseCash");
                return baseCash;
            } else {
                return -1;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // get basCash
    public int getUserStatus(int customerid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT isactive FROM Users WHERE userid = ?");
            stmt.setInt(1, customerid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int isactive = rs.getInt("isactive");
                return isactive;
            } else {
                return -1;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    // get owned stock data
    public List<Stock> getOwnedStocks(int userid) throws SQLException {
        List<Stock> owned = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT s.* FROM userStock us JOIN Stocks s ON us.stockid = s.stockid WHERE us.userid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Stock stock = new Stock(rs.getString("name"), rs.getString("symbol"), rs.getDouble("price"));
                stock.setID(rs.getInt("stockid"));
                stock.setActive(rs.getBoolean("isactive"));
                owned.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owned;
    }


    // update deposit
    public boolean setCustomerDeposit(int userid, double newDeposit) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE Users SET deposit = ? WHERE userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newDeposit);
            stmt.setInt(2, userid);
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

    // update basemoney
    public boolean updateBase(double newBaseCash, int userid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE Users SET baseCash = ? WHERE userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newBaseCash);
            stmt.setInt(2, userid);
            System.out.println(userid);
            stmt.executeUpdate();
            System.out.println("ADDED TO DB");
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


    public HashMap<Integer, Integer> getUserStockQuantity(int userid) throws SQLException {
        HashMap<Integer, Integer> stockIDQuantity = new HashMap<Integer, Integer>();
        try {
            Connection conn = getConnection();

            String sql = "SELECT * FROM userStock  WHERE userid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int stockid = rs.getInt("stockid");
                int currentQuantity = stockIDQuantity.getOrDefault(stockid, 0);
                stockIDQuantity.put(stockid, currentQuantity + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockIDQuantity;
    }

    // get stock price by id
    public double getCurrentStockPrice(int stockid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT price FROM Stocks WHERE stockid = ?");
            stmt.setInt(1, stockid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                double stockPrice = rs.getInt("price");
                return stockPrice;
            } else {
                return -1;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    // get balance for a single stock
    public double getBalance(int userid, int stockid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT balance FROM userStock WHERE stockid = ? and userid = ?");
            stmt.setInt(1, stockid);
            stmt.setInt(2, userid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getInt("balance");
                return balance;
            } else {
                return -1;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    //get Stock from Stock ID and userID
    public boolean checkStock(int userid, int stockid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM userStock WHERE stockid = ? AND userid = ?");
            stmt.setInt(1, stockid);
            stmt.setInt(2, userid);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // buy stock
    public double buyStock(int userid, int stockID, int buyQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        double stockPrice = getCurrentStockPrice(stockID);
        double spent = stockPrice * buyQuantity;

        try {
            conn = getConnection();
            if (checkStock(userid, stockID)) {
                // if stock in userStock, then just change the balance and quantity
                String sql = "UPDATE userStock SET quantity = quantity + ?, balance = balance - ? WHERE stockid = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, buyQuantity);
                stmt.setDouble(2, spent);
                stmt.setInt(3, stockID);
                stmt.executeUpdate();
            } else {
                // add stock to userStock
                conn = getConnection();
                stmt = conn.prepareStatement("INSERT INTO userStock (userid, stockid, balance, quantity) VALUES (?, ?, ?, ?)");
                stmt.setInt(1, userid);
                stmt.setInt(2, stockID);
                stmt.setDouble(3, -spent);
                stmt.setDouble(4, buyQuantity);
                stmt.executeUpdate();
            }

            // update baseCash
            String sql = "UPDATE users SET baseCash=baseCash - ? WHERE userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, spent);
            stmt.setInt(2, userid);
            stmt.executeUpdate();

            //update userStocks
            String sql2 = "INSERT INTO userStocks(userid, stockid, quantity, purchase_price) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql2);
            stmt.setInt(1, userid);
            stmt.setInt(2, stockID);
            stmt.setInt(3, buyQuantity);
            stmt.setDouble(4, stockPrice);
            stmt.executeUpdate();

            return spent;

        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // sell stock
    public double sellStock(int userid, int stockID, int sellQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        double sellPrice = getCurrentStockPrice(stockID);
        double earning = sellPrice * sellQuantity;

        try {
            // change quantity and balance
            conn = getConnection();
            String sql = "UPDATE userStock SET quantity=quantity - ?, balance=balance + ? WHERE stockid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, sellQuantity);
            stmt.setDouble(2, earning);
            stmt.setInt(3, stockID);
            stmt.executeUpdate();


            //update userStocks
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userStocks WHERE userid =" + userid + " AND stockid = " + stockID + " ORDER BY purchase_order ASC");
            double totalCostBasis = 0.0;
            int sharesSold = 0;

            while (rs.next() && sharesSold < sellQuantity) {
                int purchaseOrder = rs.getInt("purchase_order");
                int purchasedShares = rs.getInt("quantity");
                double purchasePrice = rs.getDouble("purchase_price");
                int sellingQuantity = Math.min(purchasedShares, sellQuantity - sharesSold);
                totalCostBasis += sellingQuantity * purchasePrice;
                sharesSold += sellingQuantity;

                if (purchasedShares == sellingQuantity) {
                    conn.createStatement().executeUpdate("DELETE FROM userStocks WHERE purchase_order =" + purchaseOrder);

                } else {
                    conn.createStatement().executeUpdate("UPDATE userStocks SET quantity = " + (purchasedShares - sellingQuantity) + " WHERE purchase_order = " + purchaseOrder);
                }
            }

//            double proceeds = sharesSold * sellPrice;
//            double profit = proceeds - totalCostBasis;


            // update baseCash
            sql = "UPDATE users SET baseCash=baseCash + ? WHERE userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, earning);
            stmt.setInt(2, userid);
            stmt.executeUpdate();

            return earning;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public HashMap<Integer, Double> getUnrealizedProfit(int userid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        List<Stock> stockList = getOwnedStocks(userid);
        HashMap<Integer, Double> stockUnrealizedProfit = new HashMap<>();


        try {
            // change quantity and balance
            conn = getConnection();

            for (Stock stock : stockList) {
                int stockID = stock.getID();
                ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM userStocks WHERE userid =" + userid + " AND stockid = " + stockID + " ORDER BY purchase_order ASC");
                double currentPrice = getCurrentStockPrice(stockID);
                double totalProfit = 0.0;

                while (rs.next()) {
                    int purchasedShares = rs.getInt("quantity");
                    double purchasePrice = rs.getDouble("purchase_price");
                    System.out.println("para" + purchasedShares + currentPrice + purchasePrice);
                    totalProfit += purchasedShares * (currentPrice - purchasePrice);
                }

                stockUnrealizedProfit.put(stockID, totalProfit);
            }

            return stockUnrealizedProfit;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }
}
