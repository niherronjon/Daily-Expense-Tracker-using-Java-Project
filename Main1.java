//Daily Expense Tracker.
//raw code

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager expenseManager = new ExpenseManager();

        printWelcomeBox();

        while (true)
        {
            if (expenseManager.isLoggedIn()) {
                System.out.println("\n1. Add Expense");

                System.out.println("2. View Expenses");

                System.out.println("3. Logout");

                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); 
                
                // Consume newline

                switch (choice)
                {
                    case 1:
                        expenseManager.addExpense(scanner);
                        break;

                    case 2:
                        expenseManager.viewExpenses();
                        break;

                    case 3:
                        expenseManager.logoutUser();
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
            else
            {
                System.out.println("\n1. Register User");

                System.out.println("2. Login User");

                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();
                
                // Consume newline

                switch (choice)
                {
                    case 1:
                        printRegisterBox();
                        expenseManager.registerUser(scanner);
                        break;

                    case 2:
                        printLoginBox();
                        expenseManager.loginUser(scanner);
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        }
    }

    private static void printWelcomeBox()
    {
        String message = "Welcome to Daily Expense Tracker";
        int width = message.length() + 4;

        // Print top border
        System.out.println("-".repeat(width));

        // Print message with padding
        System.out.println("| " + message + " |");

        // Print bottom border
        System.out.println("-".repeat(width));
    }

    private static void printRegisterBox()
    {
        String message = "Register User";
        int width = message.length() + 4;

        // Print top border
        System.out.println("-".repeat(width));

        // Print message with padding
        System.out.println("| " + message + " |");

        // Print bottom border
        System.out.println("-".repeat(width));
    }

    private static void printLoginBox()
    {
        String message = "Login User";
        int width = message.length() + 4;

        // Print top border
        System.out.println("-".repeat(width));

        // Print message with padding
        System.out.println("| " + message + " |");

        // Print bottom border
        System.out.println("-".repeat(width));
    }
}

class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password)
    {
        return this.password.equals(password);
    }
}

class Expense implements Serializable
{
    private String category;
    private String description;
    private double amount;
    private LocalDate date;

    public Expense(String category, String description, double amount, LocalDate date)
    {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public double getAmount()
    {
        return amount;
    }

    @Override
    public String toString()
    {
        return "Date: " + date + ", Category: " + category + ", Description: " + description + ", Amount: $" + amount;
    }
}

class ExpenseManager
{
    private List<User> users = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private User loggedInUser = null;

    private static final String FILE_PATH = "expenses.dat";

    public ExpenseManager()
    {
        loadData();
    }

    public void registerUser(Scanner scanner)
    {
        System.out.print("Enter username: ");

        String username = scanner.nextLine();

        System.out.print("Enter password: ");

        String password = scanner.nextLine();

        users.add(new User(username, password));
        System.out.println("User registered successfully!\nNow, please login to continue.");
    }

    public void loginUser(Scanner scanner)
    {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for(User user : users)
        {
            if (user.getUsername().equals(username) && user.validatePassword(password))
            {
                loggedInUser = user;
                System.out.println("Login successful!\nNow you can add expenses.");
                return;
            }
        }
        System.out.println("Invalid username or password!");
    }

    public void addExpense(Scanner scanner)
    {
        if(loggedInUser == null)
        {
            System.out.println("Please login first!");
            return;
        }

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        // Consume newline

        System.out.print("Enter date (yyyy-mm-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        expenses.add(new Expense(category, description, amount, date));
        System.out.println("Expense added successfully!");
    }

    public void viewExpenses()
    {
        if(expenses.isEmpty())
        {
            System.out.println("No expenses recorded yet.");
        }
        else
        {
            System.out.println("\nExpenses:");
            for(Expense expense : expenses)
            {
                System.out.println(expense);
            }
        }
    }

    public void logoutUser()
    {
        loggedInUser = null;
        System.out.println("You have logged out successfully!");
    }

    public boolean isLoggedIn()
    {
        return loggedInUser != null;
    }

    public void saveData()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH)))
        {
            oos.writeObject(users);
            oos.writeObject(expenses);
            System.out.println("Data saved successfully!");
        }
        catch (IOException e)
        {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData()
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH)))
        {
            users = (List<User>) ois.readObject();
            expenses = (List<Expense>) ois.readObject();
            System.out.println("Data loaded successfully!");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No previous data found.");
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}



/* Output :

------------------------------------
| Welcome to Daily Expense Tracker |
------------------------------------

1. Register User
2. Login User
Choose an option: 1
-----------------
| Register User |
-----------------
Enter username: n
Enter password: n
User registered successfully!
Now, please login to continue.

1. Register User
2. Login User
Choose an option: 2
--------------
| Login User |
--------------
Enter username: n
Enter password: n
Login successful!
Now you can add expenses.

1. Add Expense
2. View Expenses
3. Logout
Choose an option: 1
Enter category: food
Enter description: vat
Enter amount: 34
Enter date (yyyy-mm-dd): 1211-10-08
Expense added successfully!

1. Add Expense
2. View Expenses
3. Logout
Choose an option: 2

Expenses:
Date: 1211-10-08, Category: food, Description: vat, Amount: $34.0

1. Add Expense
2. View Expenses
3. Logout
Choose an option: 1
Enter category: fd
Enter description: fdfer
Enter amount: 32
Enter date (yyyy-mm-dd): 1216-10-03
Expense added successfully!

1. Add Expense
2. View Expenses
3. Logout
Choose an option: 2

Expenses:
Date: 1211-10-08, Category: food, Description: vat, Amount: $34.0
Date: 1216-10-03, Category: fd, Description: fdfer, Amount: $32.0

1. Add Expense
2. View Expenses
3. Logout
Choose an option:


*/
