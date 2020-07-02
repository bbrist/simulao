package io.simulao.executor;

import io.simulao.*;
import io.simulao.report.BasicReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.function.Supplier;

public class BasicScenarioExecutor implements ScenarioExecutor {

    private Logger log = LoggerFactory.getLogger(BasicScenarioExecutor.class);

    private Supplier<Report> reportSupplier;

    public BasicScenarioExecutor() {
        this(BasicReport::new);
    }

    public BasicScenarioExecutor(Supplier<Report> reportSupplier) {
        this.reportSupplier = reportSupplier;
    }

    public Report execute(Scenario scenario) {
        Report report = reportSupplier.get();

        Set<Action> actions = scenario.generate();
        for(Action action : actions) {
            performAndRecord(action, report);
        }

        return report;
    }

    protected void performAndRecord(Action action, Report report) {
        Reportable r = getReportable(action);
        try {
            log.trace("Performing Action: {}", r.getName());
            perform(action);

            log.trace("Reporting Action: {}", r.getName());
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
