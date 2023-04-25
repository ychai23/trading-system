public class Customer extends User {
    protected int creditScore;
    protected int baseCash;
    protected double realizedProfit;
    protected double unrealizedProfit;

    public Customer(String fname, String lname, String email, String password, String role) {
        super(fname, lname, email, password, role);
        this.creditScore = 0;
        this.baseCash = 0;
        this.realizedProfit = 0;
        this.unrealizedProfit = 0;
    }

    public boolean buyStock(){
        if (this.baseCash <= 200){
            System.out.println("you cant buy stock due to your low credit score.");
        }
        return true;
    }

    public boolean sellStock(){
        return true;
    }

    public boolean desposit(double amount){
        this.baseCash += amount;
        System.out.println("You desposited " + amount + " into your account.");
        return true;
    }

    public boolean withdraw(double amount){
        this.baseCash -= amount;
        System.out.println("You withdrew " + amount + " from your account.");
        return true;
    }
}
