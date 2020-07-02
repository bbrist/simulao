package io.simulao.report;

import io.simulao.Report;
import io.simulao.Reportable;
import io.simulao.action.ActionState;
import io.simulao.action.StatefulAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ActionStateReport implements Report {

    private Logger log = LoggerFactory.getLogger(ActionStateReport.class);

    private List<Entry> entries = new ArrayList<>();

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public void record(Reportable reportable) {
        ActionState state = ActionState.none();
        if(reportable instanceof StatefulAction) {
            state = ((StatefulAction) reportable).getActionState();
        }

        entries.add(new Entry(reportable, state));
    }

    @Override
    public void record(Reportable reportable, Exception e) {
        record(reportable);
    }

    @Override
    public void writeTo(Writer writer) throws IOException {
        for(Entry entry : entries) {
            writer.write(String.format("%s: %s", entry.reportable, entry.state.getDescription()));
            writer.write("\n");
        }
    }

    static class Entry {
        Reportable reportable;
        ActionState state;

        public Entry(Reportable reportable, ActionState state) {
            this.reportable = reportable;
            this.state = state;
        }
    }

}