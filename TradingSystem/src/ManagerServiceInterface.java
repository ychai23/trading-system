public interface ManagerServiceInterface {
    // add user to db
    public boolean addUserToDB(String fname, String lname, String email, String password, String role);
    // make User object from database based on input userid
    public User getUserFromID(int userid);
    // get user.id from email
    public int getUserID(String email);
    // set user isactive boolean to true and take user email
    public void setUserActive(String email,boolean active);
    // Method to check whether we have a Manager or not
    public boolean managerExists();
    // add stock to market
    public boolean addStockToMarket(Stock stock);
    // update stock price
    public boolean updateStockPrice(Stock updatedStock, double price);
    // get Stock Id from Symbol
    public int getStockId(String symbol);
    // block stock
    public boolean blockStock(String symbol);
    // get market data

    //get stock from id
    public Stock getStockFromID(int stockid);

}
