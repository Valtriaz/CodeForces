import java.util.Scanner;

public class LeftAndDown {

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        while (t-- > 0) {
            long a = scanner.nextLong();
            long b = scanner.nextLong();
            long k = scanner.nextLong();

            long commonDivisor = gcd(a, b);
            long requiredDx = a / commonDivisor;
            long requiredDy = b / commonDivisor;

            if (requiredDx <= k && requiredDy <= k) {
                System.out.println(1);
            } else {
                System.out.println(2);
            }
        }
        scanner.close();
    }
}