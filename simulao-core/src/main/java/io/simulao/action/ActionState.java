package io.simulao.action;

public interface ActionState {

    ActionStatus getStatus();
    String getMessage();

    static ActionState none() {
        return new ActionState() {
            @Override
            public ActionStatus getStatus() {
                return ActionStatus.NONE;
            }

            @Override
            public String getMessage() {
                return "No Status";
            }
        };
    }

}
