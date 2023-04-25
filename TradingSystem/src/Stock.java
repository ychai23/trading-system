// Import the necessary packages
import java.sql.*;
        import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Stock {

    private String name;
    private double price;

    private String symbol;

    private int ID;

    private boolean active;

    // track the ID of stock;
    private static int count = 0;



    public Stock(String name, String symbol ,double price){
        this.name = name;
        this.price = price;
        this.ID = ++count;
        this.symbol = symbol;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
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