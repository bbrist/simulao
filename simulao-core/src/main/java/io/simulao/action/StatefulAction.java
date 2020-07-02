package io.simulao.action;

public interface StatefulAction extends ReportableAction {

    ActionState getActionState();

}
