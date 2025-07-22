import java.util.Scanner;

public class Bit282A {
public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    int n = input.nextInt();
    int x=0;
    for (int i = 0; i < n; i++){
        String a = input.next();
    if (a.contains("+")){
        x++;
    }
    else {
        x--;
    }
    }
    System.out.println(x);
}
}
