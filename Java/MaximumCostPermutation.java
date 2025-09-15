import java.io.*;
import java.util.*;

public class MaximumCostPermutation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] p = new int[n];
            boolean[] present = new boolean[n + 1];
            int zeros = 0;
            for (int i = 0; i < n; i++) {
                p[i] = sc.nextInt();
                if (p[i] == 0) {
                    zeros++;
                } else {
                    if (p[i] >= 1 && p[i] <= n) present[p[i]] = true;
                }
            }

            boolean[] missing = new boolean[n + 1];
            for (int v = 1; v <= n; v++) if (!present[v]) missing[v] = true;

            int L = -1, R = -1;
            for (int i = 0; i < n; i++) {
                boolean isMismatch;
                if (p[i] == 0) {
                    if (zeros == 1 && missing[i + 1]) isMismatch = false;
                    else isMismatch = true;
                } else {
                    isMismatch = (p[i] != i + 1);
                }
                if (isMismatch) { L = i; break; }
            }
            if (L != -1) {
                for (int i = n - 1; i >= 0; i--) {
                    boolean isMismatch;
                    if (p[i] == 0) {
                        if (zeros == 1 && missing[i + 1]) isMismatch = false;
                        else isMismatch = true;
                    } else {
                        isMismatch = (p[i] != i + 1);
                    }
                    if (isMismatch) { R = i; break; }
                }
            }

            int ans = 0;
            if (L != -1) ans = R - L + 1;
            sb.append(ans).append('\n');
        }
        System.out.printf("%s", sb.toString());
        sc.close();
    }
}
