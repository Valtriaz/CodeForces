import java.util.Scanner;

public class Watermelon4A {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        if (k % 2 == 0 && k!=2){
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
