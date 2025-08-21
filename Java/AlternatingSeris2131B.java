import java.io.*;

public class AlternatingSeris {
    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
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
            while ((c = read()) <= ' ') {
                if (c == -1) return Integer.MIN_VALUE;
            }
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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int t = fs.nextInt();
        while (t-- > 0) {
            int n = fs.nextInt();
            StringBuilder line = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                int val;
                if ((i & 1) == 1) {
                    val = -1;
                } else {
                    if (i == n) val = 2;
                    else val = 3;
                }
                line.append(val).append(i == n ? "" : " ");
            }
            // print line using System.out.printf as requested
            System.out.printf("%s\n", line);
        }
    }
}
