/*Buy and select function
* Can be simpler
* Better to be a static class*/


import java.util.ArrayList;
import java.util.HashMap;

public interface BuyOrSelect {

    HashMap<String, String> ErrorCases = initErrorCases();

    static HashMap<String, String> initErrorCases() {
        HashMap<String, String> ret = new HashMap<>();
        ret.put("<NOOR>", "Number out of range");
        ret.put("<UK>", "Unknown selections");
        ret.put("<II>", "Invalid integer.");
//        ret.put("", "No input.");
        return ret;
    }

    static boolean validChoice(String ret) {
        for (String errorCase: ErrorCases.keySet()) {
            if (ret.startsWith(errorCase))
                return false;
        }
        return true;
    }

    static String getErrorMessage(String ErrorKey) {
        if (!ErrorCases.containsKey(ErrorKey)) {
            System.err.printf("No ErrorKey %s\n", ErrorKey);
            return ErrorKey;
        }
        return ErrorCases.get(ErrorKey);
    }

    static String chooseByNoOrName(ArrayList<String> arr, String input) {

        int index;
        if (input.isEmpty()) {
            return "";
        }
        try {
            index = Utils.no2index(Integer.parseInt(input));
            if (0 <= index && index < arr.size())
                return arr.get(index);
            else
                return "<NOOR>";
        } catch (NumberFormatException e) {
            if (arr.contains(input))
                return input;
            else
                return "<UK>";
        }
    }

    static String chooseByNoOrName(ArrayList<String> arr) {
        String input = InputScanner.input_string();
        return chooseByNoOrName(arr, input);
    }

    static String chooseByNo(ArrayList<String> arr, String input) {
        int index;
        if (input.isEmpty()) {
            return "";
        }
        try {
            index = Utils.no2index(Integer.parseInt(input));
            if (0 <= index && index < arr.size())
                return arr.get(index);
            else
                return "<NOOR>";
        } catch (NumberFormatException e) {
            return "<II>";
        }
    }

    static String chooseByNo(ArrayList<String> arr) {
        String input = InputScanner.input_string();
        return chooseByNo(arr, input);
    }
}
