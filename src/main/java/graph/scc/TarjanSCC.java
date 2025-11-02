package main.java.graph.scc;

import java.util.*;
import main.java.graph.util.SimpleMetrics;

public class TarjanSCC {
    private final List<List<Integer>> adj;
    private final int n;
    private final int[] ids, low;
    private final boolean[] onStack;
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final List<List<Integer>> sccs = new ArrayList<>();
    private int id = 0;
    private final SimpleMetrics metrics = new SimpleMetrics();

    public TarjanSCC(List<List<Integer>> adj) {
        this.adj = adj;
        this.n = adj.size();
        ids = new int[n];
        low = new int[n];
        Arrays.fill(ids, -1);
        onStack = new boolean[n];
    }

    public List<List<Integer>> run() {
        metrics.reset();
        for (int i = 0; i < n; i++)
            if (ids[i] == -1) dfs(i);
        metrics.stop();
        metrics.print("TarjanSCC");
        return sccs;
    }

    private void dfs(int at) {
        metrics.incOps();
        stack.push(at);
        onStack[at] = true;
        ids[at] = low[at] = id++;

        for (int to : adj.get(at)) {
            metrics.incOps();
            if (ids[to] == -1) dfs(to);
            if (onStack[to]) low[at] = Math.min(low[at], low[to]);
        }

        if (ids[at] == low[at]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                comp.add(node);
                low[node] = ids[at];
                if (node == at) break;
            }
            sccs.add(comp);
        }
    }
}
