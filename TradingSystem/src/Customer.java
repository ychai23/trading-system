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
    public void updatebaseCash(double money) { this.baseCash += money; }
    public double getbaseCash() { return this.baseCash; }

    public double desposit(double amount){
        this.baseCash += amount;
        this.deposit += amount;
        System.out.println("You desposited " + amount + " into your account.");
        return this.baseCash;
    }

    public double withdraw(double amount){
        this.baseCash -= amount;
        this.deposit -= amount;
        System.out.println("You withdrew " + amount + " from your account.");
        return this.baseCash;
    }
}
