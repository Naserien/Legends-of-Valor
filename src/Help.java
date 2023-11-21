/*print help*/


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Help {

    static Map<String, String> operationExplanations = new LinkedHashMap<>();
    static {
        operationExplanations.put("W/w", "Move Up");
        operationExplanations.put("A/a", "Move Left");
        operationExplanations.put("S/s", "Move Down");
        operationExplanations.put("D/d", "Move Right");
        operationExplanations.put("I/i", "Show State Information");
        operationExplanations.put("Q/q", "Back");

        operationExplanations.put("E/e", "Equip Armory, Weapons or Use Potions.");

        operationExplanations.put("M/m", "Enter Market");
        operationExplanations.put("S/s (in a market)", "Sell goods.");
        operationExplanations.put("G/g", "Show All Goods");
        operationExplanations.put("R/r", "Return to the Start Menu.");

        operationExplanations.put("Quit/Exit", "Quit Game");
        operationExplanations.put("Help", "Print me again!");
    }
    public static void printHelp() {
        Utils.print_instr_line();
        System.out.println("---------------------");
        System.out.println("Settings");
        System.out.println("Ice spell: reduces the damage of the target\n" +
                "Fire spell: reduces the defense of the target\n" +
                "Lightning spell: reduces the dodge chance of the target");
        System.out.println("---------------------");
        System.out.println("Operations:");
        for (Map.Entry<String, String> op2exp: operationExplanations.entrySet()) {
            System.out.printf("%s: %s\n", op2exp.getKey(), op2exp.getValue());
        }
        System.out.println("---------------------");
        Utils.print_prefix("NOTICE");
        System.out.println("Compatible with any case.");
        System.out.println("---------------------");
        Utils.print_instr_line();
    }
}
