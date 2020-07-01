package io.simulao;

public interface Report {

    int size();
    void record(Reportable reportable);
    void record(Reportable reportable, Exception e);

}
