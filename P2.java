import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2 {
	static class Task {
		public static final String INPUT_FILE = "p2.in";
		public static final String OUTPUT_FILE = "p2.out";

		int n;
		int m;
		int source;
		int dest;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
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
				source = sc.nextInt();
				dest = sc.nextInt();
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

		private void writeOutput(int [] result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				if (result.length == 0) {
					sb.append("Ciclu negativ!\n");
				} else {
					sb.append(result[dest]).append(' ');
					sb.append('\n');
				}
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int [] getResult() {
			
			int [] d = new int[n + 1]; 

			// vector pentru a seta daca exista sau nu nodul deja
			// in coada
			boolean [] isIn = new boolean[n + 1];

			// initializez distantele
			for (int i = 0; i <= n; i++) { 
				d[i] = Integer.MAX_VALUE; 
			} 
			d[source] = 0; 

			Queue<Integer> q = new LinkedList<>(); 
			isIn[source] = true; 
			q.add(source); 

			while (!q.isEmpty()) { 

				int u = q.peek(); 
				q.remove(); 
				isIn[u] = false; 

				// se relaxeaza toate muchiile care pornesc din nodul extras
				for (int i = 0; i < adj[u].size(); i++) { 

					int v = adj[u].get(i).node; 
					int thisCost = adj[u].get(i).cost; 

					if (d[v] > d[u] + thisCost) { 
						d[v] = d[u] + thisCost; 

						//daca nu se afla deja in coada, in adaug
						if (!isIn[v]) { 
							q.add(v); 
							isIn[v] = true; 
						} 
					} 
				} 
			} 
			return d;
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
