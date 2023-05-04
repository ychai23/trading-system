import java.sql.*;

public class StockMarket {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/stockmarket";

    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            // Create table to store stocks
            String sql = "CREATE TABLE IF NOT EXISTS stocks " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " symbol VARCHAR(10), " +
                    " shares INTEGER, " +
                    " purchase_price DOUBLE, " +
                    " purchase_date DATE, " +
                    " PRIMARY KEY (id))";
            stmt.executeUpdate(sql);

            // Buy stocks
            buyStock(conn, "AAPL", 100, 150.0, "2021-01-01"); // Buy 100 shares of AAPL at $150 on 2021-01-01
            buyStock(conn, "AAPL", 50, 170.0, "2021-03-01"); // Buy 50 shares of AAPL at $170 on 2021-03-01

            // Sell stocks
            sellStock(conn, "AAPL", 120, 200.0); // Sell 120 shares of AAPL at $200, using FIFO principle

            // Print current holdings
            printHoldings(conn);

            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void buyStock(Connection conn, String symbol, int shares, double price, String date) throws SQLException {
        String sql = "INSERT INTO stocks (symbol, shares, purchase_price, purchase_date) " +
                "VALUES ('" + symbol + "', " + shares + ", " + price + ", '" + date + "')";
        conn.createStatement().executeUpdate(sql);
    }

    public static void sellStock(Connection conn, String symbol, int shares, double price) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM stocks WHERE symbol = '" + symbol + "' ORDER BY purchase_date ASC");
        double totalCostBasis = 0.0;
        int sharesSold = 0;

        while (rs.next() && sharesSold < shares) {
            int id = rs.getInt("id");
            int purchasedShares = rs.getInt("shares");
            double purchasePrice = rs.getDouble("purchase_price");
            totalCostBasis += Math.min(purchasedShares, shares - sharesSold) * purchasePrice;
            sharesSold += Math.min(purchasedShares, shares - sharesSold);

            if (purchasedShares <= shares - sharesSold) {
                conn.createStatement().executeUpdate("DELETE FROM stocks WHERE id = " + id);
            } else {
                conn.createStatement().executeUpdate("UPDATE stocks SET shares = " + (purchasedShares - (shares - sharesSold)) + " WHERE id = " + id);
            }
        }

        double proceeds = sharesSold * price;
        double profit = proceeds - totalCostBasis;

        System.out.println("Sold " + sharesSold + " shares of " + symbol + " at $" + price + ", resulting in a profit of $" + profit);
    }

    public static void printHoldings(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT symbol, SUM(shares) AS total_shares, AVG(purchase_price) AS average_purchase_price FROM stocks GROUP BY symbol");

        System.out.println("Current holdings:");
        while (rs.next()) {
            String symbol = rs.getString("symbol");
            int totalShares = rs.getInt("total_shares");
            double averagePurchasePrice = rs.getDouble("average_purchase_price");
            double totalCostBasis = totalShares * averagePurchasePrice;

            System.out.println(symbol + ": " + totalShares + " shares at $" + averagePurchasePrice + ", cost basis $" + totalCostBasis);
        }
    }
}