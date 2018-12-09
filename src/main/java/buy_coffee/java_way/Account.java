package buy_coffee.java_way;

public class Account {
    Coffee coffee;
    Charge charge;


    public Account(Coffee coffee, Charge charge) {
        this.coffee = coffee;
        this.charge = charge;
    }

    @Override
    public String toString() {
        return this.coffee.toString() + ", " + this.charge.toString();
    }
}
