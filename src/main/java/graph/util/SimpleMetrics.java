package main.java.graph.util;

import java.util.*;

/**
 * Simple metrics collector for counting operations and measuring time.
 * Usage:
 *   metrics.reset();
 *   metrics.incOps();
 *   metrics.stop();
 *   metrics.print("TarjanSCC");
 */
public class SimpleMetrics {
    private long ops = 0;
    private long start, end;

    public void reset() {
        ops = 0;
        start = System.nanoTime();
    }

    public void incOps() {
        ops++;
    }

    public void stop() {
        end = System.nanoTime();
    }

    public void print(String label) {
        System.out.println("\n=== Metrics for " + label + " ===");
        System.out.println("Operations: " + ops);
        System.out.println("Time (ns): " + (end - start));
        System.out.println("==============================");
    }
}
