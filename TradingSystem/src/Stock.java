import java.sql.SQLException;

// Stock class allows to create a reference to a sepcific stock in the market
public class Stock {

    private String name;
    private double price;

    private String symbol;

    private int ID;

    private boolean active;
    private Database db;

    public Stock(String name, String symbol ,double price){
        this.db = Database.getInstance();
        this.name = name;
        this.price = price;
        this.symbol = symbol;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActive() {
        try {
            return db.getStockStatus(this.symbol);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getIDString() {
        return String.valueOf(ID);
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int id){
        this.ID = id;
    }

    public String getPriceString(){
        return String.valueOf(this.price);
    }

    public String getSymbol(){
        return this.symbol;
    }
}