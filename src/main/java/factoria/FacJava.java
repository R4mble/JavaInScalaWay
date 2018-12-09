package factoria;

public class FacJava {

    //I've wrote this code in SICP
    //I learned the difference between recursion and iteration

    //iteration : the best
    int fib(int n) {
        return go(0, 1, n);
    }

    int go(int f, int l, int n) {
        if (n == 0) return f;
        return go(l, f + l, n - 1);
    }

    //recursion: worse than iteration, because it generated tree-shaped calculate procedure
    // and a lot of repeat calculate.
    int fib_worst(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return fib_worst(n - 1) + fib_worst(n - 2);
        }
    }


    //iteration
    int fac(int n) {
        return loop(n, 1);
    }

    private int loop(int n, int acc) {
        if (n <= 0) return acc;
        return loop(n - 1, n * acc);
    }

    //recursion : not worse than iteration
    int fac_worst(int n) {
        if (n <= 1) return n;
        return n * fac_worst(n - 1);
    }

    public static void main(String[] args) {
        int x = 34;
        System.out.println(new FacJava().fib(x));
        System.out.println(new FacJava().fib_worst(x));

        System.out.println(new FacJava().fac(x));
        System.out.println(new FacJava().fac_worst(x));
    }
}
