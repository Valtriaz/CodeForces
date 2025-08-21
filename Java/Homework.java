import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Homework {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String a = br.readLine().trim();
            int m = Integer.parseInt(br.readLine().trim());
            String b = br.readLine().trim();
            String c = br.readLine().trim();

            StringBuilder sb = new StringBuilder(a);
            for (int i = 0; i < m; i++) {
                char ch = b.charAt(i);
                if (c.charAt(i) == 'V') {
                    sb.insert(0, ch);
                } else {
                    sb.append(ch);
                }
            }
            System.out.printf("%s\n", sb.toString());
        }
    }
}