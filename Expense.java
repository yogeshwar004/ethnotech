import java.util.ArrayList;
import java.util.Scanner;

public class Expense
{
    public static void main(String[] args)
    {
        ArrayList<Integer> ex = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = s.nextLine();
        int limit = 5000;
        while(true)
        {
            System.out.println("\nChoose the option:\nAdd\nSum\nExit");
            String n = s.nextLine();
            switch(n.toLowerCase())
            {
                case "add":
                    System.out.print("Enter the expense: ");
                    int number = s.nextInt();
                    s.nextLine();
                    ex.add(number);
                    int total = 0;
                    for(int i = 0; i < ex.size(); i++)
                    {
                        total += ex.get(i);
                    }
                    System.out.println("Expense added!");
                    if(total > limit)
                    {
                        System.out.println("⚠ WARNING: Budget exceeded!");
                        System.out.println("Your Total Expense = " + total);
                        System.out.println("Budget Limit = " + limit);
                    }
                    else
                    {
                        System.out.println("Current Total = " + total);
                        System.out.println("Remaining Budget = " + (limit - total));
                    }
                    break;
                case "sum":

                    int sum = 0;
                    for(int i = 0; i < ex.size(); i++)
                    {
                        sum += ex.get(i);
                    }
                    System.out.println("Total Expense = " + sum);
                    break;
                case "exit":
                    System.out.println("Goodbye " + name + "!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Operation...!");
            }
        }
    }
}
