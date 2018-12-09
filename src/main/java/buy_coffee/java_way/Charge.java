package buy_coffee.java_way;

public class Charge {
    public CreditCard creditCard;
    public Double amount;

    public Charge(CreditCard creditCard, Double amount) {
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public Charge combine(Charge other) {
        if (other.creditCard.name.equals(this.creditCard.name)) {
            return new Charge(creditCard, other.amount + this.amount);
        } else {
            System.out.println("can't combine charges to different credit card");
        }
        return null;
    }

    @Override
    public String toString() {
        return creditCard.name + " cost " + amount;
    }
}
