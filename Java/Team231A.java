    import java.util.Scanner;

public class Team231A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int win = 0;

        for (int i = 0; i < n; i++){
            int a = input.nextInt();
            int b = input.nextInt();
            int c = input.nextInt();

            int correct = a + b + c;
            if (correct >= 2){
                win++;
            }
        }
        System.out.println(win);
    }
}
