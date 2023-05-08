import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class CustomerService implements CustomerServiceInterface{
    Database db;
    Customer c;

    private int userid;
    public CustomerService(Database db, Customer c){
        this.db = db;
        this.c = c;
        this.userid = c.getId();
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

    public boolean updateEmail(String email){
        try {
            boolean success = this.db.updateEmail(this.c.getId(), email);
            if (success) {
                this.c.setEmail(email);
            }
            return success;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePW(String pw){
        try {
            boolean success = this.db.updatePW(this.c.getId(), pw);
            if (success) {
                this.c.setPassword(pw);
            }
            return success;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean buyStock(int stockid, int quantity) throws SQLException, NoDataFoundException {
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
        if (!this.db.getStockFromID(stockid).isActive()){
            return false;
        }
        double spending = this.db.buyStock(userid, stockid, quantity);
        if (spending != 1){
            this.c.updatebaseCash(-spending);
        }
        System.out.println("User " + userid + " successfully purchased " + quantity + " shares of stock " + stockid);
        return true;
    }

    public boolean sellStock(int stockid, int quantity) throws SQLException, NoDataFoundException{
        int userid = this.c.getId();
        int currentQuantity = this.db.getUserStockQuantity(userid).getOrDefault(stockid, 0);
        if (currentQuantity < quantity){
            System.out.println("You don't have enough stocks of id" + stockid);
            return false;
        }
        double earning = this.db.sellStock(userid, stockid, quantity);
        if (earning != 0){
            this.c.updatebaseCash(earning);
        }
        System.out.println("User " + userid + " successfully sold " + quantity + " shares of stock " + stockid);
        return true;
    }

    public boolean withdraw(double amount){
        if (amount > getBalance()){
            return false;
        }
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

//    public double getUnrealizedProfit() throws SQLException {
//        List<Stock> stockList = getOwnedStocks(this.userid);
//        HashMap<Integer, Integer> stockQuantity = this.db.getUserStockQuantity(this.userid);
//
//        double unrealizedProfit = 0.0;
//        for (Stock stock : stockList){
//            int stockid = stock.getID();
//            unrealizedProfit += stockQuantity.get(stockid) * this.db.getCurrentStockPrice(stockid);
//            unrealizedProfit += this.db.getBalance(userid, stockid);
//        }
//        return unrealizedProfit;
//
//    }

    HashMap<Integer, Double> stockUnrealizedProfit(int userid) throws SQLException {
        return this.db.getUnrealizedProfit(userid);
    }

    public double totalUnrealizedProfit(int userid) throws SQLException{
        HashMap<Integer, Double> stockProfits = stockUnrealizedProfit(userid);
        double totalProfit = 0.0;
        for (double profit : stockProfits.values()){
            System.out.println("profit" + profit);
            totalProfit += profit;
        }

        System.out.println("total profit" + totalProfit);
        return totalProfit;
    }
}
