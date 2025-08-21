import java.io.*;
import java.util.*;

public class FamousChoreographer {
    static final long BASE = 1315423911L;
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int t = fs.nextInt();
        StringBuilder out = new StringBuilder();
        while (t-- > 0) {
            int n = fs.nextInt();
            int m = fs.nextInt();
            char[][] a = new char[n][];
            for (int i = 0; i < n; i++) {
                a[i] = fs.next().toCharArray();
            }
            long[] pow = new long[Math.max(n, m) + 5];
            pow[0] = 1L;
            for (int i = 1; i < pow.length; i++) pow[i] = mul64(pow[i - 1], BASE);

            long[][] pref = new long[n][];
            long[][] prefRev = new long[n][];
            for (int i = 0; i < n; i++) {
                pref[i] = new long[m + 1];
                prefRev[i] = new long[m + 1];
                for (int j = 0; j < m; j++) {
                    pref[i][j + 1] = add64(mul64(pref[i][j], BASE), (a[i][j] - 'a' + 1));
                }
                for (int j = 0; j < m; j++) {
                    prefRev[i][j + 1] = add64(mul64(prefRev[i][j], BASE), (a[i][m - 1 - j] - 'a' + 1));
                }
            }
            int bestAdd = Integer.MAX_VALUE;
            int maxV = 2 * n - 2;
            int maxU = 2 * m - 2;
            int lenU = maxU + 1;

            for (int v = 0; v <= maxV; v++) {
                int rLo = Math.max(0, v - (n - 1));
                int rHi = Math.min(n - 1, v);
                if (rLo > rHi) continue;
                int rowOverlapCount = rHi - rLo + 1;

                boolean[] validU = new boolean[lenU];
                Arrays.fill(validU, true);
                int remaining = lenU;

                for (int r = rLo; r <= rHi; r++) {
                    int r2 = v - r;
                    boolean anyThisRow = false;

                    for (int u = 0; u <= maxU; u++) {
                        if (!validU[u]) continue;
                        int cLo = Math.max(0, u - (m - 1));
                        int cHi = Math.min(m - 1, u);
                        int L = cHi - cLo + 1;
                        if (L <= 0) {
                            continue;
                        }
                        long hash1 = subHash(pref[r], pow, cLo, cHi);
                        int revL = m - 1 - cHi;
                        int revR = m - 1 - cLo;
                        long hash2 = subHash(prefRev[r2], pow, revL, revR);
                        if (hash1 != hash2) {
                            validU[u] = false;
                            remaining--;
                        } else {
                            anyThisRow = true;
                        }
                    }
                    if (remaining <= 0) break;
                }

                if (remaining <= 0) continue;

                for (int u = 0; u <= maxU; u++) {
                    if (!validU[u]) continue;
                    int cLo = Math.max(0, u - (m - 1));
                    int cHi = Math.min(m - 1, u);
                    int colOverlapLen = cHi - cLo + 1;
                    if (colOverlapLen <= 0) continue;
                    if (rowOverlapCount <= 0) continue;

                    int N = Math.max(n, Math.max(v + 1, 2 * n - 1 - v));
                    int M = Math.max(m, Math.max(u + 1, 2 * m - 1 - u));
                    long added = 1L * N * M - 1L * n * m;
                    if (added < bestAdd) bestAdd = (int) added;
                    if (bestAdd == 0) break;
                }
                if (bestAdd == 0) break;
            }

            if (bestAdd == Integer.MAX_VALUE) bestAdd = 0;
            out.append(bestAdd).append('\n');
        }

        System.out.printf("%s", out.toString());
    }

    static long add64(long a, long b) { return a + b; }
    static long mul64(long a, long b) { return a * b; }

    static long subHash(long[] pref, long[] pow, int l, int r) {
        long x = pref[r + 1] - mul64(pref[l], pow[r - l + 1]);
        return x;
    }

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

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }
    }
}
