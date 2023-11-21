/*
* hero class
* many operations of hero*/


import java.io.PrintStream;
import java.lang.reflect.Parameter;
import java.util.*;

public class Hero extends HeroMonster implements StateInformation {

    private static final ArrayList<String> battleChoices = new ArrayList<>(Arrays.asList(
            "Attack, using the hero’s equipped weapon",
            "Cast a spell from the hero’s inventory",
            "Use a potion from the hero’s inventory",
            "Equip a weapon",
            "Equip a piece of armor"
    ));

    public static void showBattleChoices() {
        Utils.showWithNumber(battleChoices);
    }

    private int level;
    private int hp;
    private int mp;
    private int experience;
    private int gold;

    private int dexterity;
    private int strength;
    private int agility;

    private static ArrayList<String> skillKeys = new ArrayList<>(Arrays.asList("strength", "agility", "dexterity")); 
    private ArrayList<String> favorSkillKeys;

//    private static final ArrayList<String> attributeNames = new ArrayList<>(
//            Arrays.asList("hp", "level", "experience", "gold", "dexterity", "strength", "mana", "agility")
//    );

    public int getMemberValue(String key) {
        switch (key) {
            case "level":
                return level;
            case "hp":
                return hp;
            case "mp":
                return mp;
            case "experience":
                return experience;
            case "gold":
                return gold;
            case "dexterity":
                return dexterity;
            case "strength":
                return strength;
            case "agility":
                return agility;
            default:
                throw new IllegalArgumentException("Unsupported Member key: " + key);
        }
    }

    public void setSkillValue(String key, int value) {
        switch (key) {
            case "dexterity":
                dexterity = value;
                return;
            case "strength":
                strength = value;
                return;
            case "agility":
                agility = value;
                return;
            default:
                throw new IllegalArgumentException("Unsupported Member key: " + key);
        }
    }

    // inventory
    private final ArrayList<Armory> armories = new ArrayList<>();
    private final ArrayList<Spell> spells = new ArrayList<>();
    private final ArrayList<Potion> potions = new ArrayList<>();
    private final ArrayList<Weaponry> weaponries = new ArrayList<>();

    private final ArrayList<Armory> equippedArmories = new ArrayList<>();
    private final ArrayList<Weaponry> equippedWeaponries = new ArrayList<>();

    private final ArrayList<String> inventoryKeys = new ArrayList<>(Arrays.asList("Weapon", "Armory", "Potions", "Spells"));

    Hero(String type, String name, HashMap<String, Integer> values) {
        this.type = type;
        this.name = name;

        level = 1;
        hp = Config.FactorLevel2HP * level;
        mp = values.get("mana");
        dexterity = values.get("dexterity");
        strength = values.get("strength");
        agility = values.get("agility");
        experience = values.get("starting experience");
        gold = values.get("starting money");

        setFavors();
    }

    void setFavors() {
        switch (type) {
            case "warriors":
                favorSkillKeys = new ArrayList<>(Arrays.asList("strength", "agility"));
                break;
            case "Sorcerers":
                favorSkillKeys = new ArrayList<>(Arrays.asList("dexterity", "agility"));
                break;
            case "Paladins":
                favorSkillKeys = new ArrayList<>(Arrays.asList("strength", "dexterity"));
                break;
            default:
                favorSkillKeys = new ArrayList<>();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public String toString() {
        return "Hero{" +
                "type=" + type +
                ", name=" + name +
                ", level=" + level +
                ", hp=" + hp +
                ", mp=" + mp +
                ", gold=" + gold +
                ", experience=" + experience +
                ", strength=" + strength +
                ", dexterity=" + dexterity +
                ", agility=" + agility +
//                ", armories=" + armories +
//                ", spells=" + spells +
//                ", potions=" + potions +
//                ", weaponry=" + weaponries +
                '}';
    }

    public void addProperties(String goodsKey, String goods) {
//        System.out.println(goodsKey + " | " + goods);
        switch (goodsKey) {
            case "Armory":
                armories.add(FactoryArmory.createArmory(goods));
                return;
            case "Weaponry":
                weaponries.add(FactoryWeaponry.createWeaponry(goods));
                return;
            case "Potion":
                potions.add(FactoryPotion.createPotion(goods));
                return;
            default:
                if (goodsKey.contains("Spell")) {
                    spells.add(FactorySpell.createSpell(goodsKey, goods));
                    return;
                }
                Utils.stateError();
                System.err.printf("Unknown goods %s of type %s\n.", goods, goodsKey);
        }
    }

    @Override
    public void showStateInformation() {
        System.out.println("Your armories:");
        for (Armory armory : armories)
            System.out.println("    " + armory);
        System.out.println("Your equipped armories:");
        for (Armory armory : equippedArmories)
            System.out.println("    " + armory);
        System.out.println("Your Weaponry:");
        for (Weaponry weaponry : weaponries)
            System.out.println("    " + weaponry);
        System.out.println("Your equipped weaponry:");
        for (Weaponry weapon : equippedWeaponries)
            System.out.println("    " + weapon);
        System.out.println("Your spells:");
        for (Spell spell : spells)
            System.out.println("    " + spell);
        System.out.println("Your potions:");
        for (Potion potion : potions)
            System.out.println("    " + potion);
    }

    // subject functions
    public boolean spendGold(int price) {
        if (price > 0) {
            gold -= price;
            return true;
        }
        return false;
    }

    @Override
    public boolean succeedInDodging() {
//        System.out.printf("%d, %f, %f\n", agility, Config.FactorHeroAgilityToDodgeChance, agility * Config.FactorHeroAgilityToDodgeChance);
        return RandomMachine.get0to1() < agility * Config.FactorHeroAgilityToDodgeChance;
    }

    @Override
    public int getDamage() {
        // not use
        // we use getDamageByWeapon and getDamageBySpell
        return 0;
    }

    public int getDamageByWeapon() {
        int damage = strength;
        for (Weaponry weapon: equippedWeaponries) {
            damage += weapon.getDamage();
        }
        return (int) (damage * Config.FactorHeroDamageByWeaponAccordingToStrengthAndWeapon);
    }

    public int getDamageBySpell(Spell spell) {
        int spellDamage = spell.getDamage();
        return (int) (spellDamage * Config.FactorHeroDamageBySpellProportion  + dexterity * Config.FactorHeroDamageBySpellAccordingToDexterity * spellDamage);
    }

    @Override
    public boolean receiveHarmAndAlive(int damage) {
        for (Armory armory: equippedArmories)
            damage = armory.reduceDamage(damage);
        damage = Integer.max(damage, 0);
        damage = Config.HeroDamageAfter(damage);
        hp = Integer.max(0, hp - damage);
        Utils.print_state_line();
        System.out.printf("Hero <%s> receives %d damage.\n", name, damage);
        System.out.printf("Hero <%s>: HP = %d\n", name, hp);
        Utils.print_state_line();

        if (hp > 0)
            return true;
        else {
            System.out.printf("Hero %s die.\n", name);
            return false;
        }
    }

    private static <T> void showInventory(ArrayList<T> goods, String key) {
        System.out.println(key + ":");
        Utils.showWithNumber(goods);
    }

    private static <T> void showInventory(ArrayList<T> goods, String key, boolean upperLine, boolean lowerLine) {
        if (upperLine)
            Utils.print_state_line();
        System.out.println(key + ":");
        Utils.showWithNumber(goods);
        if (lowerLine)
            Utils.print_state_line();
    }

//    private static <T> void showInventory(ArrayList<T> goods, String key, String operation) {
//        Utils.print_instr_line();
//        Utils.showWithNumber(goods);
////        System.out.printf("Please choose a %s to %s (with the number).\n", key, operation);
////        System.out.println("Or type 'q' to operate again.");
//        Utils.print_instr_line();
//    }

    public void sellGoods() {
        Utils.print_state_line();
        System.out.println("You are selling goods.");
        Utils.print_state_line();
        showStateInformation();
        Utils.print_instr_line();
        Utils.showWithNumber(inventoryKeys);
        System.out.println("Please choose a type of goods with number");
        System.out.println("'q' to back.");
        Utils.print_instr_line();

        String input;

        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("q"))
                return;
            if (input.equalsIgnoreCase("i")) {
                Utils.print_state_line();
                showStateInformation();
                Utils.print_state_line();
                Utils.showWithNumber(inventoryKeys);
                System.out.println("Please choose a type of goods with number");
                continue;
            }
            input = Utils.chooseWithNumber(inventoryKeys, input);
            if (input == null) {
                showStateInformation();
                continue;
            }
            switch (input) {
                case "Armory":
                    showInventory(armories, "armory");
                    sellAGoods(armories);
                    return;
                case "Weapon":
                    showInventory(weaponries, "armory");
                    sellAGoods(weaponries);
                    return;
                case "Potions":
                    showInventory(potions, "potion");
                    sellAGoods(potions);
                    return;
                case "Spells":
                    showInventory(spells, "spell");
                    sellAGoods(spells);
                    return;
                default:
                    throw new RuntimeException("Impossible, wrong inventory key");
            }
        }
    }

    public <T extends Goods> void sellAGoods(ArrayList<T> arr) {
        String input;
        T goods;
        while (true) {
            input = InputScanner.input_string();
            if (input.equalsIgnoreCase("q"))
                return;
            if (input.equalsIgnoreCase("i")) {
                Utils.showWithNumber(arr);
                continue;
            }
            goods = Utils.chooseWithNumber(arr, input);
            if (goods == null) {
                Utils.stateWarning();
                System.out.println("Invalid choice");
                continue;
            }
            arr.remove(goods);
            Utils.print_state_line();
            System.out.printf("You sold %s; Got %d golds.\n", goods.getName(), goods.getCost() / 2);
            System.out.printf("Your golds: %d -> %d.\n", gold, gold += goods.getCost() / 2);
            Utils.print_state_line();
            return;
        }
    }

    public Spell chooseSpell() {
        Utils.print_state_line();
        System.out.println("You are choosing spells.");
        showInventory(spells, "spell", false, true);

        String input;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("q"))
                return null;
            if (input.equalsIgnoreCase("i")) {
                System.out.println(spells);
                continue;
            }
            try {
                int index = Utils.no2index(Integer.parseInt(input));
                if (0 <= index && index < spells.size()) {
                    if (mp > spells.get(index).getMPCost()) {
                        return spells.remove(index);
                    }
                    else {
                        System.out.printf("Your MP %d is not enough (MPCost = %d).\n", mp, spells.get(index).getMPCost());
                    }
                }
                else {
                    Utils.stateWarning();
                    System.out.println("The number you chose is out of range.");
                }
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Invalid operation.");
            }
        }
    }

    private void printUsePotionOrEquipInstruction() {
        Utils.print_state_line();
        System.out.printf("Hero %s is choosing to equip something or use potions.\n", name);
        Utils.print_state_line();
        showStateInformation();
        Utils.print_instr_line();
        Utils.showWithNumber(new ArrayList<>(Arrays.asList("Equip Armory", "Equip Weapons", "Use Potions")));
        System.out.println("Please choose the number or 'q' to back.");
        Utils.print_instr_line();
    }

    public void usePotionOrEquip() {
        printUsePotionOrEquipInstruction();
        String input;
        int index;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("i")) {
                showStateInformation();
                continue;
            }
            if (input.equalsIgnoreCase("q"))
                return;
            try {
                index = Utils.no2index(Integer.parseInt(input));
                switch (index) {
                    case 0:
                        equipArmor();
                        printUsePotionOrEquipInstruction();
                        continue;
                    case 1:
                        equipWeapon();
                        printUsePotionOrEquipInstruction();
                        continue;
                    case 2:
                        usePotion();
                        printUsePotionOrEquipInstruction();
                        continue;
                    default:
                        Utils.stateWarning();
                        System.out.println("Number out of range.");
                }
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Please enter a valid number;\n'q' to back;\n'i' for state information.");
            }
        }
    }

    public boolean usePotion() {

        Utils.print_state_line();
        System.out.println("You are using potions.");
        showInventory(potions, "potion", false, true);

        String input;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("q"))
                return false;
            if (input.equalsIgnoreCase("i")) {
                System.out.println(potions);
                continue;
            }
            try {
                int index = Utils.no2index(Integer.parseInt(input));
                if (0 <= index && index < potions.size()) {
                    useAPotion(potions.get(index));
                    potions.remove(index);
                    return true;
                }
                else {
                    Utils.stateWarning();
                    System.out.println("The number you chose is out of range.");
                }
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Invalid operation.");
            }
        }
    }

    public void useAPotion(Potion potion) {
        int increase = potion.getAttributeIncrease();
        Utils.print_state_line();
        System.out.printf("(Hero) %s used a potion %s and increased %s:\n", name, potion.getName(), potion.getAttributeAffected());
        for (String attr: potion.getAttributeAffected()) {
            updateByPotion(attr, increase);
        }
        Utils.print_state_line();
    }

    public void updateByPotion(String key, int delta) {
        switch (key) {
            case "Health":
                System.out.printf("HP: %d -> %d\n", hp, hp += delta);
                break;
            case "Mana":
                System.out.printf("MP: %d -> %d\n", mp, mp += delta);
                break;
            case "Dexterity":
                System.out.printf("Dexterity: %d -> %d\n", dexterity, dexterity += delta);
                break;
            case "Strength":
                System.out.printf("Strength: %d -> %d\n", strength, strength += delta);
                break;
            case "agility":
                System.out.printf("Agility: %d -> %d\n", agility, agility += delta);
                break;
        }
    }

    public boolean equipWeapon() {
        Utils.print_state_line();
        System.out.println("You are equipping weapons.");
        Utils.print_state_line();
        showInventory(weaponries, "weapon");
        showInventory(equippedWeaponries, "equippedWeapon");

        String input;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("q"))
                return false;
            if (input.equalsIgnoreCase("i")) {
                Utils.print_state_line();
                showInventory(weaponries, "weaponry");
                showInventory(equippedWeaponries, "equipped weaponry");
                Utils.print_state_line();
                continue;
            }
            try {
                int index = Utils.no2index(Integer.parseInt(input));
                if (0 <= index && index < weaponries.size()) {
                    return equipAWeapon(weaponries.get(index));
                }
                else {
                    Utils.stateWarning();
                    System.out.println("The number you chose is out of range.");
                }
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Invalid operation.");
            }
        }
    }

    public boolean equipAWeapon(Weaponry weapon) {
        int requiredHands = weapon.getRequiredHands();
        int usedHands = 0;
        for (Weaponry equippedWeapon: equippedWeaponries) {
            usedHands += equippedWeapon.getRequiredHands();
        }
        if (usedHands + requiredHands <= 2) {
            equippedWeaponries.add(weapon);
            weaponries.remove(weapon);
            System.out.printf("You are equipped with %s.\n", weapon.getName());
            Utils.print_state_line();
            System.out.printf("Weaponry: %s\n", weaponries);
            System.out.printf("Equipped weaponry: %s\n", equippedWeaponries);
            Utils.print_state_line();
            return true;
        }
        else {
            Utils.stateWarning();
            System.out.printf("You have used %d hands, you need %d more hands to use the new weapon.\n", usedHands, weapon.getRequiredHands());
            System.out.println("Do you want to doff weapons? Type 'y' if you want to.");
            String input = InputScanner.input_string();
            if (input.equalsIgnoreCase("y")) {
                if (doffAWeapon())
                    return equipAWeapon(weapon);
                else
                    return false;
            }
            else
                return false;
        }
    }

    public boolean doffAWeapon() {
        Weaponry temp;
        Utils.print_state_line();
        System.out.println("You are doffing weapons.");
        Utils.print_state_line();
        showInventory(equippedWeaponries, "equipped weaponry");
        String input;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("i")) {
                Utils.print_state_line();
                showInventory(weaponries, "weaponry");
                showInventory(equippedWeaponries, "equipped weaponry");
                Utils.print_state_line();
                continue;
            }
            if (input.equalsIgnoreCase("q"))
                return false;
            try {
                int index = Utils.no2index(Integer.parseInt(input));
                if (0 <= index && index < equippedWeaponries.size()) {
                    temp = equippedWeaponries.remove(index);
                    weaponries.add(temp);
                    Utils.print_state_line();
                    System.out.printf("You doffed a weapon %s.\n", temp.getName());
                    Utils.print_state_line();
                    return true;
                }
                else {
                    Utils.stateWarning();
                    System.out.println("The number you chose is out of range.");
                }
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Invalid operation.");
            }
        }
    }

    public boolean equipArmor() {

        Armory temp;

        Utils.print_state_line();
        System.out.println("You are equipping armory.");
        Utils.print_state_line();
        showInventory(armories, "armory");

        String input;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("q"))
                return false;
            if (input.equalsIgnoreCase("i")) {
                Utils.print_state_line();
                showInventory(armories, "armory");
                showInventory(equippedArmories, "equipped armory");
                Utils.print_state_line();
                continue;
            }
            try {
                int index = Utils.no2index(Integer.parseInt(input));
                if (0 <= index && index < armories.size()) {
                    temp = armories.remove(index);
                    equippedArmories.add(temp);
                    System.out.printf("You are equipped with %s now.\n", temp.getName());
                    Utils.print_state_line();
                    System.out.printf("Armory: %s\n", armories);
                    System.out.printf("Equipped armory: %s\n", equippedArmories);
                    Utils.print_state_line();
                    return true;
                }
                else {
                    Utils.stateWarning();
                    System.out.println("The number you chose is out of range.");
                }
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Invalid operation.");
            }
        }
    }

    public void recoverEveryRound() {
        hp = (int) (hp * Config.FactorMPHPRecover);
        mp = (int) (mp * Config.FactorMPHPRecover);
    }

    public int getExperienceToImprove() {
        return level * Config.FactorLevelToRequiredExperienceToLevelUp;
    }

    public int getExperienceFromMonster(Monster monster) {
        return Integer.max(0, monster.getMemberValue("level") - level) * 3 + 1;
    }

    public int getGoldFromMonster(Monster monster) {
        return (monster.getMemberValue("level")) * 100;
    }

    public void recoverAndImproveAfterFight (boolean alive, ArrayList<Monster> monsters) {
        double proportion = 1;
        if (!alive)
            proportion = Config.FactorDieRewardPortion;

        int addGold = 0;
        int addExperience = 0;

        for (Monster monster: monsters) {
            addGold += (int) (getGoldFromMonster(monster) * proportion);
            addExperience += (int) (getExperienceFromMonster(monster) * proportion);
        }

        Utils.print_state_line();
        if (alive)
            System.out.printf("(Hero) %s alive!\n", name);
        else
            System.out.printf("(Hero) %s can only get %f%% rewards.\n", name, Config.FactorDieRewardPortion * 100);
        System.out.printf("Gold: %d -> %d!\n", gold, gold + addGold);
        System.out.printf("Experience: %d -> %d!\n", experience, experience + addExperience);

        gold += addGold;
        experience += addExperience;

        int oriHP = hp;
        int oriMP = mp;

        hp = Integer.max(hp, level * Config.FactorLevel2HP);
        mp = (int) (mp * 1.1);

        System.out.printf("HP: %d -> %d!\n", oriHP, hp);
        System.out.printf("MP: %d -> %d!\n", oriMP, mp);
        Utils.print_state_line();

        improveLevel();
    }

    public void improveLevel() {
        int oriLevel = level;
        while (experience >= getExperienceToImprove()) {
            experience -= getExperienceToImprove();
            level += 1;
            for (String skillKey: skillKeys) {
                int oriValue = getMemberValue(skillKey);
                if (favorSkillKeys.contains(skillKey))
                    setSkillValue(skillKey, (int) (oriValue * (1 + Config.FactorSkillIncreaseProportion + Config.FactorSkillExtraIncreaseProportion)));
                else
                    setSkillValue(skillKey, (int) (oriValue * (1 + Config.FactorSkillIncreaseProportion)));
            }
        }
        if (level > oriLevel) {
            Utils.print_state_line();
            System.out.println("Congratulations!");
            System.out.printf("%s's level: %d -> %d!\n", name, oriLevel, level);
            System.out.printf("Now %s has %d experience.\n", name, experience);
            Utils.print_state_line();
        }
    }

}







































