import java.io.*;
import java.util.*;

public class TheCunningSellerHard {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int t = fs.nextInt();
        StringBuilder sb = new StringBuilder();
        ArrayList<Long> pow3 = new ArrayList<>();
        long p = 1L;
        while (p > 0 && p <= (long)4e18) {
            pow3.add(p);
            if (p > Long.MAX_VALUE / 3) break;
            p *= 3L;
        }
        int MAX = pow3.size();

        while (t-- > 0) {
            long n = fs.nextLong();
            long k = fs.nextLong();
            if (k > n) k = n;
            long totalDeals = n;
            long extra = 0L;

            long[] cnt = new long[MAX + 2];
            cnt[0] = n;

            for (int x = 0; x < MAX && totalDeals > k; x++) {
                if (cnt[x] < 3) continue;
                long mergesPossible = cnt[x] / 3;
                long needMerges = (totalDeals - k + 1) / 2;
                long merges = Math.min(mergesPossible, needMerges);

                cnt[x] -= merges * 3;
                cnt[x + 1] += merges;
                totalDeals -= merges * 2;
                extra += merges * pow3.get(x);
            }

            if (totalDeals > k) {
                sb.append("-1\n");
            } else {
                long ans = 3L * n + extra;
                sb.append(ans).append('\n');
            }
        }
        System.out.printf("%s", sb.toString());
    }

    static final class FastScanner {
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
        long nextLong() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Long.MIN_VALUE;
            boolean neg = (c == '-');
            if (neg) c = read();
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return neg ? -val : val;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
        int next() throws IOException { return nextInt(); }
    }
}
