package designpattern;

public class Main {

    public static void main(String[] args) {

        SpecialMove sm = new SpecialMove();

        sm.record(() -> System.out.println("down"));

        sm.trigger();
    }
}
