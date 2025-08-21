import java.io.*;
import java.util.*;

public class TheCunningSellerEasy {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());

        final int MAX = 40;
        long[] pow3 = new long[MAX];
        pow3[0] = 1L;
        for (int i = 1; i < MAX; i++) pow3[i] = pow3[i - 1] * 3L;

        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            long totalCost = 0L;

            for (int i = MAX - 1; i >= 0 && n > 0; i--) {
                if (pow3[i] > n) continue;
                long cnt = n / pow3[i];
                if (cnt == 0) continue;

                long costForI;
                if (i == 0) {
                    costForI = 3L;
                } else {
                    costForI = pow3[i + 1] + (long) i * pow3[i - 1];
                }

                totalCost += cnt * costForI;
                n -= cnt * pow3[i];
            }
            sb.append(String.format("%d\n", totalCost));
        }
        System.out.print(sb.toString());
    }
}
