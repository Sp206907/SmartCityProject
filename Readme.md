# 🏙 SmartCityProject – Extended Graph Analysis Report (Assignment 4)

---

## 🎯 Objective

This project combines two main graph theory topics — **Strongly Connected Components (SCCs)** and **Shortest Paths in Directed Acyclic Graphs (DAGs)** — into one practical simulation:  
**Smart City / Smart Campus Task Scheduling**.

The main goal is to model city maintenance and service dependencies (repairs, cleaning, sensor monitoring), detect cyclic dependencies, and plan optimal schedules using algorithmic analysis.

---

## 🧩 Learning Goals

- Detect strongly connected task groups using **Tarjan’s Algorithm**
- Build the **condensation DAG** (a graph of SCCs)
- Sort tasks using **Kahn’s Topological Sort Algorithm**
- Compute **shortest and longest paths** in DAGs for optimization
- Measure **operation counts** and **execution time** with a shared metrics system

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
└── out/ # compiled output

---

## ⚙️ How to Run

### ▶ Run Example Graph

```bash
javac -d out $(find src -name "*.java")
java -classpath out main.java.Main
▶ Run All Datasets
bash
javac -d out -cp "lib/json-20230227.jar" $(find src -name "*.java")
java -classpath "out:lib/json-20230227.jar" main.java.DatasetTester
✅ Notes:

Place all .json datasets in the /data folder

Add the json-20230227.jar file into /lib

Results include metrics and timing for each dataset automatically

📘 Theoretical Background
🔹 Strongly Connected Components (Tarjan’s Algorithm)
A Strongly Connected Component (SCC) is a maximal subgraph where every vertex can reach every other vertex.
Tarjan’s algorithm identifies SCCs using DFS, node discovery times, and a stack structure.
It runs in O(V + E) time, making it ideal for large graphs.

Output:

List of components (each a list of vertices)

Condensation DAG (each SCC becomes one node)

🔹 Condensation Graph and Topological Sort
After compressing SCCs, the resulting graph is a Directed Acyclic Graph (DAG).
A Topological Sort gives a valid order of components (no edge goes backward).

Kahn’s Algorithm steps:

Compute in-degrees of all nodes

Add nodes with zero in-degree to a queue

Remove edges and repeat

This order is crucial for scheduling smart city services.

🔹 Shortest and Longest Paths in DAG
In a DAG, shortest and longest paths can be found efficiently using Dynamic Programming (DP) along the topological order.

Shortest Path: minimum duration from source to all nodes

Longest Path (Critical Path): identifies the most time-consuming dependency chain

Used in project management, logistics, and repair planning.

🧠 Implementation Overview
📦 Packages and Roles
Package	Class	Function
graph.scc	TarjanSCC.java	Finds SCCs using DFS and stack
graph.scc	CondensationGraph.java	Builds DAG from SCC components
graph.topo	KahnTopo.java	Performs topological sorting
graph.dagsp	DAGShortestPath.java	Finds shortest & longest paths
graph.util	SimpleMetrics.java	Measures operations & time
graph.util	Metrics.java	Defines metrics interface

📊 Metrics and Instrumentation
Metric	Algorithm	Description
DFS Visits / Edges	TarjanSCC	Tracks recursive DFS steps
Push / Pop	KahnTopo	Counts queue operations
Relaxations	DAG-SP	Counts distance updates
Time (ns)	All	Measured using System.nanoTime()

The metrics are consistent across all algorithms, ensuring reproducibility.

📁 Dataset Generation
📂 Dataset Categories
Category	Files	Nodes	Description
Small	3	6–10	Simple graphs, 1–2 cycles or DAG
Medium	3	10–20	Several SCCs, mixed density
Large	3	20–50	Performance and timing tests

📂 Example JSON Structure
json
{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    {"u": 1, "v": 2, "w": 2},
    {"u": 2, "v": 3, "w": 4},
    {"u": 3, "v": 1, "w": 1},
    {"u": 4, "v": 5, "w": 2},
    {"u": 5, "v": 6, "w": 5},
    {"u": 6, "v": 7, "w": 1}
  ],
  "source": 4,
  "weight_model": "edge"
}
📊 Dataset Summary
Dataset	Nodes	Edges	Structure	Cyclic	SCCs
small1.json	8	10	Sparse	Yes	6
small2.json	7	9	Dense	No	5
small3.json	9	11	Mixed	Yes	5
medium1.json	15	22	Mixed	Yes	10
medium2.json	13	18	Mixed	Yes	13
medium3.json	14	19	DAG	No	14
large1.json	25	40	Dense	Yes	25
large2.json	35	55	Sparse	Yes	35
large3.json	45	70	Dense	Mixed	45

📈 Example Output
markdown
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

🔍 Performance and Analysis
⚙️ Algorithm Efficiency
Algorithm	Time Complexity	Space	Observations
TarjanSCC	O(V + E)	O(V)	Linear, slightly slower on dense graphs
KahnTopo	O(V + E)	O(V)	Queue-based, steady performance
DAG-SP	O(V + E)	O(V)	Linear; efficient after topological sort

⚙️ Observations
Dense graphs → more DFS recursion and queue operations

Sparse graphs → faster overall time

Many SCCs increase condensation DAG size

DAG-SP performs consistently across all graphs

💡 Discussion
Cycle detection via SCCs helps isolate repeating dependencies

Topological order simplifies scheduling and priority analysis

Critical path (longest path) highlights bottlenecks in execution

Metrics framework ensures reproducible, measurable performance

Algorithmic methods suit large-scale optimization and AI planning

🧠 Conclusions and Recommendations
✅ Key Findings
Tarjan’s SCC and DAG path algorithms scale linearly with graph size

Condensation graphs simplify complex dependencies

Metrics confirm consistent results across dataset categories

💡 Recommendations
Use SCC compression before scheduling real-world systems

Extend project to visualize DAGs with Graphviz or D3.js

Add incremental SCC updates for dynamic graph changes

Test scalability on datasets ≥100 nodes

🧾 References
Tarjan, R. E. (1972). Depth-First Search and Linear Graph Algorithms

Kahn, A. (1962). Topological Sorting of Large Networks

Cormen, Leiserson, Rivest & Stein. Introduction to Algorithms (CLRS)

org.json library documentation (Maven Central, 2023)

