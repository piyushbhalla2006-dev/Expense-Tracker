package Expense_Tracker;

/**
 * Represents one expense in the tracker.
 */
public class Expense {
    private String date;
    private String description;
    private double amount;
    private String category;

    public Expense(String date, String description, double amount, String category) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return date + " | " + description + " | $" +
                String.format("%.2f", amount) + " | " + category;
    }
}
