import java.io.*;
import java.util.*;

public class TheSecretNumber {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();
        while (t-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            ArrayList<Long> ans = new ArrayList<>();
            long pow = 10L; // 10^1
            while (pow <= n) {
                long denom = 1L + pow; // 1 + 10^k
                if (denom != 0 && n % denom == 0) {
                    long x = n / denom;
                    if (x > 0) ans.add(x);
                }
                if (pow > Long.MAX_VALUE / 10) break;
                pow *= 10L;
            }
            if (ans.isEmpty()) {
                out.append("0\n");
            } else {
                Collections.sort(ans);
                out.append(ans.size()).append('\n');
                for (int i = 0; i < ans.size(); ++i) {
                    out.append(ans.get(i));
                    if (i + 1 < ans.size()) out.append(' ');
                }
                out.append('\n');
            }
        }
        System.out.printf("%s", out.toString());
    }
}
