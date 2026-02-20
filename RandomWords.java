/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yogeshwar N
 */
import java.util.Random;
import java.util.Scanner;

public class RandomWords {
    public static void main(String[] args) {
        // 1. Create an array of strings (words)
        String[] words = {"java", "programming", "computer", "keyboard", "language", "challenge"};

        // 2. Take the length of the array
        int arrayLength = words.length;

        // 3. Use the Random function to generate a random index
        Random random = new Random();
        // nextInt(arrayLength) generates a random integer between 0 (inclusive) and arrayLength (exclusive)
        int randomIndex = random.nextInt(arrayLength);

        // 4. Randomly choose one word
        String secretWord = words[randomIndex];
        // Convert to lowercase for easier matching later
        secretWord = secretWord.toLowerCase(); 

        // Initialize variables for the game loop
        Scanner scanner = new Scanner(System.in);
        boolean guessedCorrectly = false;
        
        System.out.println("Welcome to the Word Guessing Game!");
        System.out.println("The word has " + secretWord.length() + " letters.");

        // Game loop
        while (!guessedCorrectly) {
            // 5. Read one word from the keyboard (user input)
            System.out.print("Enter your guess: ");
            String userGuess = scanner.nextLine();
            userGuess = userGuess.toLowerCase(); // Convert user input to lowercase

            // 6. Match the input with the secret word
            if (userGuess.equals(secretWord)) {
                guessedCorrectly = true;
                System.out.println("Congratulations! You guessed the word: " + secretWord);
            } else {
                System.out.println("Incorrect guess. Try again!");
            }
        }
        
        // Close the scanner
        scanner.close();
    }
}
