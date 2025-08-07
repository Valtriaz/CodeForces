import java.io.*;
import java.util.*;

public class MixMexMax2127A {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int known = -1;
            boolean zeroPresent = false;
            boolean conflict = false;
            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(st.nextToken());
                if (x == -1) continue;
                if (x == 0) zeroPresent = true;
                if (known == -1) {
                    known = x;
                } else if (known != x) {
                    conflict = true;
                }
            }
            if (zeroPresent || conflict) {
                sb.append("NO\n");
            } else {
                sb.append("YES\n");
            }
        }
        System.out.printf(sb.toString());
    }
}
