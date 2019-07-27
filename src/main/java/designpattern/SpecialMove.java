package designpattern;

import java.util.LinkedList;

public class SpecialMove {

    LinkedList<Action> actions = new LinkedList<>();

    public void record(Action action) {
        if (actions.size() == 4) {
            actions.removeFirst();
        }
        actions.add(action);
    }

    public void trigger() {
        actions.forEach(Action::action);
    }
}
