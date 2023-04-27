import java.util.List;

public interface CustomerServiceInterface {
    public boolean buyStock();
    public boolean sellStock();
    public boolean withdraw(double amount);
    public boolean deposit(double amount);
    // get market data
    public List<Stock> getMarketData();
    //get owned stock data
    public List<Stock> getOwnedStocks(int userid);
    // check if a user in db
}
