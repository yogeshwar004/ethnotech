import java.util.*;

// ============================================================
//  MAGICAL FOOD FESTIVAL MANAGER
//  Collections Used:
//    ArrayList  -> Food Stalls
//    Vector     -> Dishes per Stall (Chef updates)
//    Queue      -> Customer waiting line
//    Stack      -> Customer's visited stall backtracking
//    LinkedList -> Daily festival history
// ============================================================

// ─────────────────────────────────────────────────────────────
//  MODEL CLASSES
// ─────────────────────────────────────────────────────────────

class Dish {
    String name;
    double price;
    String specialEffect; // e.g. "Glowing Eyes", "Flying Feeling"

    Dish(String name, double price, String specialEffect) {
        this.name = name;
        this.price = price;
        this.specialEffect = specialEffect;
    }

    public String toString() {
        return String.format("  %-20s ₹%-8.2f [%s]", name, price, specialEffect);
    }
}

class Stall {
    int id;
    String name;
    String cuisine;
    double rating;
    Vector<Dish> dishes = new Vector<>(); // Chef continuously updates dishes

    Stall(int id, String name, String cuisine, double rating) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public String toString() {
        return String.format("[%d] %-20s | Cuisine: %-12s | Rating: %.1f/5.0",
                id, name, cuisine, rating);
    }
}

class Customer {
    int id;
    String name;
    String mood;          // Happy | Adventurous | Confused
    String preferredCuisine;
    Stack<String> trail;  // Tracks last visited stalls (backtracking support)

    Customer(int id, String name, String mood, String preferredCuisine) {
        this.id = id;
        this.name = name;
        this.mood = mood;
        this.preferredCuisine = preferredCuisine;
        this.trail = new Stack<>();
    }

    public String toString() {
        return String.format("#%-3d %-15s | Mood: %-12s | Pref: %s",
                id, name, mood, preferredCuisine);
    }
}

class VisitRecord {
    String customerName;
    String stallName;
    String dishOrdered;
    double amountPaid;
    int serialNo;

    VisitRecord(int serialNo, String customerName, String stallName,
                String dishOrdered, double amountPaid) {
        this.serialNo = serialNo;
        this.customerName = customerName;
        this.stallName = stallName;
        this.dishOrdered = dishOrdered;
        this.amountPaid = amountPaid;
    }

    public String toString() {
        return String.format("[Visit #%d] %-15s -> %-20s | Ordered: %-20s | Paid: ₹%.2f",
                serialNo, customerName, stallName, dishOrdered, amountPaid);
    }
}

// ─────────────────────────────────────────────────────────────
//  FESTIVAL MANAGER
// ─────────────────────────────────────────────────────────────

class FestivalManager {

    ArrayList<Stall>      stalls        = new ArrayList<>();   // All stalls
    Queue<Customer>       customerQueue = new LinkedList<>();   // Customer line
    LinkedList<VisitRecord> history     = new LinkedList<>();   // Daily history

    private int visitSerial = 1;
    private Scanner sc;

    FestivalManager(Scanner sc) { this.sc = sc; }

    // ── Divider helper ──────────────────────────────────────
    private void line() {
        System.out.println("─".repeat(65));
    }

    // ── 1. ADD NEW FOOD STALL ───────────────────────────────
    public void addStall() {
        line();
        System.out.println("  ★  ADD NEW FOOD STALL");
        line();
        System.out.print("  Stall ID    : ");
        int id = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Stall Name  : ");
        String name = sc.nextLine().trim();
        System.out.print("  Cuisine Type: ");
        String cuisine = sc.nextLine().trim();
        System.out.print("  Rating(1-5) : ");
        double rating = Double.parseDouble(sc.nextLine().trim());

        stalls.add(new Stall(id, name, cuisine, rating));
        System.out.println("\n  ✔ Stall Added Successfully!");
    }

    // ── 2. CHEF UPDATES DISHES ─────────────────────────────
    public void manageDishes() {
        if (stalls.isEmpty()) { System.out.println("  No stalls exist yet."); return; }
        line();
        System.out.println("  ★  CHEF DISH MANAGEMENT  (Vector)");
        line();
        listStalls();
        System.out.print("  Select Stall ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Stall stall = findStall(id);
        if (stall == null) { System.out.println("  Stall not found!"); return; }

        System.out.println("\n  1. Add Dish   2. Remove Dish   3. View Dishes   4. Back");
        System.out.print("  Choice: ");
        int ch = Integer.parseInt(sc.nextLine().trim());

        switch (ch) {
            case 1:
                System.out.print("  Dish Name     : ");
                String dn = sc.nextLine().trim();
                System.out.print("  Price         : ");
                double dp = Double.parseDouble(sc.nextLine().trim());
                System.out.print("  Magical Effect: ");
                String de = sc.nextLine().trim();
                stall.dishes.add(new Dish(dn, dp, de));
                System.out.println("  ✔ Dish Added to " + stall.name);
                break;
            case 2:
                if (stall.dishes.isEmpty()) { System.out.println("  No dishes."); break; }
                printDishes(stall);
                System.out.print("  Dish index to remove (1-based): ");
                int ri = Integer.parseInt(sc.nextLine().trim()) - 1;
                if (ri >= 0 && ri < stall.dishes.size()) {
                    System.out.println("  ✔ Removed: " + stall.dishes.remove(ri).name);
                } else System.out.println("  Invalid index.");
                break;
            case 3:
                if (stall.dishes.isEmpty()) System.out.println("  No dishes yet.");
                else printDishes(stall);
                break;
            default:
                System.out.println("  Back to main menu.");
        }
    }

    // ── 3. ADD CUSTOMER TO QUEUE ────────────────────────────
    public void addCustomer() {
        line();
        System.out.println("  ★  ADD CUSTOMER TO QUEUE");
        line();
        System.out.print("  Customer ID    : ");
        int id = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Name           : ");
        String name = sc.nextLine().trim();
        System.out.print("  Mood (Happy / Adventurous / Confused): ");
        String mood = sc.nextLine().trim();
        System.out.print("  Preferred Cuisine: ");
        String pref = sc.nextLine().trim();

        customerQueue.add(new Customer(id, name, mood, pref));
        System.out.println("\n  ✔ " + name + " joined the queue. Queue size: " + customerQueue.size());
    }

    // ── 4. SERVE NEXT CUSTOMER ──────────────────────────────
    public void serveNextCustomer() {
        if (customerQueue.isEmpty()) { System.out.println("  Queue is empty!"); return; }
        if (stalls.isEmpty())        { System.out.println("  No stalls available!"); return; }

        Customer c = customerQueue.poll();
        line();
        System.out.println("  ★  SERVING CUSTOMER: " + c.name.toUpperCase());
        line();
        System.out.println("  Customer: " + c);

        Stall recommended = recommend(c);
        System.out.println("\n  Smart Recommendation based on mood [" + c.mood + "]:");
        System.out.println("  → " + recommended);

        // Assign a dish if available
        String dishName = "General Order";
        double amount = 0;
        if (!recommended.dishes.isEmpty()) {
            Dish d = recommended.dishes.get(0); // first available dish
            dishName = d.name;
            amount = d.price;
            System.out.println("\n  Suggested Dish: " + d);
        } else {
            System.out.print("  No dishes listed. Enter amount paid manually: ₹");
            amount = Double.parseDouble(sc.nextLine().trim());
        }

        // Track stall in customer's trail (Stack)
        c.trail.push(recommended.name);

        // Log to history (LinkedList)
        history.addLast(new VisitRecord(visitSerial++, c.name, recommended.name, dishName, amount));
        System.out.println("\n  ✔ Visit recorded. Remaining queue: " + customerQueue.size());
    }

    // ── 5. TRACK LAST VISITED STALLS (Stack) ───────────────
    public void trackVisit() {
        line();
        System.out.println("  ★  TRACK CUSTOMER STALL TRAIL  (Stack)");
        line();
        System.out.print("  Customer Name: ");
        String name = sc.nextLine().trim();
        System.out.print("  Stall visited (name): ");
        String stallName = sc.nextLine().trim();

        // Find or build a temp tracking object
        Customer found = findServedCustomer(name);
        if (found == null) {
            // Create a quick tracking entry
            found = new Customer(0, name, "Happy", "Any");
        }
        found.trail.push(stallName);
        System.out.println("  ✔ Stall \"" + stallName + "\" pushed to " + name + "'s trail.");
        System.out.println("  Current trail (top = latest): " + found.trail);
    }

    // ── 6. CUSTOMER FORGETS → GO BACK  (Stack pop) ─────────
    public void goBack() {
        line();
        System.out.println("  ★  CUSTOMER FORGOT SOMETHING → GO BACK  (Stack)");
        line();
        System.out.print("  Customer Name: ");
        String name = sc.nextLine().trim();

        Customer found = findServedCustomer(name);
        if (found == null || found.trail.isEmpty()) {
            System.out.println("  No stall trail found for this customer.");
            return;
        }
        String lastStall = found.trail.pop();
        System.out.println("  ✔ " + name + " is going back to: " + lastStall);
        if (!found.trail.isEmpty()) {
            System.out.println("  Previous stall before that: " + found.trail.peek());
        } else {
            System.out.println("  That was the first stall. Nothing further back.");
        }
    }

    // ── 7. SHOW FULL DAILY HISTORY (LinkedList) ─────────────
    public void showHistory() {
        line();
        System.out.println("  ★  DAILY FESTIVAL HISTORY  (LinkedList)");
        line();
        if (history.isEmpty()) { System.out.println("  No visits recorded yet."); return; }
        for (VisitRecord vr : history) System.out.println("  " + vr);
        System.out.println("\n  Total visits today: " + history.size());
    }

    // ── 8. SHOW LAST N VISITS ───────────────────────────────
    public void showLastN() {
        if (history.isEmpty()) { System.out.println("  No history yet."); return; }
        System.out.print("  Show last N visits. Enter N: ");
        int n = Integer.parseInt(sc.nextLine().trim());
        int start = Math.max(0, history.size() - n);
        line();
        System.out.println("  ★  LAST " + n + " VISITS");
        line();
        for (int i = start; i < history.size(); i++) {
            System.out.println("  " + history.get(i));
        }
    }

    // ── 9. VIEW ALL STALLS ──────────────────────────────────
    public void listStalls() {
        if (stalls.isEmpty()) { System.out.println("  No stalls added yet."); return; }
        System.out.println("\n  All Stalls:");
        for (Stall s : stalls) System.out.println("  " + s);
    }

    // ── 10. VIEW QUEUE ──────────────────────────────────────
    public void viewQueue() {
        if (customerQueue.isEmpty()) { System.out.println("  Queue is empty."); return; }
        line();
        System.out.println("  ★  CURRENT CUSTOMER QUEUE");
        line();
        int pos = 1;
        for (Customer c : customerQueue) {
            System.out.println("  " + pos++ + ". " + c);
        }
    }

    // ─────────────────────────────────────────────────────────
    //  SMART RECOMMENDATION ENGINE
    // ─────────────────────────────────────────────────────────
    private Stall recommend(Customer c) {
        List<Stall> pool = new ArrayList<>();
        for (Stall s : stalls) {
            if (s.cuisine.equalsIgnoreCase(c.preferredCuisine)) pool.add(s);
        }
        if (pool.isEmpty()) pool = new ArrayList<>(stalls);

        Random rand = new Random();
        switch (c.mood.toLowerCase()) {
            case "adventurous":
                // Highest rated stall
                return pool.stream().max(Comparator.comparingDouble(s -> s.rating)).orElse(pool.get(0));
            case "confused":
                // Random pick
                return pool.get(rand.nextInt(pool.size()));
            case "happy":
            default:
                // Top 3, pick randomly
                pool.sort((a, b) -> Double.compare(b.rating, a.rating));
                return pool.get(rand.nextInt(Math.min(3, pool.size())));
        }
    }

    // ─────────────────────────────────────────────────────────
    //  UTILITY HELPERS
    // ─────────────────────────────────────────────────────────
    private Stall findStall(int id) {
        for (Stall s : stalls) if (s.id == id) return s;
        return null;
    }

    // Tracks served customers with trails (simple in-memory map)
    private Map<String, Customer> servedMap = new HashMap<>();

    private Customer findServedCustomer(String name) {
        return servedMap.getOrDefault(name.toLowerCase(), null);
    }

    public void registerServed(Customer c) {
        servedMap.put(c.name.toLowerCase(), c);
    }

    private void printDishes(Stall s) {
        System.out.println("\n  Dishes at " + s.name + ":");
        System.out.printf("  %-3s %-20s %-10s %s%n", "#", "Name", "Price", "Effect");
        line();
        for (int i = 0; i < s.dishes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + s.dishes.get(i));
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  MAIN APPLICATION
// ─────────────────────────────────────────────────────────────

public class FestivalApp {

    static void banner() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║       🍽  MAGICAL FOOD FESTIVAL MANAGER  🍽              ║");
        System.out.println("║  Collections: ArrayList | Vector | Queue | Stack | LL   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    static void menu() {
        System.out.println("\n  ┌─────────────────────────────────┐");
        System.out.println("  │           MAIN MENU             │");
        System.out.println("  ├─────────────────────────────────┤");
        System.out.println("  │  1. Add New Food Stall          │");
        System.out.println("  │  2. Chef Manage Dishes (Vector) │");
        System.out.println("  │  3. Add Customer to Queue       │");
        System.out.println("  │  4. Serve Next Customer         │");
        System.out.println("  │  5. Track Stall Visit (Stack)   │");
        System.out.println("  │  6. Customer Goes Back (Stack)  │");
        System.out.println("  │  7. Daily History (LinkedList)  │");
        System.out.println("  │  8. Show Last N Visits          │");
        System.out.println("  │  9. View All Stalls             │");
        System.out.println("  │ 10. View Customer Queue         │");
        System.out.println("  │  0. Exit                        │");
        System.out.println("  └─────────────────────────────────┘");
        System.out.print("  Enter choice: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FestivalManager fm = new FestivalManager(sc);
        banner();

        int choice;
        do {
            menu();
            choice = Integer.parseInt(sc.nextLine().trim());
            switch (choice) {
                case 1:  fm.addStall();           break;
                case 2:  fm.manageDishes();        break;
                case 3:  fm.addCustomer();         break;
                case 4:  fm.serveNextCustomer();   break;
                case 5:  fm.trackVisit();          break;
                case 6:  fm.goBack();              break;
                case 7:  fm.showHistory();         break;
                case 8:  fm.showLastN();           break;
                case 9:  fm.listStalls();          break;
                case 10: fm.viewQueue();           break;
                case 0:
                    System.out.println("\n  ✨ Thank you for visiting the Magical Food Festival! ✨\n");
                    break;
                default:
                    System.out.println("  ✖ Invalid choice. Try again.");
            }
        } while (choice != 0);

        sc.close();
    }
}
