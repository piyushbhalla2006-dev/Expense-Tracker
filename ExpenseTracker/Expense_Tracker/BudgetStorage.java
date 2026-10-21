package Expense_Tracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Saves each monthly budget in budgets.csv.
 * Example row: 2026-10,800.00
 */
public class BudgetStorage {
    private static final String FILE_NAME = "budgets.csv";

    public static Map<String, Double> loadBudgets() {
        Map<String, Double> budgets = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> parts = CsvUtil.parseLine(line);

                if (parts.size() != 2) {
                    continue;
                }

                try {
                    budgets.put(parts.get(0), Double.parseDouble(parts.get(1)));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping budget row with invalid amount.");
                }
            }
        } catch (IOException e) {
            // No budgets have been saved yet.
        }

        return budgets;
    }

    public static void saveBudgets(Map<String, Double> budgets) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Double> entry : budgets.entrySet()) {
                writer.println(CsvUtil.toLine(
                        entry.getKey(),
                        String.valueOf(entry.getValue())
                ));
            }
        } catch (IOException e) {
            System.out.println("Could not save budgets: " + e.getMessage());
        }
    }
}
