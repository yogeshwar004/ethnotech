import java.util.Scanner;

public class Pattern
{
    public static void main(String[] args)
    {
        Scanner s=new Scanner(System.in);
        int n=s.nextInt();
        int k=1;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(i>j || i==j)
                    System.out.print(3*k++ + "\t");
                else
                    System.out.print("   ");
                
            }
        System.out.println();
        }

    }
}
