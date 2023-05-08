
// Manager class allows to create a reference to the only manager when the manager logs in
public class Manager extends User {
    public Manager(String fname, String lname, String email, String password, String role) {
        super(fname, lname, email, password, role);
    }
}
