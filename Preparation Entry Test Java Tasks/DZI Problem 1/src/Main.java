import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        int max = 0;

        for (int i = 0; i < N; i++) {
            int x = in.nextInt();
            if(x>max)
                max=x;
        }
        int maxA = max + 1;
        System.out.println("Max Element =  "+maxA);

        char[] d = String.valueOf(maxA).toCharArray();
        Arrays.sort(d);
        String maxDigitString = new String(d);

        System.out.println("Max Digit = " + maxDigitString);
        int maxDigitInt = Integer.parseInt(maxDigitString);
        System.out.println("next Max Digit = " + (maxDigitInt + 1));
    }
}
