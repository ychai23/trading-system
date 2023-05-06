import java.sql.SQLException;
import java.util.List;

public class ManagerService implements ManagerServiceInterface{
    Database db;
    private static ManagerService instance = null;
    public ManagerService(Database db){
        this.db = db;
    }

    public static ManagerService getInstance() {
        if (instance == null) {
            instance = new ManagerService(Database.getInstance());
        }
        return instance;
    }
    // add user to db
    public boolean addUserToDB(String fname, String lname, String email, String password, String role){
        try {
            return this.db.addUserToDB(fname, lname, email, password, role);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // make User object from database based on input userid
    public User getUserFromID(int userid){
        try {
            return this.db.getUserFromID(userid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // get user.id from email
    public int getUserID(String email){
        try {
            return this.db.getUserID(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // getUserData
    public List<User> getUserData(){
        try{
            return this.db.getUsersData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // set user isactive boolean to true and take user email
    public void setUserActive(String email,boolean active){
        try {
            this.db.setUserActive(email, active);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to check whether we have a Manager or not
    public boolean managerExists(){
        try {
            return this.db.managerExists();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // add stock to market
    public boolean addStockToMarket(Stock stock){
        try {
            return this.db.addStockToMarket(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // update stock price
    public boolean updateStockPrice(Stock updatedStock, double price){
        try {
            return this.db.updateStockPrice(updatedStock, price);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // get Stock Id from Symbol
    public int getStockId(String symbol){
        try {
            return this.db.getStockId(symbol);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // block stock
    public boolean blockStock(String symbol){
        try {
            return this.db.blockStock(symbol);
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
    public Stock getStockFromID(int stockid){
        try {
            return this.db.getStockFromID(stockid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStockName(Stock updatedStock, String name){
        try {
            return this.db.updateStockName(updatedStock, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateStockSymbol(Stock updatedStock, String symbol){
        try {
            return this.db.updateStockSymbol(updatedStock, symbol);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
