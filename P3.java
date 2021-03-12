import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P3 {
	static class Task {
		public static final String INPUT_FILE = "p3.in";
		public static final String OUTPUT_FILE = "p3.out";
		public static final int INF = (int) 1e9;

		int n;
		int m;
		double efort;
		int source = 1;
		
		public class Edge implements Comparable<Edge> {
			public int node;
			public int cost;
			
			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}

			public int compareTo(Edge rhs) {
				return Integer.compare(cost, rhs.cost);
			}
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Edge> [] adj;
		
		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				efort = (double)sc.nextInt();
				adj = new ArrayList[n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void writeOutput(ArrayList<Integer> result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
									OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				bw.write(String.format("%.8f", efort));
				bw.write("\n");
				for (int i = 0; i < result.size(); i++) {
					if (i == result.size() - 1) {
						sb.append(result.get(i));
					} else {
						sb.append(result.get(i)).append(' ');
					}
				}
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		// functia ce imi intoarce costul unei muchii de la s la d
		private int getP(int s, int d) {
			for (int i = 0; i < adj[s].size(); i++) {
				if (adj[s].get(i).node == d) {
					return adj[s].get(i).cost;
				}
			}
			return -1;
		}

		// implentarea algoritmuli Dijkstra preluat din solutia laboratorului ()
		private int [] dijkstra(int source, ArrayList<Integer> dist) {

			for (int i = 1; i <= n; i++) {
				dist.set(i, INF);
			}

			PriorityQueue<Edge> pq = new PriorityQueue<>();
			int [] p = new int[n + 1];
			p[source] = -1;
			dist.set(source, 0);
			pq.add(new Edge(source, 0));

			while (!pq.isEmpty()) {
				int u = pq.peek().node;
				int d = pq.poll().cost;
				
				if (d > dist.get(u)) {
					continue;
				}

				for (Edge e: adj[u]) {
					if (d + e.cost < dist.get(e.node)) {
						dist.set(e.node, d + e.cost);
						pq.add(new Edge(e.node, dist.get(e.node)));
						p[e.node] = u;
					}
				}
			}
			
			for (int i = 1; i <= n; i++) {
				if (dist.get(i) == INF) {
					dist.set(i, -1);
				}
			}
			return p;
		}
		
		private ArrayList<Integer> getResult() {
			ArrayList<Integer> d = new ArrayList<>();
			// construiesc calea de la sursa la destinatie
			ArrayList<Integer> path = new ArrayList<Integer>();

			for (int i = 0; i <= n; i++) {
				d.add(0);
			}
			
			// apelez dijkstra ce intoarce drumul cu solutia cea mai buna 
			// gasita
			int [] p = dijkstra(source, d);
			int nod = p[n];
			
			// construies calea in functie de parinti
			path.add(n);
			while (nod != -1) {
				path.add(nod);
				nod = p[nod];
			}
			Collections.reverse(path);
			
			// calculez efortul ramas
			for (int i = 0; i < path.size() - 1; i++) {
				double aux = 1 - (getP(path.get(i), path.get(i + 1)) * (0.01));
				efort *= aux;
			}
			return path;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}
	
	public static void main(String[] args) {
		new Task().solve();
	}
}