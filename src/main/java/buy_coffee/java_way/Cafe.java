package buy_coffee.java_way;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Cafe {

    public Account buyCoffee(CreditCard cc) {
        Coffee cup = new Coffee(5);
        return new Account(cup, new Charge(cc, cup.price));
    }

    public Accounts buyCoffee(CreditCard cc, int n) {
        List<Coffee> coffees = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            coffees.add(new Coffee(5));
        }
        return new Accounts(coffees, new Charge(cc, coffees.stream().mapToDouble(c -> c.price).sum()));
    }

    //a hard way to complete divide, reduce and collect
    public List<Charge> coalesce(List<Charge> charges) {
         return charges.stream().collect(Collectors.groupingBy(c -> c.creditCard.name)).values().stream()
                 .map(c -> c.stream().reduce(Charge::combine).orElse(null)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Cafe c = new Cafe();
        Account cofe1 = c.buyCoffee(new CreditCard("wyl"));
        Accounts cofe2 = c.buyCoffee(new CreditCard("wyl"), 2);
        Accounts cofe3 = c.buyCoffee(new CreditCard("ramble"), 5);
        Accounts cofe4 = c.buyCoffee(new CreditCard("wang"), 31);
        Accounts cofe5 = c.buyCoffee(new CreditCard("lisi"), 134);

        List<Charge> charges = c.coalesce(Arrays.asList(cofe1.charge, cofe2.charge,
                cofe3.charge, cofe4.charge, cofe5.charge));

        System.out.println(cofe1);
        System.out.println(cofe2);
        System.out.println(cofe3);
        System.out.println(charges);
    }
}
