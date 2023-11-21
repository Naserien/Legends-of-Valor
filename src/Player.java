/*
* player class
* player position
* have heroes*/


import java.util.*;

public class Player implements Movable, StateInformation {

    private final String name;
    private final String color;
    private static final char symbol = 'X';

    private ArrayList<Hero> heroes;

    // pos
    private int atRow;
    private int atCol;
    public void setPos(int r, int c) {
        atRow = r;
        atCol = c;
    }
    public int[] getRC() {
        return new int[]{atRow, atCol};
    }

    public Player() {
        name = enterName("Legend");
        color = selectColor("CYAN");
    }

    @Override
    public String toString() {
        return Color.get_message_with_color(symbol, color);
    }

    public String enterName(String default_name) {
        System.out.printf("Please enter your name (or leave it empty to use the default name %s).\n", default_name);
        String name = InputScanner.input_string();
        if (name.isEmpty()) {
            name = default_name;
        }
        System.out.printf("Welcome %s!\n", name);
        return name;
    }

    public String selectColor(String default_color) {
        return Color.select_color(default_color);
    }

    public void selectHeroes(int default_number_of_heroes) {
        int maxN = Config.RecommendedMaxHeroes;
        Utils.print_instr_line();
        System.out.println("Please enter the number of heroes you want to fight with.");
        System.out.printf("Or use the default number of heroes %d.\n", default_number_of_heroes);
        System.out.printf("The recommended max number is %d.\n", maxN);
        Utils.print_instr_line();
        String input;
        int n;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty()) {
                n = default_number_of_heroes;
                break;
            }
            else {
                try {
                    n = Integer.parseInt(input);

                    if (n < 1) {
                        Utils.stateWarning();
                        System.out.println("At least 1 hero is needed");
                    }
                    else if (n > maxN) {
                        Utils.stateWarning();
                        System.out.println("Exceeded the recommended max heroes");
                    }
                    else
                        break;
                } catch (NumberFormatException e) {
                    Utils.stateError();
                    System.out.println("Please enter a valid number.");
                }
            }
        }
        System.out.printf("You will fight with %d heroes\n", n);
        heroes = HeroMonsterSelector.selectHeroes(n);
        Utils.print_state_line();
        System.out.println("Heroes chosen. Going to fight!");
        Utils.print_state_line();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public char getSymbol() {
        return symbol;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public int getNumHeroes() {
        return heroes.size();
    }

    @Override
    public boolean At(int r, int c) {
        return atRow == r && atCol == c;
    }

    @Override
    public boolean updatePos(String input) {
        if (input.isEmpty())
            return false;
        char direction = input.charAt(0);
        switch (direction) {
            case 'w':
                atRow --;
                return true;
            case 's':
                atRow ++;
                return true;
            case 'a':
                atCol --;
                return true;
            case 'd':
                atCol ++;
                return true;
            default:
                System.err.println("Invalid input to update position.");
                return false;
//                throw new IllegalArgumentException(String.format("Illegal operation or direction %c\n", direction));
        }
    }

    @Override
    public void showStateInformation() {
        for (Hero hero: heroes) {
            System.out.println("-----");
            System.out.println("Hero: " + hero);
            hero.showStateInformation();
        }
    }

    public void equip() {
        String input;
        Hero hero;
        Utils.print_state_line();
        showStateInformation();
        Utils.print_state_line();
        while (true) {
            // select hero
            if (heroes.size() > 1) {
                Utils.showWithNumber(heroes);
                Utils.print_instr_line();
                System.out.println("Please choose the heroes you want to equip (with number).");
                System.out.println("'q' to back.");
                Utils.print_instr_line();
                input = InputScanner.input_string();
                if (input.isEmpty())
                    continue;
                if (input.equalsIgnoreCase("q")) {
                    return;
                }
                if (input.equalsIgnoreCase("i")) {
                    continue;
                }
                hero = Utils.chooseWithNumber(heroes, input);
            }
            else
                hero = heroes.get(0);
            if (hero == null)
                continue;
            hero.usePotionOrEquip();
            Utils.print_state_line();
            showStateInformation();
            Utils.print_state_line();
        }
    }
}











