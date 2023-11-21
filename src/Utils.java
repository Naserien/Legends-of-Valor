import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Utils {

    static String COLOR_INDEX = "YELLOW";
    static String COLOR_STATE = "BLUE";
    static String COLOR_INSTR = "GREEN";

    static String COLOR_WARNING = "CYAN";
    static String COLOR_ERROR = "RED";

    public static String add_double_slash_line(String messages) {
        return "==================== " + messages + " ====================";
    }

    public static void print_state_line() {
        System.out.println(Color.get_message_with_color(add_double_slash_line("STATE"), COLOR_STATE));
    }

    public static void print_instr_line() {
        System.out.println(Color.get_message_with_color(add_double_slash_line("Instr"), COLOR_INSTR));
    }

    public static void print_prefix(String messages) {
        System.out.printf("[%s] ", messages);
    }

    public static void stateWarning() {
        print_prefix(Color.get_message_with_color("WARNING", COLOR_WARNING));
    }

    public static void stateError() {
        print_prefix(Color.get_message_with_color("ERROR", COLOR_ERROR));
    }

    public static int[] get_board_width_height(int h, int w) {
        print_instr_line();
        System.out.printf("The default size of the board is [%d × %d]\n", h, w);
        System.out.println("Type 'y' if you want to set yours.");
        print_instr_line();
        String choice = InputScanner.input_string();
        if (choice.contains("y")) {
            System.out.print("Height: ");
            w = InputScanner.input_int();
            System.out.print("Width: ");
            h = InputScanner.input_int();
        }
        return new int[]{w, h};
    }

    public static <T> boolean ArrContains(T[] arr, T targetValue) {
        for(T s: arr){
            if(s.equals(targetValue)){
                return true;
            }
        }
        return false;
    }

    public static int no2index(int no) {
        return no - 1;
    }

    // Utility method to add a hero to the pool
    public static void addToPool(HashMap<String, ArrayList<String>> pool, String type, String name) {
        pool.computeIfAbsent(type, k -> new ArrayList<>()).add(name);
    }

    public static void printPoolAndNo(ArrayList<String> pool) {
        for (int i=0; i<pool.size(); i++) {
            System.out.println(i+1 + ". " + pool.get(i));
        }
    }

    public static <T> void showWithNumber(ArrayList<T> arr) {
        if (arr.isEmpty())
            System.out.println("[]");
        for (int i=0; i<arr.size(); i++) {
            System.out.printf("%d. %s\n", i+1, arr.get(i));
        }
    }

    public static <T> T chooseWithNumber(ArrayList<T> arr, String input) {
        int index;
        try {
            index = Utils.no2index(Integer.parseInt(input));
            if (0 <= index && index < arr.size())
                return arr.get(index);
            else {
                Utils.stateWarning();
                System.out.println("Number out of range.");
            }
        } catch (NumberFormatException e) {
            Utils.stateWarning();
            System.out.println("Invalid number");
        }
        return null;
    }

    public static ArrayList<Integer> getRangeArrayList(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(i);
        }
        return result;
    }

}
