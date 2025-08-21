import java.io.*;
import java.util.*;

public class SetsOfComplementarySums2125E {
    static final int MOD = 998244353;
    static final int MAX_VAL = 200005;
    static final int MAX_K = 633;
    static long[][] prefixSumDouble;

    public static void precompute(int maxK) {
        prefixSumDouble = new long[maxK + 1][MAX_VAL];

        long[] dpPrev = new long[MAX_VAL];
        long[] dpCurr = new long[MAX_VAL];
        long[] singlePrefix = new long[MAX_VAL];

        dpPrev[0] = 1;
        prefixSumDouble[0][0] = 1;
        for (int i = 1; i < MAX_VAL; i++) {
            prefixSumDouble[0][i] = (prefixSumDouble[0][i - 1] + 1) % MOD;
        }

        for (int k = 1; k <= maxK; k++) {
            long minSum = (long) k * (k + 1) / 2;
            Arrays.fill(dpCurr, 0);
            Arrays.fill(singlePrefix, 0);

            for (int s = 0; s < MAX_VAL; s++) {
                dpCurr[s] = dpPrev[s];
                if (s >= k) dpCurr[s] = (dpCurr[s] + dpCurr[s - k]) % MOD;

                long val = 0;
                if (s >= minSum) val = dpCurr[(int)(s - minSum)];

                singlePrefix[s] = ((s > 0 ? singlePrefix[s - 1] : 0) + val) % MOD;
                prefixSumDouble[k][s] = ((s > 0 ? prefixSumDouble[k][s - 1] : 0) + singlePrefix[s]) % MOD;
            }

            long[] temp = dpPrev;
            dpPrev = dpCurr;
            dpCurr = temp;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(br.readLine());

        int[] n = new int[t];
        int[] x = new int[t];
        int maxK = 0;

        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n[i] = Integer.parseInt(st.nextToken());
            x[i] = Integer.parseInt(st.nextToken());
            maxK = Math.max(maxK, n[i] - 1);
        }

        maxK = Math.min(maxK, MAX_K - 1);
        precompute(maxK);

        for (int i = 0; i < t; i++) {
            int k = n[i] - 1;

            if (n[i] == 1) {
                out.println(x[i]);
                continue;
            }
            if (n[i] > x[i] || k >= MAX_K) {
                out.println(0);
                continue;
            }

            long lower = Math.max(n[i], (long) k * (k + 3) / 2);
            if (lower > x[i]) {
                out.println(0);
                continue;
            }

            int sStart = Math.max((int)(lower - k), (int)((long)k * (k + 1) / 2));
            int sEnd = Math.min(x[i] - k, MAX_VAL - 1);

            if (sStart > sEnd) {
                out.println(0);
                continue;
            }

            long res = (prefixSumDouble[k][sEnd] - (sStart > 0 ? prefixSumDouble[k][sStart - 1] : 0) + MOD) % MOD;
            out.println(res);
        }

        out.flush();
    }
}
