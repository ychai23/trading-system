import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            Database db = new Database("jdbc:mysql://localhost/tradingSystem", "root", "saibaba18baba");
            TradingSystemGUI gui = new TradingSystemGUI(db);
            gui.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}