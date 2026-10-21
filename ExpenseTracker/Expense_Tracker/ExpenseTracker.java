package Expense_Tracker;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Console-based personal expense tracker.
 */
public class ExpenseTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load existing information when the app starts.
        ArrayList<Expense> expenses = ExpenseStorage.loadExpenses();
        Map<String, Double> budgets = BudgetStorage.loadBudgets();

        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("Choose an option: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addExpense(scanner, expenses);
                    break;
                case "2":
                    viewExpenses(expenses);
                    break;
                case "3":
                    deleteExpense(scanner, expenses);
                    break;
                case "4":
                    showTotal(expenses);
                    break;
                case "5":
                    showCategoryTotals(expenses);
                    break;
                case "6":
                    manageMonthlyBudget(scanner, expenses, budgets);
                    break;
                case "7":
                    if (ExpenseStorage.exportToCsv(expenses)) {
                        System.out.println("Exported to expenses_export.csv");
                    }
                    break;
                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Please choose a number from the menu.");
            }
        }

        // Save data before closing.
        ExpenseStorage.saveExpenses(expenses);
        BudgetStorage.saveBudgets(budgets);
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== Expense Tracker ===");
        System.out.println("1. Add expense");
        System.out.println("2. View all expenses");
        System.out.println("3. Delete expense");
        System.out.println("4. View total spending");
        System.out.println("5. Spending by category");
        System.out.println("6. Set/view monthly budget");
        System.out.println("7. Export expenses to CSV");
        System.out.println("0. Exit");
    }

    private static void addExpense(Scanner scanner, ArrayList<Expense> expenses) {
        String date = askForDate(scanner);
        if (date == null) {
            return;
        }

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Description cannot be empty.");
            return;
        }

        System.out.print("Amount: $");
        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        System.out.print("Category (e.g. Food, Transport, Bills): ");
        String category = scanner.nextLine().trim();

        if (category.isEmpty()) {
            category = "Uncategorized";
        }

        expenses.add(new Expense(date, description, amount, category));
        ExpenseStorage.saveExpenses(expenses);
        System.out.println("Expense added.");
    }

    private static String askForDate(Scanner scanner) {
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        try {
            LocalDate.parse(date);
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
            return null;
        }
    }

    private static void viewExpenses(ArrayList<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        for (int i = 0; i < expenses.size(); i++) {
            System.out.println(i + ". " + expenses.get(i));
        }
    }

    private static void deleteExpense(Scanner scanner, ArrayList<Expense> expenses) {
        viewExpenses(expenses);

        if (expenses.isEmpty()) {
            return;
        }

        System.out.print("Enter the number of the expense to delete: ");
        int index;

        try {
            index = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a whole number.");
            return;
        }

        if (index < 0 || index >= expenses.size()) {
            System.out.println("That expense number does not exist.");
            return;
        }

        expenses.remove(index);
        ExpenseStorage.saveExpenses(expenses);
        System.out.println("Expense deleted.");
    }

    private static void showTotal(ArrayList<Expense> expenses) {
        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        System.out.printf("Total spending: $%.2f%n", total);
    }

    private static void showCategoryTotals(ArrayList<Expense> expenses) {
        Map<String, Double> totals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double currentTotal = totals.getOrDefault(category, 0.0);
            totals.put(category, currentTotal + expense.getAmount());
        }

        if (totals.isEmpty()) {
            System.out.println("No expenses to summarize.");
            return;
        }

        System.out.println("\n--- Spending by Category ---");
        for (String category : totals.keySet()) {
            System.out.printf("%s: $%.2f%n", category, totals.get(category));
        }
    }

    private static void manageMonthlyBudget(
            Scanner scanner,
            ArrayList<Expense> expenses,
            Map<String, Double> budgets) {

        System.out.print("Month (YYYY-MM): ");
        String month = scanner.nextLine().trim();

        try {
            YearMonth.parse(month);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid month. Use YYYY-MM.");
            return;
        }

        System.out.print("Budget amount (leave blank to view): ");
        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);

                if (amount < 0) {
                    System.out.println("Budget cannot be negative.");
                    return;
                }

                budgets.put(month, amount);
                BudgetStorage.saveBudgets(budgets);
                System.out.println("Budget saved.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid budget amount.");
                return;
            }
        }

        double spent = 0;
        for (Expense expense : expenses) {
            if (expense.getDate().startsWith(month)) {
                spent += expense.getAmount();
            }
        }

        if (!budgets.containsKey(month)) {
            System.out.printf("Spending in %s: $%.2f. No budget set yet.%n", month, spent);
            return;
        }

        double budget = budgets.get(month);
        System.out.printf("Budget: $%.2f | Spent: $%.2f | Remaining: $%.2f%n",
                budget, spent, budget - spent);

        if (spent > budget) {
            System.out.println("You are over budget.");
        }
    }
}
