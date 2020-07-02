package io.simulao.executor;

import io.simulao.BasicScenario;
import io.simulao.Report;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LoopingScenarioExecutorTest {

    private static BasicScenario scenario = new BasicScenario();

    private List<Report> reports = new ArrayList<>();

    private LoopingScenarioExecutor executor = new LoopingScenarioExecutor(new BasicScenarioExecutor(), reports::add);

    @BeforeAll
    public static void setup() {
        scenario.addAction(() -> {});
        scenario.addAction(() -> {});
    }

    @BeforeEach
    public void prepare() {
        reports.clear();
        executor.stop();
        executor.load(scenario);
    }

    @Test
    public void whenExecutingMultipleIterations_executorStopsWhenNoMoreIterationsRemain() throws Exception {
        executor.setIterations(5);
        executor.start();
        Thread.sleep(1000);

        Assert.assertFalse(executor.isRunning());
    }

    @Test
    public void whenExecutingInfiniteIterations_executorStopsWhenStopped() throws Exception {
        executor.start();
        Thread.sleep(1000);
        executor.stop();
        Thread.sleep(200);
        int n = reports.size();
        Thread.sleep(1000);

        Assert.assertEquals("Executor should stop when stopped", n, reports.size());
    }

    @Test
    public void whenExecutingInfiniteIterations_executorStartsWhenStarted() throws Exception {
        executor.start();
        Thread.sleep(1000);
        executor.stop();
        int n = reports.size();
        executor.start();
        Thread.sleep(1000);
        executor.stop();

        Assert.assertTrue("Expected executor to restart", n < reports.size());
    }

    @Test
    public void whenAddingIterationsToInfinite_iterationsStayInfinite() {
        executor.setIterations(-1);
        executor.addIterations(5);

        Assert.assertEquals(-1, executor.remainingIterations());
    }

    @Test
    public void whenSettingIterations_infiniteBecomesNumber() {
        executor.setIterations(-1);
        executor.setIterations(5);

        Assert.assertEquals(5, executor.remainingIterations());
    }

    @Test
    public void whenAddingIterations_iterationsAreIncreased() {
        executor.setIterations(2);
        executor.addIterations(3);

        Assert.assertEquals(5, executor.remainingIterations());
    }

    @Test
    public void whenExecutingOne_iterationsDecrease() {
        executor.setIterations(2);
        executor.executeOne();
        Assert.assertEquals(1, executor.remainingIterations());
    }

    @Test
    public void whenExecutingOne_oneScenarioIsExecuted() {
        executor.executeOne();
        Assert.assertEquals(1, reports.size());
    }

    @Test
    public void whenScenarioNotLoaded_doNotExecuteOne() {
        executor.reset();
        boolean executed = executor.executeOne();

        Assert.assertFalse(executed);
    }

    @Test
    public void whenNoRemainingIterations_doNotExecuteOne() {
        executor.setIterations(1);
        Assert.assertTrue("Expected to execute first iterations", executor.executeOne());

        Assert.assertFalse("Expected no remaining iterations", executor.executeOne());
    }

    @Test
    public void executeTimes_runsNNumberOfTimes() throws Exception {
        executor.execute(scenario, 3);
        Thread.sleep(1000);

        Assert.assertEquals(3, reports.size());
    }

    @Test
    public void setExecutorOverridesExistingExecutor() {
        executor.setExecutor(s -> null);
        Report report = executor.execute(scenario);

        Assert.assertNull(report);
    }

}
