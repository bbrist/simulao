package io.simulao.report;

import io.simulao.Report;
import io.simulao.Reportable;

import java.io.IOException;
import java.io.Writer;
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
    public void writeTo(Writer writer) throws IOException {
        for(Entry entry : entries) {
            writer.write(String.format("%s\n", entry.content));
        }
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
