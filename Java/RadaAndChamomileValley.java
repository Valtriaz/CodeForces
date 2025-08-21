import java.io.*;
import java.util.*;

public class RadaAndChamomileValley {
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }

    static class Edge { int to, id; Edge(int t, int i){to=t;id=i;} }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();
        int t = fs.nextInt();
        while (t-- > 0) {
            int n = fs.nextInt(), m = fs.nextInt();
            ArrayList<Edge>[] g = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) g[i] = new ArrayList<>();
            int[] eu = new int[m + 1], ev = new int[m + 1];
            for (int i = 1; i <= m; i++) {
                int u = fs.nextInt(), v = fs.nextInt();
                eu[i] = u; ev[i] = v;
                g[u].add(new Edge(v, i));
                g[v].add(new Edge(u, i));
            }

            boolean[] visited = new boolean[n + 1];
            int[] tin = new int[n + 1], low = new int[n + 1];
            boolean[] isBridge = new boolean[m + 1];
            int timer = 0;

            int[] stV = new int[n + 5], stIter = new int[n + 5], stParent = new int[n + 5], stParentEdge = new int[n + 5];
            int sp;

            for (int start = 1; start <= n; start++) {
                if (visited[start]) continue;
                sp = 0;
                stV[sp] = start;
                stIter[sp] = 0;
                stParent[sp] = -1;
                stParentEdge[sp] = -1;
                sp++;
                while (sp > 0) {
                    int v = stV[sp - 1];
                    int iter = stIter[sp - 1];
                    int parent = stParent[sp - 1];
                    int pedge = stParentEdge[sp - 1];

                    if (!visited[v]) {
                        visited[v] = true;
                        tin[v] = low[v] = ++timer;
                    }

                    if (iter < g[v].size()) {
                        Edge e = g[v].get(iter);
                        stIter[sp - 1] = iter + 1;
                        int to = e.to, id = e.id;
                        if (id == pedge) continue;
                        if (visited[to]) {
                            if (tin[to] < low[v]) low[v] = tin[to];
                        } else {
                            stV[sp] = to;
                            stIter[sp] = 0;
                            stParent[sp] = v;
                            stParentEdge[sp] = id;
                            sp++;
                        }
                    } else {
                        sp--;
                        if (parent != -1) {
                            if (low[v] < low[parent]) low[parent] = low[v];
                            if (low[v] > tin[parent]) {
                                isBridge[pedge] = true;
                            }
                        }
                    }
                }
            }

            int[] comp = new int[n + 1];
            Arrays.fill(comp, -1);
            int compCnt = 0;
            ArrayDeque<Integer> qd = new ArrayDeque<>();
            for (int i = 1; i <= n; i++) {
                if (comp[i] != -1) continue;
                compCnt++;
                comp[i] = compCnt;
                qd.clear();
                qd.add(i);
                while (!qd.isEmpty()) {
                    int v = qd.poll();
                    for (Edge e : g[v]) {
                        if (isBridge[e.id]) continue;
                        if (comp[e.to] == -1) {
                            comp[e.to] = compCnt;
                            qd.add(e.to);
                        }
                    }
                }
            }

            int comp1 = comp[1], compn = comp[n];

            ArrayList<int[]>[] tree = new ArrayList[compCnt + 1];
            for (int i = 1; i <= compCnt; i++) tree[i] = new ArrayList<>();
            for (int i = 1; i <= m; i++) {
                if (isBridge[i]) {
                    int a = comp[eu[i]], b = comp[ev[i]];
                    tree[a].add(new int[]{b, i});
                    tree[b].add(new int[]{a, i});
                }
            }

            boolean[] isSTBridge = new boolean[m + 1];
            if (comp1 != compn) {
                int[] tPar = new int[compCnt + 1];
                int[] tParEdge = new int[compCnt + 1];
                Arrays.fill(tPar, -1);
                ArrayDeque<Integer> dq = new ArrayDeque<>();
                dq.add(comp1);
                tPar[comp1] = 0;
                while (!dq.isEmpty()) {
                    int v = dq.poll();
                    if (v == compn) break;
                    for (int[] e : tree[v]) {
                        int to = e[0], idx = e[1];
                        if (tPar[to] == -1) {
                            tPar[to] = v;
                            tParEdge[to] = idx;
                            dq.add(to);
                        }
                    }
                }
                int cur = compn;
                while (cur != comp1 && cur != 0) {
                    int idx = tParEdge[cur];
                    if (idx == 0) break;
                    isSTBridge[idx] = true;
                    cur = tPar[cur];
                }
            }

            int q = fs.nextInt();
            int[] queries = new int[q];
            for (int i = 0; i < q; i++) queries[i] = fs.nextInt();

            boolean anyST = false;
            for (int i = 1; i <= m; i++) if (isSTBridge[i]) { anyST = true; break; }
            if (!anyST) {
                for (int i = 0; i < q; i++) {
                    out.append(-1).append(i + 1 == q ? '\n' : ' ');
                }
                continue;
            }

            final int INF = Integer.MAX_VALUE / 4;
            int[] dist = new int[n + 1];
            int[] ansEdge = new int[n + 1];
            Arrays.fill(dist, INF);
            Arrays.fill(ansEdge, Integer.MAX_VALUE);

            ArrayDeque<Integer> queue = new ArrayDeque<>();
            for (int i = 1; i <= m; i++) if (isSTBridge[i]) {
                int a = eu[i], b = ev[i];
                if (0 < dist[a] || (0 == dist[a] && i < ansEdge[a])) {
                    dist[a] = 0; ansEdge[a] = i; queue.add(a);
                } else if (0 == dist[a] && i < ansEdge[a]) { ansEdge[a] = i; }
                if (0 < dist[b] || (0 == dist[b] && i < ansEdge[b])) {
                    dist[b] = 0; ansEdge[b] = i; queue.add(b);
                } else if (0 == dist[b] && i < ansEdge[b]) { ansEdge[b] = i; }
            }

            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (Edge e : g[v]) {
                    int to = e.to;
                    int nd = dist[v] + 1;
                    int nid = ansEdge[v];
                    if (nd < dist[to] || (nd == dist[to] && nid < ansEdge[to])) {
                        dist[to] = nd;
                        ansEdge[to] = nid;
                        queue.add(to);
                    }
                }
            }

            for (int i = 0; i < q; i++) {
                int c = queries[i];
                if (dist[c] == INF) out.append(-1);
                else out.append(ansEdge[c]);
                out.append(i + 1 == q ? '\n' : ' ');
            }
        }
        System.out.printf("%s", out.toString());
    }
}
