package io.simulao.report;

import io.simulao.Report;
import io.simulao.Reportable;

import java.util.ArrayList;
import java.util.List;

public class BasicReport implements Report {

    private List<Entry> entries = new ArrayList<>();

    public BasicReport() {}

    public void addEntry(String content) {
        this.entries.add(new Entry(content));
    }

    @Override
    public void record(Reportable reportable) {
        addEntry(String.format("%s: Success", reportable.getName()));
    }

    @Override
    public void record(Reportable reportable, Exception e) {
        addEntry(String.format("%s: %s", reportable.getName(), e.getMessage()));
    }

    @Override
    public int size() {
        return entries.size();
    }

    public static class Entry {
        String content;

        public Entry(String content) {
            this.content = content;
        }
    }

}
