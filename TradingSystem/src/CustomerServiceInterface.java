import java.sql.SQLException;
import java.util.List;

/*
* CustomerService Interface lists out all the operations a customer can perform
*/
public interface CustomerServiceInterface {
    // buy stock - sepcified by stockid and quantity
    public boolean buyStock(int stockid, int quantity) throws SQLException;
    // sell stock - sepcified by stockid owned by customer and quantity
    public boolean sellStock(int stockid, int quantity) throws SQLException;
    // withdraw money from account
    public boolean withdraw(double amount);
    // deposit money from account
    public boolean deposit(double amount);
    // get most updated market data
    public List<Stock> getMarketData();
    // get owned stock data
    public List<Stock> getOwnedStocks(int userid);
}
