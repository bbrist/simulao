package io.simulao.executor;

import io.simulao.*;

import java.util.Set;

public class BasicScenarioExecutor implements ScenarioExecutor {

    public Report execute(Scenario scenario) {
        BasicReport report = new BasicReport();

        Set<Action> actions = scenario.generate();
        for(Action action : actions) {
            performAndRecord(action, report);
        }

        return report;
    }

    protected void performAndRecord(Action action, Report report) {
        Reportable r = getReportable(action);
        try {
            perform(action);
            report.record(r);
        } catch (Exception e) {
            report.record(r, e);
        }
    }

    protected void perform(Action action) throws Exception {
        action.perform();
    }

    protected Reportable getReportable(Action action) {
        if (action instanceof Reportable) {
            return (Reportable) action;
        } else {
            return new DefaultReportable(action);
        }
    }

    static class DefaultReportable implements Reportable {

        private Action action;

        public DefaultReportable(Action action) {
            this.action = action;
        }

        public String getName() {
            return action.getClass().getName();
        }

    }

}
