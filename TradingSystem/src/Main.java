import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            Database db = new Database("jdbc:mysql://localhost/tradingSystem", "root", "jctheboi");
//            TradingSystemGUI gui = new TradingSystemGUI(db);
            UserLoginPage login  = new UserLoginPage(db);
            login.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}