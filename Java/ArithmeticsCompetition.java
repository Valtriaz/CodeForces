import java.io.*;
import java.util.*;

public class ArithmeticsCompetition {
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
            int c, sgn = 1, x = 0;
            do { c = read(); } while (c <= ' ');
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') {
                x = x * 10 + (c - '0');
                c = read();
            }
            return x * sgn;
        }

        long nextLong() throws IOException {
            int c, sgn = 1;
            long x = 0;
            do { c = read(); } while (c <= ' ');
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') {
                x = x * 10 + (c - '0');
                c = read();
            }
            return x * sgn;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();
        while (t-- > 0) {
            int n = fs.nextInt();
            int m = fs.nextInt();
            int q = fs.nextInt();

            long[] A = new long[n];
            long[] B = new long[m];
            for (int i = 0; i < n; i++) A[i] = fs.nextLong();
            for (int i = 0; i < m; i++) B[i] = fs.nextLong();

            Arrays.sort(A);
            Arrays.sort(B);

            long[] preA = new long[n + 1];
            long[] preB = new long[m + 1];
            for (int i = 1; i <= n; i++) preA[i] = preA[i - 1] + A[n - i];
            for (int i = 1; i <= m; i++) preB[i] = preB[i - 1] + B[m - i];

            for (int qi = 0; qi < q; qi++) {
                int x = fs.nextInt();
                int y = fs.nextInt();
                int z = fs.nextInt();

                if (z == 0) {
                    out.append(0).append('\n');
                    continue;
                }

                int L = Math.max(0, z - y);
                int R = Math.min(z, x);
                int low = L, high = R;
                while (low < high) {
                    int mid = (low + high) >>> 1;
                    long sMid = preA[mid] + preB[z - mid];
                    long sNext = preA[mid + 1] + preB[z - (mid + 1)];
                    if (sMid <= sNext) {
                        low = mid + 1;
                    } else {
                        high = mid;
                    }
                }
                long best = preA[low] + preB[z - low];
                out.append(best).append('\n');
            }
        }

        System.out.printf("%s", out.toString());
    }
}
