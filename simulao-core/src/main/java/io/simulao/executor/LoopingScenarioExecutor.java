package io.simulao.executor;

import io.simulao.Report;
import io.simulao.Scenario;
import io.simulao.ScenarioExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class LoopingScenarioExecutor implements ScenarioExecutor {

    private Logger log = LoggerFactory.getLogger(LoopingScenarioExecutor.class);

    private Scenario scenario;
    private AtomicInteger counter;
    private AtomicBoolean running = new AtomicBoolean(false);

    private Thread thread;

    private Consumer<Report> reportConsumer;
    private ScenarioExecutor executor;

    public LoopingScenarioExecutor(ScenarioExecutor executor, Consumer<Report> reportConsumer) {
        this.reportConsumer = reportConsumer;
        this.executor = executor;
        this.reset();
    }

    public void start() {
        log.debug("Starting LoopingScenarioExecutor");
        this.running.set(true);
        thread = new Thread(this::execute);
        thread.start();
    }

    public void stop() {
        log.debug("Stopping LoopingScenarioExecutor");
        this.running.set(false);
    }

    public void load(Scenario scenario) {
        log.debug("Loading Scenario: {}", scenario);
        this.scenario = scenario;
    }

    public void setIterations(int n) {
        this.counter = new AtomicInteger(n);
    }

    public void addIterations(int n) {
        int m = counter.get();
        if (n > 0 && m > 0) {
            setIterations(m + n);
        }
    }

    public void setExecutor(ScenarioExecutor executor) {
        this.executor = executor;
    }

    public void execute(Scenario scenario, int times) {
        setIterations(times);
        load(scenario);
        start();
    }

    public Report execute(Scenario scenario) {
        return executor.execute(scenario);
    }

    protected void execute() {
        while(isRunning()) {
            if (iterationsRemain()) {
                executeOne();
            } else {
                stop();
            }
        }
    }

    public boolean executeOne() {
        log.trace("Executing Single Iteration");

        // Verify Scenario is loaded
        if (scenario == null) {
            log.warn("No Scenario is Loaded");
            return false;
        }

        // Verify iterations remain
        if (!iterationsRemain()) {
            log.warn("No Iterations Remain");
            return false;
        }

        // Execute and report
        Report report = execute(scenario);
        reportConsumer.accept(report);

        // Decrease remaining iterations
        decrement();

        return true;
    }

    public boolean iterationsRemain() {
        int n = remainingIterations();
        return n > 0 || n == -1;
    }

    public int remainingIterations() {
        return counter.get();
    }

    public void reset() {
        log.debug("Resetting LoopingScenarioExecutor");
        this.scenario = null;
        this.counter = new AtomicInteger(-1);
    }

    public boolean isRunning() {
        return this.running.get();
    }

    private void decrement() {
        int n = counter.get();
        if (n > 0) {
            counter.decrementAndGet();
        }
    }

}
