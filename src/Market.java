import java.util.*;

public class Market implements OperationsHappen{
    
    private static final Market market = new Market();

    private static final ArrayList<String> goodsKeys = new ArrayList<>(Arrays.asList(
            "Armory", "Potion", "Weaponry", "FireSpells", "IceSpells", "LightningSpells"
    ));
    private static final HashMap<String, ArrayList<String>> goodsKey2goods = new HashMap<>();

    static {
        // goodsKey2good
        goodsKey2goods.put("Armory", Pools.getArmoryPool());
        goodsKey2goods.put("Weaponry", Pools.getWeaponryPool());
        goodsKey2goods.put("Potion", Pools.getPotionPool());
        goodsKey2goods.putAll(Pools.getSpellPool());
//        goodsKey2goods.put("Spell", tileSpellPool);
    }

    private Market() {
        Help.printHelp();
    }

    public static Market getMarket() {
        return market;
    }

    public void showAllGoods() {
        showGoods("Armory");
        showGoods("Weaponry");
        showGoods("Spell");
        showGoods("Potion");
    }

    public void showGoods(String goodsKey){
        if (goodsKeys.contains(goodsKey)) {
            printGoodsKey(goodsKey);
            if (goodsKey.equals("Armory")) {
                TablePrinter.printFormattedTable(Pools.getArmoryValues());
            }
            else if (goodsKey.equals("Potion")) {
                TablePrinter.printFormattedTable(Pools.getPotionValues());
            }
            else if (goodsKey.equals("Weaponry")) {
                TablePrinter.printFormattedTable(Pools.getWeaponryValues());
            }
            else if (Pools.getSpellTypes().contains(goodsKey)) {
                TablePrinter.printFormattedTable(Pools.getSpellValues().get(goodsKey));
            }
            else {
                Utils.print_prefix("Impossible");
                System.err.printf("GoodsKey %s contained by goodsKey but not by spellTypes.\n", goodsKey);
            }
        } else if (Objects.equals(goodsKey, "Spell")) {
            for (String spellType: Pools.getSpellTypes()) {
                printGoodsKey(spellType);
                TablePrinter.printFormattedTable(Pools.getSpellValues().get(spellType));
            }
        } else {
            Utils.stateWarning();
            System.out.printf("No goods named %s\n", goodsKey);
        }
    }

    public void showGoodsKeys() {
        System.out.println("===== Types of Goods =====");
        Utils.printPoolAndNo(goodsKeys);
        System.out.println("===== Types of Goods =====");
    }

    public void showGoodsOfGoodsKeys(String goodsKey) {
        System.out.printf("===== Goods of %s =====\n", goodsKey);
        Utils.printPoolAndNo(goodsKey2goods.get(goodsKey));
        System.out.printf("===== Goods of %s =====\n", goodsKey);
    }

    private void printGoodsKey(String goodsKey) {
        System.out.printf("************ %s *************\n", goodsKey);
    }

    @Override
    public void printOperationInstructions() {
        Utils.print_instr_line();
        System.out.println("'q' to back or leave market;\n'i' to show your properties;\n" +
                "'g' to show all goods;\n't' to show types of goods;\n's' to sell goods.");
//            System.out.println("Enter number or full name to select goods.");
        System.out.println("Please use the number to choose type or goods.");
        Utils.print_instr_line();
    }

    public void printOperationInstructions(boolean keyDecided, String key) {
        if (keyDecided) {
            showGoods(key);
            showGoodsOfGoodsKeys(key);
        }
        else
            showGoodsKeys();
        printOperationInstructions();
    }

    public void trade(Player player) {

        String key = "";
        String goods;
        boolean keyDecided = false;
        int tradeGold;

        String input;

        System.out.println("Welcome to market.");

        for (Hero hero: player.getHeroes()) {

            showAllGoods();

            Utils.print_state_line();
            System.out.printf("Hero <%s>, you are trading with market.\n", hero.getName());
            System.out.printf("You have %d gold; Your level is %d.\n",
                    hero.getMemberValue("gold"), hero.getMemberValue("level"));
            Utils.print_state_line();

            showGoodsKeys();
            printOperationInstructions();

            while (true) {

                input = InputScanner.input_string();

                if(input.isEmpty())
                    continue;

                if (input.equalsIgnoreCase("q")) {
                    if (keyDecided) {
                        keyDecided = false;
                        printOperationInstructions(keyDecided, key);
                        continue;
                    }
                    else
                        break;
                }

                if (input.equalsIgnoreCase("i")) {
                    Utils.print_state_line();
                    System.out.println("State Information:");
                    player.showStateInformation();
                    Utils.print_state_line();
                    printOperationInstructions(keyDecided, key);
                    continue;
                }

                if (input.equalsIgnoreCase("g")) {
                    showAllGoods();
                    printOperationInstructions(keyDecided, key);
                    continue;
                }

                if (input.equalsIgnoreCase("t")) {
                    showGoodsKeys();
                    printOperationInstructions(keyDecided, key);
                    continue;
                }

                if (input.equalsIgnoreCase("s")) {
                    hero.sellGoods();
                    printOperationInstructions(keyDecided, key);
                    continue;
                }

                if (keyDecided) {
                    goods = BuyOrSelect.chooseByNoOrName(goodsKey2goods.get(key), input);
                    if (!BuyOrSelect.validChoice(goods)) {
                        System.out.println(BuyOrSelect.getErrorMessage(goods));
                        continue;
                    }
                    tradeGold = tradeWithHero(hero, key, goods);
                    if (tradeGold == 0) {
                        showGoods(key);
                        showGoodsOfGoodsKeys(key);
                        continue;
                    }
                    hero.spendGold(tradeGold);
                    Utils.print_state_line();
                    System.out.printf("Congratulations! %s bought a %s (%s).\n", hero.getName(), goods, key);
                    System.out.printf("Your gold has %d rest.\n", hero.getMemberValue("gold"));
                    Utils.print_state_line();

//                    keyDecided = false;
                    printOperationInstructions(keyDecided, key);
                }
                else {
                    key = BuyOrSelect.chooseByNoOrName(goodsKeys, input);
                    if (BuyOrSelect.validChoice(key)) {
                        keyDecided = true;
                        Utils.print_state_line();
                        System.out.printf("You are choosing %s.\n", key);
                        Utils.print_state_line();
                        printOperationInstructions(keyDecided, key);
                    }
                    else
                        System.out.println(BuyOrSelect.getErrorMessage(key));
                }
            }
        }
    }

    public int canBuy(Hero hero, String goodsType, String goodsName) {
        int heroValue;
        int requiredValue;

        heroValue = hero.getMemberValue("level");
        requiredValue = FactoryGoods.getRequiredLevel(goodsType, goodsName);
        if (heroValue < requiredValue) {
            Utils.stateWarning();
            System.out.printf("Your level %d is not satisfied (>= %d).\n", heroValue, requiredValue);
            return 0;
        }

        heroValue = hero.getMemberValue("gold");
        requiredValue = FactoryGoods.getPrice(goodsType, goodsName);
        if (heroValue < requiredValue) {
            Utils.stateWarning();
            System.out.printf("Your gold %d is not enough (price = %d).\n", heroValue, requiredValue);
            return 0;
        }
        return requiredValue;
    }

    public int tradeWithHero(Hero hero, String goodsType, String goodsName) {
        int price = canBuy(hero, goodsType, goodsName);
        if (price > 0) {
            hero.addProperties(goodsType, goodsName);
            return price;
        }
        return 0;
    }
}
