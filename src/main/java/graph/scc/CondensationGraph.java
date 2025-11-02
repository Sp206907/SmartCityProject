package main.java.graph.scc;

import java.util.*;

public class CondensationGraph {
    private final List<List<Integer>> dag;
    private final int[] compIndex;

    public CondensationGraph(List<List<Integer>> adj, List<List<Integer>> sccs) {
        int n = adj.size();
        compIndex = new int[n];

        // map each vertex to its component id
        for (int i = 0; i < sccs.size(); i++)
            for (int v : sccs.get(i)) compIndex[v] = i;

        dag = new ArrayList<>();
        for (int i = 0; i < sccs.size(); i++) dag.add(new ArrayList<>());

        // build edges between different components only
        Set<String> seen = new HashSet<>();
        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                int cu = compIndex[u];
                int cv = compIndex[v];
                if (cu != cv && seen.add(cu + "-" + cv))
                    dag.get(cu).add(cv);
            }
        }
    }

    public List<List<Integer>> getDAG() { return dag; }
    public int[] getCompIndex() { return compIndex; }
}
