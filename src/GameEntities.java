/*Base abstract class*/


import java.util.HashMap;

public abstract class GameEntities {

    static String dirFiles = "./Legends_Monsters_and_Heroes/";
    protected String type;
    protected String name;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getValue() {
        return FileReader.readStats(dirFiles + type + ".txt").get(name);
    }

    public HashMap<String, String> getValueAsString() {
        return FileReader.readStatsToString(dirFiles + type + ".txt").get(name);
    }

    abstract void check_null();
}
