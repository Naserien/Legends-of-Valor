/*formatted printer*/


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TablePrinter {

    public static String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    // Helper method to center text within a given width
    private static String center(String text, int width) {
        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - leftPadding - text.length();
        return repeatChar(' ', leftPadding) + text + repeatChar(' ', rightPadding);
    }

    // T is Integer or String in this project
    public static <T> void printFormattedTable(HashMap<String, HashMap<String, T>> data) {
        // Calculate the width for the "No" column based on the size of the data set
        int noColumnWidth = Math.max("No".length(), String.valueOf(data.size()).length());

        // Calculate the maximum length of the first column (Name)
        int nameColumnWidth = "Name".length();
        for (String name : data.keySet()) {
            nameColumnWidth = Math.max(nameColumnWidth, name.length());
        }

        // Calculate the maximum widths of other columns
        HashMap<String, Integer> columnWidths = new HashMap<>();
        for (Map.Entry<String, HashMap<String, T>> entry : data.entrySet()) {
            for (Map.Entry<String, T> innerEntry : entry.getValue().entrySet()) {
                String key = innerEntry.getKey();
                T value = innerEntry.getValue();
                columnWidths.putIfAbsent(key, key.length());
                columnWidths.put(key, Math.max(columnWidths.get(key), value.toString().length()));
            }
        }

        // Create the top and bottom border
        String topAndBottomBorder = "+";
        for (int i = 0; i < noColumnWidth + 2; i++) {
            topAndBottomBorder += "-";
        }
        topAndBottomBorder += "-";
        for (int i = 0; i < nameColumnWidth + 1; i++) {
            topAndBottomBorder += "-";
        }
        for (Integer width : columnWidths.values()) {
            topAndBottomBorder += "-";
            for (int i = 0; i < width + 1; i++) {
                topAndBottomBorder += "-";
            }
        }
        topAndBottomBorder += "+";

        // Print the top border
        System.out.println(topAndBottomBorder);

        // Print the header row
        String header = "| " + center("No", noColumnWidth) + " |";
        header += center("Name", nameColumnWidth) + " |";
        Set<String> keys = columnWidths.keySet();
        for (String key : keys) {
            header += center(key, columnWidths.get(key)) + " |";
        }
        System.out.println(header);

        // Print the separator
        String separator = "|";
        for (int i = 0; i < noColumnWidth + 2; i++) {
            separator += "-";
        }
        separator += "+";
        for (int i = 0; i < nameColumnWidth + 1; i++) {
            separator += "-";
        }
        for (Integer width : columnWidths.values()) {
            separator += "+";
            for (int i = 0; i < width + 1; i++) {
                separator += "-";
            }
        }
        separator += "|";
        System.out.println(separator);

        // Print the data rows
        int rowNum = 1; // Row numbers start at 1
        String value;
        for (Map.Entry<String, HashMap<String, T>> entry : data.entrySet()) {
            String row = "| " + center(String.valueOf(rowNum++), noColumnWidth) + " |";
            row += center(entry.getKey(), nameColumnWidth) + " |";
            for (String key : keys) {
                if (entry.getValue().containsKey(key))
                    value = entry.getValue().get(key).toString();
                else
                    value = " ";
                row += center(value, columnWidths.get(key)) + " |";
            }
            System.out.println(row);
        }

        // Print the bottom border
        System.out.println(topAndBottomBorder);
    }

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> data = new HashMap<>();
        // Populate the data for demonstration purposes
        data.put("Alice", new HashMap<>());
        data.get("Alice").put("Math", 90);
        data.get("Alice").put("Science", 95);

        data.put("Bob", new HashMap<>());
        data.get("Bob").put("Math", 85);
        data.get("Bob").put("Science", 82);

        // print
        printFormattedTable(data);
    }
}
