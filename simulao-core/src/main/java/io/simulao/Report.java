package io.simulao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Report {

    Logger log = LoggerFactory.getLogger(Report.class);

    int size();
    void record(Reportable reportable);
    void record(Reportable reportable, Exception e);

}
