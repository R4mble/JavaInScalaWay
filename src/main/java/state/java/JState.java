package state.java;

class AS<A, S> {
    A a;
    S s;
}

interface Run<S, A> {
    AS run(S s);
}

interface F<A, B, S> {
    JState<S, B> f(A a);
}


public class JState<S, A> {
    private Run run;

    public JState(Run run) {
        this.run = run;
    }

    public JState flatMap(F f) {
        return new JState(s -> {
            AS as = run.run(s);
            return f.f(as.a).run.run(as.s);
        });
    }

    public JState unit(A a) {
        return new JState(s -> new AS());
    }
}
