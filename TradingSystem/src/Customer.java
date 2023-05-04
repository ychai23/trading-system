public class Customer extends User {

    private double baseCash;
    private double deposit;
    private double realizedProfit;
    private double unrealizedProfit;

    public Customer(String fname, String lname, String email, String password, String role, double baseCash, double deposit) {
        super(fname, lname, email, password, role);
        this.baseCash = baseCash;
        this.deposit = deposit;
        this.realizedProfit = getRealizedProfit();
        //this.unrealizedProfit = getUnrealizedProfit();
    }
    public double getRealizedProfit(){
        return this.baseCash - this.deposit;
    }
    public double getBaseCash() {return this.baseCash; }

    public boolean buyStock(){
        if (this.baseCash <= 200){
            System.out.println("you cant buy stock due to your low credit score.");
        }
        return true;
    }

    public boolean sellStock(){
        return true;
    }

    public double desposit(double amount){
        this.baseCash += amount;
        System.out.println("You desposited " + amount + " into your account.");
        return this.baseCash;
    }

    public double withdraw(double amount){
        this.baseCash -= amount;
        System.out.println("You withdrew " + amount + " from your account.");
        return this.baseCash;
    }
}
