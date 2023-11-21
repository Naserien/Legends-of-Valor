/*
* potions extends goods*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Potion extends Goods{

    private final int requiredLevel;
    private final int attributeIncrease;
    private final ArrayList<String> attributeAffected;

    Potion(String name, HashMap<String, String> valuesAndDescriptions) {
        this.type = "Potion";
        this.name = name;

        cost = Integer.parseInt(valuesAndDescriptions.get("cost"));
        requiredLevel = Integer.parseInt(valuesAndDescriptions.get("required level"));

        attributeIncrease = Integer.parseInt(valuesAndDescriptions.get("attribute increase"));
        attributeAffected = new ArrayList<>(Arrays.asList(valuesAndDescriptions.get("attribute affected").split("/")));
    }

    @Override
    public String toString() {
        return "Potion{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", requiredLevel=" + requiredLevel +
                ", attributeIncrease=" + attributeIncrease +
                ", attributeAffected='" + attributeAffected + '\'' +
                '}';
    }

    public int getAttributeIncrease() {
        return attributeIncrease;
    }

    public ArrayList<String> getAttributeAffected() {
        return attributeAffected;
    }
}
