package main.java.graph.util;

public interface Metrics {
    void incOps();
    long getOps();
    void startTimer();
    void stopTimer();
    long getTimeNano();
    void reset();
    void addOperation(String key);
    void print();
}
