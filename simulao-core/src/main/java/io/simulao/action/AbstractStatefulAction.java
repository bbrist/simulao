package io.simulao.action;

public abstract class AbstractStatefulAction implements StatefulAction {

    private ActionState state = new ActionState();

    abstract void doPerform() throws Exception;

    @Override
    public ActionState getActionState() {
        return state;
    }

    @Override
    public void perform() {
        try {
            state.start();
            doPerform();
            state.complete();
        } catch (Exception e) {
            state.failed(e);
        }
    }

}
