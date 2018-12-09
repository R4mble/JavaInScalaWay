package buy_coffee.java_way;

import java.util.List;

public class Accounts {
    List<Coffee> coffees;
    Charge charge;


    public Accounts(List<Coffee> coffees, Charge charge) {
        this.coffees = coffees;
        this.charge = charge;
    }

    @Override
    public String toString() {
        return this.coffees.toString() + ", " + this.charge.toString();
    }
}
