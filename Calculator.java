import java.util.Scanner;
public class Calculator
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter the Two Numbers:");
            int a=sc.nextInt();
            int b=sc.nextInt();
            System.out.println("Enter the Operation\n1. Addition\n2. Substaction\n 3. Multiplication\n4. Division\n5. Power\n");
            int op=sc.nextInt();
            System.out.print("Result=");
            switch(op)
            {
                case 1: System.out.println(a+b);
                        break;
                case 2: System.out.println(a-b);
                        break;
                case 3: System.out.println(a*b);
                        break;
                case 4: if(a>b) System.out.println(a/b);
                        else if(b>a)System.out.println(b/a);
                        else System.out.println("Invalid!");
                        break;
                case 5: System.out.println((Math.pow(a,b)));
                        break;
                default: System.out.println("Invalid!");
                         break;
            }
        }
    }
}
