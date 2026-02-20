import java.util.Scanner;

abstract class Appliances
{
    boolean status=false;
    abstract void start();
    abstract void stop();
}
class Light extends Appliances
{
    Boolean input;
    Light(boolean input)
    {
        this.input=input;
    }
    void start()
    {
        
        if(!status)
        {
            System.out.println("Light is turned on");
            status=true;
        }
        else
        {
            System.out.println("Light is already turned on");
        }
    }
    void stop()
    {
        if(!status)
        {
            System.out.println("Light is already turned off");
        }
        else
        {
            System.out.println("Light is turning off");
            status=false;
        }
    }
}
public class Power
{
    public static void main(String[] args)
    {
        Scanner s=new Scanner(System.in);
        boolean status=s.nextBoolean();
        
    }
}
