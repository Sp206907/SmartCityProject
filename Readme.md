# 🏙 SmartCityProject – Graph Analysis Report (Assignment 4)

## 🎯 Objective
This project implements three core **graph algorithms** using Java to analyze task dependencies in a **Smart City Scheduling** scenario.  
It measures algorithm performance across different dataset sizes and structures.

### Implemented Algorithms
1. **Tarjan’s Algorithm (SCC)** – detects Strongly Connected Components and compresses cycles.
2. **Kahn’s Algorithm (Topological Sort)** – orders tasks in acyclic graphs.
3. **Shortest / Longest Path Algorithms for DAGs** – finds optimal and critical paths.

---

## 🗂 Folder Structure
SmartCityProject/
├── src/
│ └── main/java/
│ ├── main/java/
│ │ ├── Main.java
│ │ └── DatasetTester.java
│ ├── graph/
│ │ ├── scc/
│ │ │ ├── TarjanSCC.java
│ │ │ └── CondensationGraph.java
│ │ ├── topo/
│ │ │ └── KahnTopo.java
│ │ ├── dagsp/
│ │ │ └── DAGShortestPath.java
│ │ └── util/
│ │ ├── SimpleMetrics.java
│ │ └── Metrics.java
├── data/ # JSON graph datasets
├── lib/ # org.json library
└── out/ # compiled output files

yaml
Копировать код

---

## ⚙️ How to Run

### 🧩 Run the example (Main.java)
```bash
javac -d out $(find src -name "*.java")
java -classpath out main.java.Main
🧩 Run all datasets (DatasetTester.java)
bash
Копировать код
javac -d out -cp "lib/json-20230227.jar" $(find src -name "*.java")
java -classpath "out:lib/json-20230227.jar" main.java.DatasetTester
✅ Notes:

Place .json datasets in the /data folder.

Ensure json-20230227.jar is in /lib.

Output shows metrics and timing for each dataset automatically.

📊 Metrics and Instrumentation
Metric	Algorithm	Description
DFS Visits / Edges	TarjanSCC	Tracks recursion and edge traversals
Queue Pushes / Pops	KahnTopo	Tracks in-degree operations
Relaxations	DAG-SP	Tracks distance updates in shortest/longest paths
Time (ns)	All	Recorded using System.nanoTime()

The shared SimpleMetrics class records both operation counts and runtime.
All algorithms call it through the same interface.

📁 Dataset Generation (Part 2)
📂 Overview
All datasets are stored in /data/ and include both cyclic and acyclic graphs with various densities.

Category	Count	Nodes	Description
Small	3	6–10	Simple cases, 1–2 cycles or DAG
Medium	3	10–20	Mixed structure, several SCCs
Large	3	20–50	Performance and timing tests

Each dataset was generated with different densities (sparse vs dense) and connectivity patterns.

📁 Data Summary
Dataset	Nodes	Edges	Structure	Cyclic
small1.json	8	10	Sparse	Yes
small2.json	7	9	Dense	No
small3.json	9	11	Mixed	Yes
medium1.json	15	22	Mixed	Yes
medium2.json	13	18	Mixed	Yes
medium3.json	14	19	DAG	No
large1.json	25	40	Dense	Yes
large2.json	35	55	Sparse	Yes
large3.json	45	70	Dense	Mixed

📈 Example Output
markdown
Копировать код
=== Metrics for TarjanSCC ===
Operations: 16
Time (ns): 159600
==============================
=== Metrics for KahnTopo ===
Operations: 16
Time (ns): 84000
==============================
=== Metrics for DAG Shortest Path ===
Operations: 5
Time (ns): 76900
==============================
=== Metrics for DAG Longest Path ===
Operations: 5
Time (ns): 36800
==============================
Topological order: [5, 4, 3, 2, 1, 0]
Shortest distances: [5.0, 4.0, 3.0, 2.0, 1.0, 0.0]
Longest distances:  [5.0, 4.0, 3.0, 2.0, 1.0, 0.0]
📊 Results Summary
Dataset	SCC Count	Time (SCC ns)	Time (Topo ns)	Time (SP ns)	Total Ops
small1.json	6	25,500	104,700	8,400	32
medium1.json	10	133,200	37,000	9,400	51
large1.json	25	212,800	144,600	71,100	90

🔍 Analysis and Discussion
⚙️ Algorithm Bottlenecks
TarjanSCC – recursion depth grows with more SCCs.

KahnTopo – performance depends on queue size (node degree).

DAG Shortest/Longest Path – very efficient once topological order is computed.

⚙️ Effect of Graph Structure
Dense graphs (more edges) → higher operation count and runtime.

Sparse DAGs → fewer operations and faster results.

Multiple SCCs → increase condensation graph size and complexity.

🧠 Conclusions
Algorithm	Strength	Limitation	Best Used For
TarjanSCC	Detects cycles precisely	Recursion-heavy	Network dependency analysis
KahnTopo	Simple and efficient DAG sorting	Needs in-degree array	Task scheduling
DAG Shortest Path	Finds critical paths fast	Works only on DAGs	Workflow optimization

✅ Overall:
The algorithms show O(V + E) time complexity.

The instrumentation validates efficiency and correctness.

The system can scale from small to large graphs smoothly.

🧾 References
Tarjan, R. (1972). Depth-first search and linear graph algorithms.

Kahn, A. (1962). Topological sorting of large networks.

Cormen et al., Introduction to Algorithms (CLRS)

org.json library for dataset parsing.