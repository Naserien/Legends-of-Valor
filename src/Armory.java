/*Armory extend Goods*/

import java.util.HashMap;

public class Armory extends Goods {

    private final int requiredLevel;
    private final int damageReduction;

    public int reduceDamage(int damage) {
        return Integer.max(0, damage - damageReduction);
    }

    Armory(String name, HashMap<String, Integer> values) {

        this.type = "Armory";
        this.name = name;

        cost = values.get("cost");
        requiredLevel = values.get("required level");
        damageReduction = values.get("damage reduction");
    }

    @Override
    public String toString() {
        return "Armory{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", requiredLevel=" + requiredLevel +
                ", damageReduction=" + damageReduction +
                '}';
    }
}
