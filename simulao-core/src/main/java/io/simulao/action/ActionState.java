package io.simulao.action;

public class ActionState {

    private Exception exception = null;
    private Status status = Status.PENDING;

    public String getStatus() {
        return status.status;
    }

    public void start() {
        this.status = Status.RUNNING;
    }

    public void complete() {
        this.status = Status.COMPLETE;
    }

    public void failed(Exception e) {
        this.exception = e;
        this.status = Status.FAILED;
    }

    public String getDescription() {
        String message = getStatus();

        if (status == Status.FAILED && exception != null) {
            message = String.format("%s - %s", message, exception.getMessage());
        }

        return message;
    }

    public static ActionState none() {
        ActionState state = new ActionState();
        state.status = Status.NONE;
        return state;
    }

    enum Status {

        NONE("No State"),
        PENDING("Pending"),
        RUNNING("Running"),
        COMPLETE("Complete"),
        FAILED("Failed"),

        ;

        private final String status;

        Status(String status) {
            this.status = status;
        }

    }

}
