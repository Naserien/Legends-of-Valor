/*
* read files once*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Pools {

    //// Armory
    // values
    private static final HashMap<String, HashMap<String, Integer>> armoryValues =
            FileReader.readStats(Config.getFilePath("Armory"));
    public static HashMap<String, HashMap<String, Integer>> getArmoryValues() {
        return armoryValues;
    }
    // pool
    private static final ArrayList<String> armoryPool = Pools.values2pool(armoryValues);
    public static ArrayList<String> getArmoryPool() {
        return armoryPool;
    }

    //// Weaponry
    // values
    private static final HashMap<String, HashMap<String, Integer>> weaponryValues =
            FileReader.readStats(Config.getFilePath("Weaponry"));
    public static HashMap<String, HashMap<String, Integer>> getWeaponryValues() {
        return weaponryValues;
    }
    // pool
    private static final ArrayList<String> weaponryPool = Pools.values2pool(weaponryValues);
    public static ArrayList<String> getWeaponryPool() {
        return weaponryPool;
    }

    //// Potion
    // values
    private static final HashMap<String, HashMap<String, String>> potionValues =
            FileReader.readStatsToString(Config.getFilePath("Potions"));
    public static HashMap<String, HashMap<String, String>> getPotionValues() {
        return potionValues;
    }
    // pool
    private static final ArrayList<String> potionPool = Pools.values2pool(potionValues);
    public static ArrayList<String> getPotionPool() {
        return potionPool;
    }

    //// Spell: 2 layer
    // types
    private static final ArrayList<String> spellTypes = new ArrayList<>(Arrays.asList("FireSpells", "IceSpells", "LightningSpells"));
    public static ArrayList<String> getSpellTypes() {
        return spellTypes;
    }
    // values
    private static final HashMap<String, HashMap<String, HashMap<String, Integer>>> spellValues = new HashMap<>();
    public static HashMap<String, HashMap<String, HashMap<String, Integer>>> getSpellValues() {
        return spellValues;
    }
    // pool
    private static final HashMap<String, ArrayList<String>> spellPool;
    public static HashMap<String, ArrayList<String>> getSpellPool() {
        return spellPool;
    }

    //// Hero
    // types
    private static final ArrayList<String> heroTypes = new ArrayList<>(Arrays.asList("Paladins", "Sorcerers", "Warriors"));
    public static ArrayList<String> getHeroTypes() {
        return heroTypes;
    }
    // values
    private static final HashMap<String, HashMap<String, HashMap<String, Integer>>> heroValues = new HashMap<>();
    public static HashMap<String, HashMap<String, HashMap<String, Integer>>> getHeroValues() {
        return heroValues;
    }
    // pool
    private static final HashMap<String, ArrayList<String>> heroPool;
    public static HashMap<String, ArrayList<String>> getHeroPool() {
        return heroPool;
    }

    //// Monster
    // types
    private static final ArrayList<String> monsterTypes = new ArrayList<>(Arrays.asList("Dragons", "Exoskeletons", "Spirits"));
    public static ArrayList<String> getMonsterTypes() {
        return monsterTypes;
    }
    // values
    private static final HashMap<String, HashMap<String, HashMap<String, Integer>>> monsterValues = new HashMap<>();
    public static HashMap<String, HashMap<String, HashMap<String, Integer>>> getMonsterValues() {
        return monsterValues;
    }
    // pool
    private static final HashMap<String, ArrayList<String>> monsterPool;
    public static HashMap<String, ArrayList<String>> getMonsterPool() {
        return monsterPool;
    }

    static {
        for (String type: spellTypes)
            spellValues.put(type, FileReader.readStats(Config.getFilePath(type)));
        spellPool = Pools.layerValues2LayerPool(spellValues);

        for (String type: heroTypes)
            heroValues.put(type, FileReader.readStats(Config.getFilePath(type)));
        heroPool = Pools.layerValues2LayerPool(heroValues);

        for (String type: monsterTypes)
            monsterValues.put(type, FileReader.readStats(Config.getFilePath(type)));
        monsterPool = Pools.layerValues2LayerPool(monsterValues);

    }

    public static <T> ArrayList<String> values2pool(HashMap<String, T> values) {
        return new ArrayList<>(values.keySet());
    }

    public static <T> HashMap<String, ArrayList<String>> layerValues2LayerPool(HashMap<String, HashMap<String, T>> values) {
        HashMap<String, ArrayList<String>> ret = new HashMap<>();
        for (String key: values.keySet())
            ret.put(key, values2pool(values.get(key)));
        return ret;
    }


}
