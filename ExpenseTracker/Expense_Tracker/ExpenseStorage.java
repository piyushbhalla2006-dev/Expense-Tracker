package Expense_Tracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads expenses from and saves expenses to expenses.csv.
 */
public class ExpenseStorage {
    private static final String FILE_NAME = "expenses.csv";

    public static ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> parts = CsvUtil.parseLine(line);

                if (parts.size() != 4) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                try {
                    String date = parts.get(0);
                    String description = parts.get(1);
                    double amount = Double.parseDouble(parts.get(2));
                    String category = parts.get(3);

                    expenses.add(new Expense(date, description, amount, category));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping row with an invalid amount.");
                }
            }
        } catch (IOException e) {
            // The file may not exist the first time the app runs.
        }

        return expenses;
    }

    public static void saveExpenses(ArrayList<Expense> expenses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.println(CsvUtil.toLine(
                        expense.getDate(),
                        expense.getDescription(),
                        String.valueOf(expense.getAmount()),
                        expense.getCategory()
                ));
            }
        } catch (IOException e) {
            System.out.println("Could not save expenses: " + e.getMessage());
        }
    }

    public static boolean exportToCsv(ArrayList<Expense> expenses) {
        String exportFile = "expenses_export.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(exportFile))) {
            writer.println("date,description,amount,category");

            for (Expense expense : expenses) {
                writer.println(CsvUtil.toLine(
                        expense.getDate(),
                        expense.getDescription(),
                        String.valueOf(expense.getAmount()),
                        expense.getCategory()
                ));
            }

            return true;
        } catch (IOException e) {
            System.out.println("Could not export expenses: " + e.getMessage());
            return false;
        }
    }
}
