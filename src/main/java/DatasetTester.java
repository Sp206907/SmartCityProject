package main.java;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

import main.java.graph.scc.TarjanSCC;
import main.java.graph.scc.CondensationGraph;
import main.java.graph.topo.KahnTopo;
import main.java.graph.dagsp.DAGShortestPath;

/**
 * Reads every .json dataset in /data/, runs SCC → Topo → DAG-SP,
 * and prints metrics + timing summary for each.
 *
 * Requires org.json (already bundled with IntelliJ or add via Maven: org.json:json:20230227)
 */
public class DatasetTester {
    public static void main(String[] args) throws Exception {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            System.out.println("❌ Folder 'data/' not found.");
            return;
        }

        File[] files = dataDir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            System.out.println("❌ No .json datasets found in /data.");
            return;
        }

        System.out.println("=== Running Dataset Tests ===\n");
        for (File f : files) {
            System.out.println("📂 Dataset: " + f.getName());
            runDataset(f);
            System.out.println("---------------------------------------------\n");
        }
    }

    private static void runDataset(File f) throws Exception {
        String json = Files.readString(f.toPath());
        JSONObject obj = new JSONObject(json);
        int n = obj.getInt("n");
        JSONArray arr = obj.getJSONArray("edges");

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < arr.length(); i++) {
            JSONObject e = arr.getJSONObject(i);
            int u = e.getInt("u"), v = e.getInt("v");
            adj.get(u).add(v);
        }

        // --- SCC ---
        TarjanSCC scc = new TarjanSCC(adj);
        List<List<Integer>> sccs = scc.run();
        System.out.println("SCC count: " + sccs.size());

        // --- Condensation ---
        CondensationGraph cond = new CondensationGraph(adj, sccs);
        List<List<Integer>> dag = cond.getDAG();

        // --- Topo ---
        List<Integer> topo = KahnTopo.sort(dag);

        // --- DAG SP ---
        List<List<int[]>> adjw = new ArrayList<>();
        for (int i = 0; i < dag.size(); i++) adjw.add(new ArrayList<>());
        for (int u = 0; u < dag.size(); u++)
            for (int v : dag.get(u)) adjw.get(u).add(new int[]{v, 1});

        int src = topo.get(0);
        DAGShortestPath sp = new DAGShortestPath(adjw);
        double[] shortest = sp.shortest(src, topo);
        double[] longest = sp.longest(src, topo);

        // Show summary
        System.out.println("Topological order: " + topo);
        System.out.println("Source node: " + src);
        System.out.println("Shortest distances: " + Arrays.toString(shortest));
        System.out.println("Longest distances:  " + Arrays.toString(longest));
    }
}
