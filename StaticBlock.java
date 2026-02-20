public class StaticBlock
{
    static
    {
        System.out.println("Static block");
    }
    public static void main(String[] args)
    {
        System.out.println("Hello world");
        System.err.println("Error message");
    }
}
