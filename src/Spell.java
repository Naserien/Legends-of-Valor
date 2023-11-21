import java.util.HashMap;

public class Spell extends Goods {

    private final int requiredLevel;
    private final int damage;
    private final int manaCost;

    Spell(String type, String name, HashMap<String, Integer> values) {
        this.type = type;
        this.name = name;

        cost = values.get("cost");
        requiredLevel = values.get("required level");
        damage = values.get("damage");
        manaCost = values.get("mana cost");
    }

    @Override
    public String toString() {
        return "Spell{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", requiredLevel=" + requiredLevel +
                ", damage=" + damage +
                ", manaCost=" + manaCost +
                '}';
    }

    public int getMPCost() {
        return manaCost;
    }

    public int getDamage() {
        return damage;
    }
}
