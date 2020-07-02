package io.simulao.action;

public enum ActionStatus {

    NONE("No State"),
    PENDING("Pending"),
    RUNNING("Running"),
    COMPLETE("Complete"),
    FAILED("Failed"),

    ;

    private final String status;

    ActionStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }

}