package Expense_Tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Small helper for writing and reading comma-separated values.
 * Fields containing commas or quotation marks are quoted safely.
 */
public class CsvUtil {

    public static String escape(String value) {
        if (value == null) {
            value = "";
        }

        if (value.contains(",") || value.contains("\"") ||
                value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }

    public static String toLine(String... values) {
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                line.append(",");
            }
            line.append(escape(values[i]));
        }

        return line.toString();
    }

    public static List<String> parseLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == '"') {
                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (character == ',' && !insideQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(character);
            }
        }

        values.add(current.toString());
        return values;
    }
}
