import java.io.*;

public class From1ToInfinity {
    static final long[] POW10 = new long[19];
    static {
        POW10[0] = 1L;
        for (int i = 1; i < POW10.length; i++) POW10[i] = POW10[i-1] * 10L;
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
        long nextLong() throws IOException {
            int c; do { c = read(); } while (c <= ' ' && c != -1);
            int sign = 1; if (c == '-') { sign = -1; c = read(); }
            long val = 0;
            while (c > ' ') { val = val * 10 + (c - '0'); c = read(); }
            return val * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int t = (int) fs.nextLong();
        StringBuilder out = new StringBuilder();

        while (t-- > 0) {
            long k = fs.nextLong();
            long ans = solve(k);
            // printf as requested
            System.out.printf("%d%n", ans);
        }
    }

    static long solve(long k) {
        if (k <= 0) return 0;

        long x = maxXWithDigitsAtMost(k);

        long sum = sumDigits1ToN(x);

        long r = k - totalDigitsUpTo(x);
        if (r > 0) {
            long nxt = x + 1;
            String s = Long.toString(nxt);
            long extra = 0;
            for (int i = 0; i < (int) r; i++) extra += s.charAt(i) - '0';
            sum += extra;
        }
        return sum;
    }

    static long maxXWithDigitsAtMost(long k) {
        long lo = 0, hi = k;
        while (lo < hi) {
            long mid = lo + ((hi - lo + 1) >>> 1);
            if (totalDigitsUpTo(mid) <= k) lo = mid;
            else hi = mid - 1;
        }
        return lo;
    }

    static long totalDigitsUpTo(long n) {
        if (n <= 0) return 0;
        long res = 0;
        for (int d = 1; d <= 18; d++) {
            long start = POW10[d-1];
            if (n < start) break;
            long end = Math.min(n, POW10[d] - 1);
            long cnt = end - start + 1;
            res += cnt * d;
        }
        return res;
    }

    static long sumDigits1ToN(long n) {
        if (n <= 0) return 0;
        return sumDigits0ToN(n);
    }

    static long sumDigits0ToN(long n) {
        if (n < 10) return n * (n + 1) / 2;
        long p = 1;
        while (p * 10 <= n) p *= 10;
        long a = n / p;
        long b = n % p;
        int d = digits(p) - 1;
        long sum0toPminus1 = d * 45L * (p / 10);
        return a * sum0toPminus1
                + (a * (a - 1) / 2) * p
                + a * (b + 1)
                + sumDigits0ToN(b);
    }

    static int digits(long x) {
        // x is power of 10 here; but keep general
        int d = 0;
        while (x > 0) { d++; x /= 10; }
        return d;
    }
}
