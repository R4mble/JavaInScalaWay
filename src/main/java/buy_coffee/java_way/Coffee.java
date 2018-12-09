package buy_coffee.java_way;

public class Coffee {

    public double price;

    public Coffee(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "coffee cost " + price;
    }
}
