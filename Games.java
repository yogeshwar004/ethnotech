// Wallet class - protects balance from direct modification
class Wallet
{
    private int balance;
    
    Wallet(int initialBalance)
    {
        this.balance = initialBalance;
    }
    
    // Protected getter - can only read balance
    int getBalance()
    {
        return balance;
    }
    
    // Protected deduction - deducted only through games
    boolean deductCredits(int credits)
    {
        if (balance >= credits) {
            balance -= credits;
            return true;
        }
        return false;
    }
    
    // Add credits to wallet
    void addCredits(int credits)
    {
        balance += credits;
    }
    
    void displayBalance()
    {
        System.out.println("Current Balance: " + balance + " credits");
    }
}

// Abstract Game class
abstract class Game
{
    protected Wallet wallet;
    
    Game(Wallet wallet)
    {
        this.wallet = wallet;
    }
    
    // Common way to start any game
    abstract void start();
    
    // Common way to stop any game
    abstract void stop();
    
    // Each game has different credits per play
    abstract int getCreditsRequired();
    
    // Common play method
    void play()
    {
        int creditsNeeded = getCreditsRequired();
        
        if (wallet.getBalance() < creditsNeeded) {
            System.out.println("Insufficient credits! Need " + creditsNeeded + " credits.");
            System.out.println("You only have " + wallet.getBalance() + " credits.");
            return;
        }
        
        if (wallet.deductCredits(creditsNeeded)) {
            start();
            System.out.println("Playing game... " + creditsNeeded + " credits deducted.");
            stop();
            System.out.println();
            wallet.displayBalance();
        }
    }
}

// Racing Game - 20 credits per play
class Racing extends Game
{
    Racing(Wallet wallet)
    {
        super(wallet);
    }
    
    @Override
    void start()
    {
        System.out.println("*** Racing Game Started ***");
        System.out.println("Accelerating... Vroom Vroom!");
    }
    
    @Override
    void stop()
    {
        System.out.println("Finishing Line Crossed! Game Over.");
    }
    
    @Override
    int getCreditsRequired()
    {
        return 20;
    }
}

// Shooting Game - 15 credits per play
class Shooting extends Game
{
    Shooting(Wallet wallet)
    {
        super(wallet);
    }
    
    @Override
    void start()
    {
        System.out.println("*** Shooting Game Started ***");
        System.out.println("Targeting enemy... Bang Bang!");
    }
    
    @Override
    void stop()
    {
        System.out.println("All targets eliminated! Game Over.");
    }
    
    @Override
    int getCreditsRequired()
    {
        return 15;
    }
}

// VR Game - 30 credits per play (premium game)
class VR extends Game
{
    VR(Wallet wallet)
    {
        super(wallet);
    }
    
    @Override
    void start()
    {
        System.out.println("*** VR Game Started ***");
        System.out.println("Entering virtual reality... 3D experience activated!");
    }
    
    @Override
    void stop()
    {
        System.out.println("Virtual world closed! Game Over.");
    }
    
    @Override
    int getCreditsRequired()
    {
        return 30;
    }
}

public class Games
{
    public static void main(String[] args)
    {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   Welcome to Game Arcade System    ║");
        System.out.println("╚════════════════════════════════════╝\n");
        
        // Player name input
        System.out.print("Enter your name: ");
        String playerName = sc.nextLine();
        
        // Initial credits
        System.out.print("Enter initial credits (minimum 50): ");
        int initialCredits = sc.nextInt();
        while (initialCredits < 50) {
            System.out.print("Minimum 50 credits required! Enter again: ");
            initialCredits = sc.nextInt();
        }
        sc.nextLine(); // Consume newline
        
        Wallet playerWallet = new Wallet(initialCredits);
        System.out.println("\n✓ Membership Card Purchased for " + playerName + "!");
        playerWallet.displayBalance();
        
        // Create game options
        Game[] games = {
            new Racing(playerWallet),
            new Shooting(playerWallet),
            new VR(playerWallet)
        };
        
        String[] gameNames = {"Racing Game (20 credits)", "Shooting Game (15 credits)", "VR Game (30 credits)"};
        
        boolean playing = true;
        
        while (playing) {
            System.out.println("\n┌────────────────────────────────────┐");
            System.out.println("│       AVAILABLE GAMES               │");
            System.out.println("├────────────────────────────────────┤");
            System.out.println("│ 1. Racing Game (20 credits)        │");
            System.out.println("│ 2. Shooting Game (15 credits)      │");
            System.out.println("│ 3. VR Game (30 credits)            │");
            System.out.println("│ 4. Check Balance                   │");
            System.out.println("│ 5. Add Credits                     │");
            System.out.println("│ 6. Exit                            │");
            System.out.println("└────────────────────────────────────┘");
            
            System.out.print("Choose an option (1-6): ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                case 2:
                case 3:
                    System.out.println("\n--- " + gameNames[choice - 1] + " ---");
                    System.out.print("How many times do you want to play? ");
                    int playCount = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    
                    for (int i = 0; i < playCount; i++) {
                        System.out.println("\n[Round " + (i + 1) + "]");
                        games[choice - 1].play();
                        
                        if (playerWallet.getBalance() < 15) {
                            System.out.println("\n⚠ Not enough credits for more games!");
                            break;
                        }
                    }
                    break;
                    
                case 4:
                    System.out.println("\n--- BALANCE CHECK ---");
                    playerWallet.displayBalance();
                    break;
                    
                case 5:
                    System.out.print("\nEnter amount to add (minimum 10): ");
                    int addCredits = sc.nextInt();
                    if (addCredits >= 10) {
                        playerWallet.addCredits(addCredits);
                        System.out.println("✓ Credits added successfully!");
                        playerWallet.displayBalance();
                    } else {
                        System.out.println("✗ Invalid amount! Minimum 10 credits.");
                    }
                    sc.nextLine(); // Consume newline
                    break;
                    
                case 6:
                    playing = false;
                    break;
                    
                default:
                    System.out.println("✗ Invalid option! Please choose 1-6.");
                    sc.nextLine(); // Consume newline
            }
        }
        
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║         GAME SESSION ENDED          ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("Thanks for playing, " + playerName + "!");
        System.out.println("Final ");
        playerWallet.displayBalance();
        
        sc.close();
    }
}
