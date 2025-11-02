package main.java.graph.topo;

import java.util.*;
import main.java.graph.util.SimpleMetrics;

public class KahnTopo {
    public static List<Integer> sort(List<List<Integer>> adj) {
        SimpleMetrics metrics = new SimpleMetrics();
        metrics.reset();

        int n = adj.size();
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++)
            for (int v : adj.get(i)) {
                indeg[v]++;
                metrics.incOps();
            }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            metrics.incOps();
            order.add(u);
            for (int v : adj.get(u)) {
                indeg[v]--;
                metrics.incOps();
                if (indeg[v] == 0) q.add(v);
            }
        }

        metrics.stop();
        metrics.print("KahnTopo");
        return order;
    }
}
