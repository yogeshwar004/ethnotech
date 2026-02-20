import java.util.Scanner;

public class SwitchSystemXOR
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter S1 (0/1): ");
        int s1 = sc.nextInt();
        System.out.print("Enter S2 (0/1): ");
        int s2 = sc.nextInt();
        int fan   = (s1==1 && s2==0) ? 1 : 0;
        int motor = (s1==0 && s2==1) ? 1 : 0;
        int light = s1 ^ s2;
        if(fan==1)
            System.out.println("Fan is ON");
        else
            System.out.println("Fan is OFF");
        if(motor==1)
            System.out.println("Motor is ON");
        else
            System.out.println("Motor is OFF");
        if(light==1)
            System.out.println("Light is ON");
        else
            System.out.println("Light is OFF");
    }
}
