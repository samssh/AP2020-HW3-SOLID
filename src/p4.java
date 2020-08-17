import java.util.Scanner;

public class p4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n=scanner.nextInt();
        int[] dn = new int[n];
        int max=0;
        for (int i = 0; i < n; i++) {
            dn[i] =scanner.nextInt();
            if (max<dn[i])max = dn[i];
        }
        boolean[] result = new boolean[max+1];
        for (int i = 0; i <=max ; i++) {
            int di=D(i);
            if (di<=max)
            result[di]=true;
        }
        for (int i = 0; i < n; i++) {
            if (result[dn[i]]) System.out.println("yes");
            else System.out.println("no");
        }
    }

    static int D(int n){
        return n+sumOfNumbers(n)+sumOfPrimes(n);
    }

    static int sumOfNumbers(int n){
        int sum=0;
        while (n>0){
            sum+=n%10;
            n/=10;
        }
        return sum;
    }

    static int sumOfPrimes(int n){
        int sum=0;
        for (int i = 2; i*i <= n; i++) {
            if (n%i==0) sum+=i;
            while (n%i==0)n/=i;
        }
        if (n>1) sum+=n;
        return sum;
    }
}
