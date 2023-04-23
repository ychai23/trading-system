import java.sql.*;

public class User {
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String role;

    public User(String fname, String lname,String email, String password, String role) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
     // Getters
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    // Setters
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
    // Add User to Database
    public boolean addUser(){
        Database db = Database.getInstance();
        try {
            if(db.addUserToDB(fname, lname, email, password, role)){
                System.out.println("User added to database!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding User to database: " + e.getMessage());
            return false;
        }
        return false;
    }


}
