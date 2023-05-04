import java.sql.SQLException;
import java.util.List;

public class CustomerService implements CustomerServiceInterface{
    Database db;
    Customer c;
    public CustomerService(Database db, Customer c){
        this.db = db;
        this.c = c;
    }
    public Customer getCustomer(){
        return this.c;
    }
    public double getBalance(){
        try {
            return this.db.getBaseCash(this.c.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean buyStock(int stockid, int quantity) throws SQLException {
        double totalCost = this.db.getCurrentStockPrice(stockid) * quantity;
        int userid = this.c.getId();
        double currentCash = this.db.getBaseCash(userid);
        if (this.db.getUserStatus(userid) == 0){
            System.out.println("Your account have been locked");
            return false;
        }
        if (totalCost > currentCash){
            System.out.println("You don't have enough money");
            return false;
        }
        this.db.buyStock(this.c.getId(), stockid, quantity);
        System.out.println("User " + userid + " successfully purchased " + quantity + " shares of stock " + stockid);
        return true;
    }
    @Override
    public boolean sellStock(int stockid, int quantity)throws SQLException{
        int userid = this.c.getId();
        int currentQuantity = this.db.getUserStockQuantity(userid).get(stockid);
        if (currentQuantity < quantity){
            System.out.println("You don't have enough stocks");
            return false;
        }
        this.db.sellStock(userid,stockid,quantity);
        System.out.println("User " + userid + " successfully sold " + quantity + " shares of stock " + stockid);
        return true;
    }

    public boolean withdraw(double amount){
        double newBaseCash = this.c.withdraw(amount);
        try {
            return (this.db.updateBase(newBaseCash, this.c.getId()) && this.db.setCustomerDeposit(this.c.getId(), newBaseCash));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deposit(double amount){
        double newBaseCash = this.c.desposit(amount);
        try {
            return (this.db.updateBase(newBaseCash, this.c.getId()) && this.db.setCustomerDeposit(this.c.getId(), newBaseCash));
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

    public double getBaseCash(int userid){
        try {
            return this.db.getBaseCash(userid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
