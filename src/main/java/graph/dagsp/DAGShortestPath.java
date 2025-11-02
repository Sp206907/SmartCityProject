package main.java.graph.dagsp;

import java.util.*;
import main.java.graph.util.SimpleMetrics;

public class DAGShortestPath {
    private final List<List<int[]>> adj;
    private final int n;
    private final SimpleMetrics metrics = new SimpleMetrics();

    public DAGShortestPath(List<List<int[]>> adj) {
        this.adj = adj;
        this.n = adj.size();
    }

    public double[] shortest(int src, List<Integer> topo) {
        metrics.reset();
        double[] dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[src] = 0;

        for (int u : topo)
            if (dist[u] != Double.POSITIVE_INFINITY)
                for (int[] e : adj.get(u)) {
                    int v = e[0], w = e[1];
                    metrics.incOps();
                    if (dist[v] > dist[u] + w)
                        dist[v] = dist[u] + w;
                }

        metrics.stop();
        metrics.print("DAG Shortest Path");
        return dist;
    }

    public double[] longest(int src, List<Integer> topo) {
        metrics.reset();
        double[] dist = new double[n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        dist[src] = 0;

        for (int u : topo)
            if (dist[u] != Double.NEGATIVE_INFINITY)
                for (int[] e : adj.get(u)) {
                    int v = e[0], w = e[1];
                    metrics.incOps();
                    if (dist[v] < dist[u] + w)
                        dist[v] = dist[u] + w;
                }

        metrics.stop();
        metrics.print("DAG Longest Path");
        return dist;
    }
}
