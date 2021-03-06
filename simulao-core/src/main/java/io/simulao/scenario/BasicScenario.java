package io.simulao.scenario;

import io.simulao.Action;
import io.simulao.Scenario;

import java.util.HashSet;
import java.util.Set;

public class BasicScenario implements Scenario {

    private Set<Action> actions = new HashSet<>();

    public void addAction(Action action) {
        this.actions.add(action);
    }

    @Override
    public Set<Action> generate() {
        return new HashSet<>(actions);
    }

}
