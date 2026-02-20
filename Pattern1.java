import java.util.Scanner;

public class Pattern1
{
    public static void main(String[] args)
    {
        char a[][]=new char[8][8];
        char next='W';
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the number of rows and columns: ");
        int n=s.nextInt();
        int m=s.nextInt();
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(next=='W')
                {
                    a[i][j]='B';
                    next=a[i][j];
                }
                else
                {
                    a[i][j]='W';
                    next=a[i][j];
                }
            }
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                System.out.print(a[i][j]+"\t");
            }
            System.out.println();
        }
    }    
}
