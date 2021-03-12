import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class P1 {
	static class Task {
		
		public static final String INPUT_FILE = "p1.in";	
		public static final String OUTPUT_FILE = "p1.out";

		int n;
		int m;
		int k;

		boolean ok;
		//vector in care setez daca un nod este blocat sau nu
		boolean [] block;
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer> [] adj;
		@SuppressWarnings("unchecked")
		ArrayList<Integer> guard;
		@SuppressWarnings("unchecked")
		ArrayList<Integer> p;

		class Edge {
			int x;
			int y;
		
			public Edge(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}

		// exceptie ce va fi aruncata pentru a se iesi din recursivitatea
		// fortat
		class FoundBlock extends Exception {    
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
										INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				k = sc.nextInt();

				adj = new ArrayList[n + 1];
				guard = new ArrayList(k);
				p = new ArrayList(n - 1);

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}

				for (int i = 0; i < k; i++) {
					guard.add(sc.nextInt());
				}

				for (int i = 0; i < n - 1; i++) {
					p.add(sc.nextInt());
				}

				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
												OUTPUT_FILE)));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// parcurgerea dfs, in cazul in care nodul coincide cu unul dintre nodurile
		// din care lorzii pleaca atunci se iese din recursivitate complet
		void dfs(int node, boolean[] visited, ArrayList<Integer> path) throws FoundBlock {
			visited[node] = true;

			for (int v: adj[node]) {
				if (!block[v]) {

					if (guard.indexOf(v) != -1) {
						throw new FoundBlock();
					}

					if (!visited[v]) {
						dfs(v, visited, path);
					}
				}
			}
			path.add(node);
		}

		// functia incearca sa faca mereu dfs pe graf pornind din nodul 1;
		// daca s - a iesit din recursibitate prin aruncare unei exceptii
		// atunci inseamna ca s - a atins un nod in care se afla lorzii
		// deci urmatorul nod din permutare trebuie blocat
		public int getResult() {
			int cnt = 0;
			block = new boolean [n + 1];
			while (true) {
				boolean [] visited = new boolean[n + 1];
				ArrayList<Integer> path = new ArrayList<>();
				ok = false;   
				try {
					dfs(1, visited, path);
				} catch (FoundBlock ex) {
					ok = true;
				}
				if (ok) {
					block[p.get(cnt)] = true;
					cnt++;
				} else {
					break;
				}
			}
			return cnt;
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