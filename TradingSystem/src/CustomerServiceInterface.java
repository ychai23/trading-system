import java.sql.SQLException;
import java.util.List;

/*
* CustomerService Interface lists out all the operations a customer can perform
*/
public interface CustomerServiceInterface {
    public boolean buyStock(int stockid, int quantity) throws SQLException, NoDataFoundException;
    public boolean sellStock(int stockid, int quantity) throws SQLException, NoDataFoundException;
    public boolean withdraw(double amount);
    // deposit money from account
    public boolean deposit(double amount);
    // get most updated market data
    public List<Stock> getMarketData();
    // get owned stock data
    public List<Stock> getOwnedStocks(int userid);
}
