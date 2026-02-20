import java.util.*;

// ---------- DOMAIN CLASSES ----------

class Dish {
    String name;
    double price;
    int spiceLevel; // 1-5
    String magicalEffect;

    Dish(String name, double price, int spiceLevel, String magicalEffect) {
        this.name = name;
        this.price = price;
        this.spiceLevel = spiceLevel;
        this.magicalEffect = magicalEffect;
    }

    @Override
    public String toString() {
        return name + " (₹" + price + ", spice " + spiceLevel + ", effect: " + magicalEffect + ")";
    }
}

class Stall {
    int stallId;
    String name;
    String cuisineType;
    double rating;    // 1-5
    double avgPrice;  // approximate price per dish
    Vector<Dish> dishes = new Vector<>();

    Stall(int stallId, String name, String cuisineType, double rating, double avgPrice) {
        this.stallId = stallId;
        this.name = name;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.avgPrice = avgPrice;
    }

    @Override
    public String toString() {
        return "[" + stallId + "] " + name + " - " + cuisineType +
                " (Rating: " + rating + ", Avg ₹" + avgPrice + ")";
    }
}

class Customer {
    int customerId;
    String name;
    String mood;          // Happy, Adventurous, Confused
    int hungerLevel;      // 1-10
    String preferredCuisine;
    Stack<Stall> visitedStalls = new Stack<>();

    Customer(int customerId, String name, String mood, int hungerLevel, String preferredCuisine) {
        this.customerId = customerId;
        this.name = name;
        this.mood = mood;
        this.hungerLevel = hungerLevel;
        this.preferredCuisine = preferredCuisine;
    }

    @Override
    public String toString() {
        return "#" + customerId + " " + name + " [Mood: " + mood +
                ", Hunger: " + hungerLevel + ", Pref: " + preferredCuisine + "]";
    }
}

class VisitRecord {
    String customerName;
    String stallName;
    long visitNo;
    double amountSpent;

    VisitRecord(String customerName, String stallName, long visitNo, double amountSpent) {
        this.customerName = customerName;
        this.stallName = stallName;
        this.visitNo = visitNo;
        this.amountSpent = amountSpent;
    }

    @Override
    public String toString() {
        return "#" + visitNo + " " + customerName + " visited " + stallName +
                " and spent ₹" + amountSpent;
    }
}

// ---------- FESTIVAL MANAGER USING COLLECTIONS ----------

class FestivalManager {
    // All stalls in ArrayList
    private ArrayList<Stall> stalls = new ArrayList<>();

    // Customer line as Queue
    private Queue<Customer> customerQueue = new LinkedList<>();

    // Daily history as LinkedList
    private LinkedList<VisitRecord> history = new LinkedList<>();

    private long visitCounter = 1;

    // -------- STALL OPERATIONS --------
    public void addStall(Scanner sc) {
        System.out.print("Enter Stall ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Stall Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Cuisine Type: ");
        String cuisine = sc.nextLine();
        System.out.print("Enter Rating (1-5): ");
        double rating = sc.nextDouble();
        System.out.print("Enter Average Price: ");
        double avgPrice = sc.nextDouble();
        sc.nextLine();

        Stall stall = new Stall(id, name, cuisine, rating, avgPrice);
        stalls.add(stall);
        System.out.println("Stall added: " + stall);
    }

    private Stall findStallById(int id) {
        for (Stall s : stalls) {
            if (s.stallId == id) return s;
        }
        return null;
    }

    public void manageDishes(Scanner sc) {
        System.out.print("Enter Stall ID to manage dishes: ");
        int id = sc.nextInt();
        sc.nextLine();
        Stall stall = findStallById(id);
        if (stall == null) {
            System.out.println("Stall not found.");
            return;
        }

        int choice;
        do {
            System.out.println("\nDish Menu for " + stall.name);
            System.out.println("1. Add Dish");
            System.out.println("2. Remove Dish");
            System.out.println("3. View Dishes");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Dish name: ");
                    String dname = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Spice Level (1-5): ");
                    int spice = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Magical Effect: ");
                    String effect = sc.nextLine();
                    stall.dishes.add(new Dish(dname, price, spice, effect));
                    System.out.println("Dish added.");
                    break;
                case 2:
                    for (int i = 0; i < stall.dishes.size(); i++) {
                        System.out.println((i + 1) + ". " + stall.dishes.get(i));
                    }
                    System.out.print("Enter dish index to remove: ");
                    int idx = sc.nextInt();
                    sc.nextLine();
                    if (idx >= 1 && idx <= stall.dishes.size()) {
                        Dish removed = stall.dishes.remove(idx - 1);
                        System.out.println("Removed: " + removed);
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;
                case 3:
                    if (stall.dishes.isEmpty()) {
                        System.out.println("No dishes yet.");
                    } else {
                        for (Dish d : stall.dishes) {
                            System.out.println(" - " + d);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Back to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }

    // -------- CUSTOMER OPERATIONS --------

    public void addCustomerToQueue(Scanner sc) {
        System.out.print("Customer ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Mood (Happy/Adventurous/Confused): ");
        String mood = sc.nextLine();
        System.out.print("Hunger Level (1-10): ");
        int hunger = sc.nextInt();
        sc.nextLine();
        System.out.print("Preferred Cuisine: ");
        String pref = sc.nextLine();

        Customer c = new Customer(id, name, mood, hunger, pref);
        customerQueue.add(c);
        System.out.println("Customer added to queue: " + c);
    }

    private Stall recommendStall(Customer c) {
        if (stalls.isEmpty()) return null;

        List<Stall> matching = new ArrayList<>();
        for (Stall s : stalls) {
            if (s.cuisineType.equalsIgnoreCase(c.preferredCuisine)) {
                matching.add(s);
            }
        }
        List<Stall> baseList = matching.isEmpty() ? stalls : matching;

        Stall best = null;
        Random rand = new Random();

        switch (c.mood.toLowerCase()) {
            case "adventurous":
                for (Stall s : baseList) {
                    if (best == null || s.rating > best.rating) best = s;
                }
                break;
            case "confused":
                double bestDiff = Double.MAX_VALUE;
                for (Stall s : baseList) {
                    double diff = Math.abs(s.avgPrice - 200); // mid price anchor
                    if (diff < bestDiff) {
                        bestDiff = diff;
                        best = s;
                    }
                }
                break;
            case "happy":
            default:
                baseList.sort((a, b) -> Double.compare(b.rating, a.rating));
                int limit = Math.min(3, baseList.size());
                best = baseList.get(rand.nextInt(limit));
        }
        return best;
    }

    public void serveNextCustomer(Scanner sc) {
        if (customerQueue.isEmpty()) {
            System.out.println("No customers in queue.");
            return;
        }
        Customer c = customerQueue.poll();
        System.out.println("Serving: " + c);

        Stall recommended = recommendStall(c);
        if (recommended == null) {
            System.out.println("No stalls available.");
            return;
        }
        System.out.println("Recommended stall: " + recommended);
        c.visitedStalls.push(recommended);

        System.out.print("Approx amount spent at this stall: ");
        double amt = sc.nextDouble();
        sc.nextLine();

        history.add(new VisitRecord(c.name, recommended.name, visitCounter++, amt));
        System.out.println("Visit recorded.");
    }

    public void customerGoToAnotherStall(Scanner sc) {
        System.out.print("Enter Customer ID (existing served customer): ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Stall ID to move to: ");
        int stallId = sc.nextInt();
        sc.nextLine();

        Stall stall = findStallById(stallId);
        if (stall == null) {
            System.out.println("Stall not found.");
            return;
        }

        // Simple demo: create temp customer object (in real app you'd track customers globally)
        Customer temp = new Customer(id, "TempCustomer", "Happy", 5, stall.cuisineType);
        temp.visitedStalls.push(stall);
        System.out.println("Customer moved to stall: " + stall);
    }

    public void customerForgetAndGoBack() {
        // This is a conceptual demo: in full design you'd keep a map of id -> Customer.
        System.out.println("To fully support this, maintain a Map<Integer, Customer> and use their visitedStalls Stack.");
        System.out.println("Pop from the Stack to show the last stall they were at.");
    }

    // -------- HISTORY OPERATIONS --------

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No visits yet.");
            return;
        }
        for (VisitRecord vr : history) {
            System.out.println(vr);
        }
    }

    public void showLastNVisits(Scanner sc) {
        System.out.print("Enter N: ");
        int n = sc.nextInt();
        sc.nextLine();
        if (history.isEmpty()) {
            System.out.println("No visits yet.");
            return;
        }
        int start = Math.max(0, history.size() - n);
        for (int i = start; i < history.size(); i++) {
            System.out.println(history.get(i));
        }
    }

    public void showStalls() {
        if (stalls.isEmpty()) {
            System.out.println("No stalls created yet.");
            return;
        }
        for (Stall s : stalls) {
            System.out.println(s);
        }
    }
}

// ---------- MAIN APP WITH MENU ----------

public class FestivalApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FestivalManager manager = new FestivalManager();
        int choice;

        do {
            System.out.println("\n=== Magical Food Festival Manager ===");
            System.out.println("1. Add new food stall");
            System.out.println("2. Manage dishes for a stall (Vector)");
            System.out.println("3. Add customer to queue");
            System.out.println("4. Serve next customer with smart recommendation");
            System.out.println("5. Customer goes to another stall (Stack usage demo)");
            System.out.println("6. Customer forgets something and goes back (Stack)");
            System.out.println("7. Show daily festival history (LinkedList)");
            System.out.println("8. Show last N visits");
            System.out.println("9. Show all stalls");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manager.addStall(sc);
                    break;
                case 2:
                    manager.manageDishes(sc);
                    break;
                case 3:
                    manager.addCustomerToQueue(sc);
                    break;
                case 4:
                    manager.serveNextCustomer(sc);
                    break;
                case 5:
                    manager.customerGoToAnotherStall(sc);
                    break;
                case 6:
                    manager.customerForgetAndGoBack();
                    break;
                case 7:
                    manager.showHistory();
                    break;
                case 8:
                    manager.showLastNVisits(sc);
                    break;
                case 9:
                    manager.showStalls();
                    break;
                case 10:
                    System.out.println("Exiting. Thank you for visiting the Magical Food Festival!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 10);

        sc.close();
    }
}
