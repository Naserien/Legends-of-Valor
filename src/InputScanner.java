/*
* based on a scanner
* input int, char, string
* if receive "exit" or "quit" (including Uppercase), system will exit
* Utilize regex to parse String to n integers
* */

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputScanner {

    private static final Scanner scanner = new Scanner(System.in);

    public static String input_string() {
        String input = scanner.nextLine();
        check_quit(input);
        while (check_help(input)) {
            input = scanner.nextLine();
        }
        return input;
    }

    public static int input_int() {
        while (true) {
            String input = input_string();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Please enter a valid integer!");
            }
        }
    }

    public static char input_char() {
        String temp = input_string();
        while (true) {
            if (Objects.equals(temp, "")) {
                Utils.stateWarning();
                System.out.println("Input Required!!!");
                temp = input_string();
                continue;
            }
            return temp.charAt(0);
        }
    }

    public static int[] parse_n_none_negative_integers(String input_content, int n) {
        int count = 0;
        int[] integers = new int[n];
        if (input_content.contains("-")) {
            System.out.println("***********************\n" +
                    "[Attention] We do not support negative number !!!\n" +
                    "***********************");
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input_content);

        // Number Input

        while (matcher.find()) {
            if (count == n) {
                count = n + 1;
                break;
            }
            int number = Integer.parseInt(matcher.group());
            integers[count] = number;
            count ++;
        }

        if (count == n) {
            return integers;
        } else {
            return null;
        }
    }

    public static int[] input_n_none_negative_integers(int n) {
        String input_content;
        int[] integers;

        while (true) {

            input_content = input_string();

            integers = parse_n_none_negative_integers(input_content, n);
            if (integers != null)
                return integers;
            else {
                Utils.stateWarning();
                System.out.printf("Please input %d integers.\n", n);
            }
        }
    }

    public static void check_quit(String input) {
        if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit"))
            System.exit(0);
    }

    public static boolean check_help(String input) {
        if (input.equalsIgnoreCase("help")) {
            Help.printHelp();
            return true;
        }
        return false;
    }
}
