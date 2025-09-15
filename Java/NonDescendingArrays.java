import java.io.*;
import java.util.*;

public class NonDescendingArrays {
    static final long MOD = 998244353L;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] a = new int[n];
            int[] b = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            for (int i = 0; i < n; i++) b[i] = sc.nextInt();

            long dp0 = 1L, dp1 = 1L;

            for (int i = 1; i < n; i++) {
                long cur0 = 0L, cur1 = 0L;

                int a0 = a[i], b0 = b[i];
                int a1 = b[i], b1 = a[i];

                int pa0 = a[i - 1], pb0 = b[i - 1];
                int pa1 = b[i - 1], pb1 = a[i - 1];

                if (pa0 <= a0 && pb0 <= b0) cur0 = (cur0 + dp0) % MOD;
                if (pa1 <= a0 && pb1 <= b0) cur0 = (cur0 + dp1) % MOD;

                if (pa0 <= a1 && pb0 <= b1) cur1 = (cur1 + dp0) % MOD;
                if (pa1 <= a1 && pb1 <= b1) cur1 = (cur1 + dp1) % MOD;

                dp0 = cur0;
                dp1 = cur1;
            }

            System.out.println((dp0 + dp1) % MOD);
        }
        sc.close();
    }
}
