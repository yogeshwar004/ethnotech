public class ChessBoard
{
    public static void main(String[] args)
    {
        char a[][]=new char[8][8];
        char next='W';
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
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
            next=a[i][2];
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
