/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yogeshwar N
 */
import java.util.*;
public class RandomNumbers
{
    public static void main(String[] args)
    {
        while(true)
        {
            Random r=new Random();
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the range: (1 to n)");
            int n=sc.nextInt();
            int number=r.nextInt(n)+1;
            int attempts=0;
            System.out.println("Welcome to number guessing game!");
            System.out.println("I'm thinking of a number between O to "+(n));
            while(true)
            {
                System.out.println("Enter the guess: ");
                int guess=sc.nextInt();
                attempts++;
                if(guess<number)
                    System.out.println("Guess is too less");
                else if(guess==number)
                {
                    System.out.println("You have guessed the number in attempt number "+attempts);
                    break;
                }
                else
                    System.out.println("Guess is too high");
            }
        }
    }
}
