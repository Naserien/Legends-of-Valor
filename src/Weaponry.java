import java.util.HashMap;

public class Weaponry extends Goods{

    private final int level;
    private final int damage;
    private final int requiredHands;

    Weaponry(String name, HashMap<String, Integer> values) {
        this.type = "weapon";
        this.name = name;

        cost = values.get("cost");
        level = values.get("level");
        damage = values.get("damage");
        requiredHands = values.get("required hands");
    }

    @Override
    public String toString() {
        return "Weaponry{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", level=" + level +
                ", damage=" + damage +
                ", requiredHands=" + requiredHands +
                '}';
    }

    public int getDamage() {
        return damage;
    }

    public int getRequiredHands() {
        return requiredHands;
    }
}
