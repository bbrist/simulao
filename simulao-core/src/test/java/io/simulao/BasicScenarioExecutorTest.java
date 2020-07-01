package io.simulao;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class BasicScenarioExecutorTest {

    private BasicScenarioExecutor executor = new BasicScenarioExecutor();

    @Test
    public void whenGivenScenario_outputReport() {
        BasicScenario scenario = new BasicScenario();
        scenario.addAction(() -> {});
        scenario.addAction(() -> {});

        Report report = executor.execute(scenario);
        Assert.assertEquals(2, report.size());
    }

}
