package io.simulao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public interface Report {

    Logger log = LoggerFactory.getLogger(Report.class);

    int size();
    void record(Reportable reportable);
    void writeTo(Writer writer) throws IOException;

}
