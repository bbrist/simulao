package io.simulao.action;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class ActionStateWrapperTest {

    @Test
    public void whenActionPerformed_stateIsComplete() throws Exception {
        StatefulAction action = new ActionStateWrapper(() -> {});
        action.perform();

        ActionStatus status = action.getActionState().getStatus();
        Assert.assertEquals(ActionStatus.COMPLETE, status);
    }

    @Test
    public void whenActionNotPerformed_stateIsPending() {
        StatefulAction action = new ActionStateWrapper(() -> {});

        ActionStatus status = action.getActionState().getStatus();
        Assert.assertEquals(ActionStatus.PENDING, status);
    }

    @Test
    public void whenActionFails_stateIsFailed_withException() throws Exception {
        final String msg = "My Exception Message";
        StatefulAction action = new ActionStateWrapper(() -> {
            throw new Exception(msg);
        });

        action.perform();
        ActionState state = action.getActionState();
        Assert.assertEquals(ActionStatus.FAILED, state.getStatus());
        Assert.assertTrue(state.getMessage().contains(msg));
    }

}
