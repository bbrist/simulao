package io.simulao.action;

import io.simulao.Action;
import io.simulao.Reportable;

public class ActionStateWrapper implements StatefulAction {

    private Action action;

    private final ActionState state = new ActionState();

    public ActionStateWrapper(Action action) {
        this.action = action;
    }

    @Override
    public ActionState getActionState() {
        return state;
    }

    @Override
    public void perform() {
        try {
            state.start();
            action.perform();
            state.complete();
        } catch (Exception e) {
            state.failed(e);
        }
    }

    @Override
    public String getName() {
        if (action instanceof Reportable) {
            return ((Reportable) action).getName();
        }
        return String.format("Unnamed %s", action.getClass().getName());
    }

}
