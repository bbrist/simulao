package io.simulao.action;

import io.simulao.Action;
import io.simulao.Reportable;

public class ActionStateWrapper implements StatefulAction {

    private Action action;

    private final State state = new State();

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

    static class State implements ActionState {

        private Exception exception = null;
        private ActionStatus status = ActionStatus.PENDING;

        public void start() {
            this.status = ActionStatus.RUNNING;
        }

        public void complete() {
            this.status = ActionStatus.COMPLETE;
        }

        public void failed(Exception e) {
            this.exception = e;
            this.status = ActionStatus.FAILED;
        }

        public ActionStatus getStatus() {
            return status;
        }

        public String getMessage() {
            String message = status.status();

            if (status == ActionStatus.FAILED && exception != null) {
                message = String.format("%s - %s", message, exception.getMessage());
            }

            return message;
        }

    }

}
