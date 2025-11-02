package main.java;

import java.util.*;
import main.java.graph.scc.TarjanSCC;
import main.java.graph.scc.CondensationGraph;
import main.java.graph.topo.KahnTopo;
import main.java.graph.dagsp.DAGShortestPath;

public class Main {
    public static void main(String[] args) {
        // === Example graph (same as in assignment sample) ===
        int n = 8;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        int[][] edges = {
                {0,1,3},{1,2,2},{2,3,4},{3,1,1},
                {4,5,2},{5,6,5},{6,7,1},
                // add a connection from group A to B:
                {3,4,1}
        };

        for (int[] e : edges) adj.get(e[0]).add(e[1]);

        // 1️⃣ SCC
        TarjanSCC scc = new TarjanSCC(adj);
        List<List<Integer>> sccs = scc.run();
        System.out.println("=== SCC Components ===");
        int idx = 0;
        for (List<Integer> comp : sccs) {
            System.out.println("Component " + idx++ + ": " + comp + " (size=" + comp.size() + ")");
        }

        // 2️⃣ Condensation graph
        CondensationGraph cond = new CondensationGraph(adj, sccs);
        List<List<Integer>> dag = cond.getDAG();

        System.out.println("\n=== Condensation DAG ===");
        for (int i = 0; i < dag.size(); i++)
            System.out.println("Node " + i + " -> " + dag.get(i));

        // 3️⃣ Topological order
        List<Integer> order = KahnTopo.sort(dag);
        System.out.println("\nTopological order: " + order);

        // 4️⃣ Shortest / Longest Paths using real condensation DAG
        List<List<int[]>> adjw = new ArrayList<>();
        for (int i = 0; i < dag.size(); i++) adjw.add(new ArrayList<>());

// assign weight = 1 for every edge in condensation DAG
        for (int u = 0; u < dag.size(); u++) {
            for (int v : dag.get(u)) {
                adjw.get(u).add(new int[]{v, 1});
            }
        }

// ✅ choose the first node in topological order as source
        int src = order.get(0);

        DAGShortestPath sp = new DAGShortestPath(adjw);
        double[] shortest = sp.shortest(src, order);
        double[] longest = sp.longest(src, order);

        System.out.println("DEBUG: Condensation DAG edges = " + dag);
        System.out.println("\nSource node: " + src);
        System.out.println("Shortest distances: " + Arrays.toString(shortest));
        System.out.println("Longest distances:  " + Arrays.toString(longest));

        System.out.println("DEBUG: Condensation DAG edges = " + dag);


        System.out.println("\nSource node: " + src);
        System.out.println("Shortest distances: " + Arrays.toString(shortest));
        System.out.println("Longest distances:  " + Arrays.toString(longest));

    }
}
