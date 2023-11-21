/*used to select heroes and generate monsters*/

import java.nio.channels.ByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HeroMonsterSelector implements BuyOrSelect {

    private static final ArrayList<String> heroTypes = Pools.getHeroTypes();
    private static final ArrayList<String> monsterTypes = Pools.getMonsterTypes();

    private static final HashMap<String, ArrayList<String>> heroPool = Pools.getHeroPool();
    private static final HashMap<String, ArrayList<String>> monsterPool = Pools.getMonsterPool();

    public static HashMap<String, HashMap<String, Integer>> getValueOfType(String type) {
        if (Pools.getHeroValues().containsKey(type)) {
            return Pools.getHeroValues().get(type);
        }
        else if (Pools.getMonsterValues().containsKey(type)) {
            return Pools.getMonsterValues().get(type);
        }
        else {
            // inaccessible
            Utils.stateError();
            System.err.printf("Unknown type of heroes or monsters: %s.\n", type);
            return null;
        }
    }

    // table print
    public static void printValueOfType(HashMap<String, HashMap<String, Integer>> values) {
        if (values == null) {
            Utils.stateError();
            System.err.println("Unable to print null values");
        }
        else {
            TablePrinter.printFormattedTable(values);
        }
    }

    public static void printValueOfType(String type) {
        printValueOfType(getValueOfType(type));
    }

    // ======================= select heroes =======================
    public static ArrayList<Hero> selectHeroes(int n) {
        ArrayList<Hero> ret = new ArrayList<>();
        for (int i=0; i<n; i++) {
            Utils.print_state_line();
            System.out.printf("You are choosing the %d-th hero\n", i + 1);
            Utils.print_state_line();
            ret.add(selectAHero());
        }
        return ret;
    }

    public static Hero selectAHero() {
        String heroType;
        String heroName;
        while (true) {
            Utils.print_instr_line();
            System.out.println("Please choose a type of hero from:");
            Utils.printPoolAndNo(heroTypes);
            System.out.println("You can enter the full name or use the number.");
            System.out.println("Or leave it empty to randomly choose a hero.");
            Utils.print_instr_line();

            heroType = BuyOrSelect.chooseByNoOrName(heroTypes);
            if (heroType.isEmpty()) {
                HashMap<String, String> tm = _randomChoose1(heroTypes, heroPool);
                return _selectAHero(tm.get("type"), tm.get("name"));
            }
            else if (heroType.startsWith("<NOOR>")) {
                Utils.stateWarning();
                System.out.println(BuyOrSelect.getErrorMessage("<NOOR>"));
                continue;
            }

            else if (heroType.startsWith("<UK>")) {
                Utils.stateWarning();
                System.out.println(BuyOrSelect.getErrorMessage("<UK>") + " heroType.");
                continue;
            }

            Utils.print_state_line();
            System.out.printf("You chose %s.\n", heroType);
            printValueOfType(heroType);
            Utils.print_state_line();

            Utils.print_instr_line();
            System.out.println("Please choose a hero.");
            System.out.println("Please enter the number.");
            System.out.printf("Or leave it empty to randomly choose a %s.\n", heroType);
            Utils.print_instr_line();

            ArrayList<String> heroNames = heroPool.get(heroType);
            while (true) {
                heroName = BuyOrSelect.chooseByNo(heroNames);

                if (heroName.isEmpty()) {
                    heroName = RandomMachine.selectRandomElements(heroNames, 1).get(0);
                    return _selectAHero(heroType, heroName);
                }
                else if (heroName.startsWith("<NOOR>")) {
                    Utils.stateWarning();
                    System.out.println(BuyOrSelect.getErrorMessage("<NOOR>"));
                    continue;
                } else if (heroName.startsWith("<II>")) {
                    System.out.println(BuyOrSelect.getErrorMessage("<II>"));
                    continue;
                }

                return _selectAHero(heroType, heroName);
            }
        }
    }

    private static Hero _selectAHero(String type, String name) {
        System.out.printf("You chose %s.\n", name);
        return FactoryHero.createHero(type, name);
    }

    private static HashMap<String, String> _randomChoose1(ArrayList<String> types, HashMap<String, ArrayList<String>> type2names) {
        int index = RandomMachine.chooseInt(types.size());
        String type = types.get(index);
        String name = RandomMachine.selectRandomElements(type2names.get(type), 1).get(0);
        HashMap<String, String> ret = new HashMap<>();
        ret.put("type", type);
        ret.put("name", name);
        return ret;
    }

    // ======================= Generate Monsters =======================
//    // random, may very high level
//    public static ArrayList<Monster> generateMonsters(int n) {
//        ArrayList<Monster> ret = new ArrayList<>();
//        for (int i=0; i<n; i++) {
//            HashMap<String, String> tm = _randomChoose1(monsterTypes, monsterPool);
//            ret.add(FactoryMonster.createMonster(tm.get("type"), tm.get("name")));
//            // change to get according to level
//            // level > ... return
//        }
//        return ret;
//    }

    public static ArrayList<Monster> generateMonstersHard(int sumHeroLevel) {
        ArrayList<Monster> ret = new ArrayList<>();
        int sumLevel = 0;
        Monster monster;
        while (true) {
            HashMap<String, String> tm = _randomChoose1(monsterTypes, monsterPool);
            monster = FactoryMonster.createMonster(tm.get("type"), tm.get("name"));
            if (monster.getMemberValue("level") > sumHeroLevel * 2)
                continue;
            ret.add(monster);
            sumLevel += monster.getMemberValue("level");
            if (sumLevel > sumHeroLevel)
                break;
        }
        return ret;
    }

    public static ArrayList<Monster> generateMonsters(int sumHeroLevel) {
        ArrayList<Monster> ret = new ArrayList<>();
        int sumLevel = 0;
        Monster monster;
        while (true) {
            HashMap<String, String> tm = _randomChoose1(monsterTypes, monsterPool);
            monster = FactoryMonster.createMonster(tm.get("type"), tm.get("name"));
            if (monster.getMemberValue("level") > sumHeroLevel * 2)
                continue;
            if (sumLevel + monster.getMemberValue("level") > sumHeroLevel) {
                if (!ret.isEmpty())
                    return ret;
                else
                    continue;
            }
            ret.add(monster);
            sumLevel += monster.getMemberValue("level");
        }
    }

    // ================== some utils, may be moved to Utils in the future. =====================
    public static String formatStringToTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
