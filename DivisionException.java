public class DivisionException
{
   public static void main(String[] args)
    {
    try
        {
           int result = 10 / 0;
        }
       catch(ArithmeticException e)
        {
           System.out.println("Error: Division by zero");
        }
        finally
        {
           System.out.println("Operation complete");
        }
    }
}