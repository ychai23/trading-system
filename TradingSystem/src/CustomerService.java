import java.sql.SQLException;
import java.util.List;

public class CustomerService implements CustomerServiceInterface{
    Database db;
    Customer c;
    public CustomerService(Database db, Customer c){
        this.db = db;
        this.c = c;
    }
    public boolean buyStock() {
        return true;
    }
    public boolean sellStock(){
        return true;
    }
    public boolean withdraw(double amount){
        double newBaseCash = this.c.withdraw(amount);
        try {
            return this.db.updateBase(newBaseCash, this.c.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deposit(double amount){
        double newBaseCash = this.c.desposit(amount);
        try {
            return this.db.updateBase(newBaseCash, this.c.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Stock> getMarketData(){
        try {
            return this.db.getMarketData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //get owned stock data
    public List<Stock> getOwnedStocks(int userid){
        try {
            return this.db.getOwnedStocks(userid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
