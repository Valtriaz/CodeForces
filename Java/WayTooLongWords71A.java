import java.util.Scanner;

public class WayTooLongWords71A {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        for (int i = 0; i < n; i++) {
            String word = input.next();
            int l = word.length();
            if (word.length() > 10) {
                System.out.println(word.charAt(0) + "" + (l - 2) + word.charAt(l - 1));
            } else {
                System.out.println(word);
            }
        }
    }
}
