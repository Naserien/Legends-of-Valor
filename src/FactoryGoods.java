/*
* Goods factory
* Some basic functions about goods*/


public class FactoryGoods {
    public static int getGoodsValue(String goodsKey, String goodsName, String valueName) {

        int ret = -1;
        try {
            if (goodsKey.equals("Armory")) {
                ret = Pools.getArmoryValues().get(goodsName).get(valueName);
            }
            else if (goodsKey.equals("Potion")) {
                ret = Integer.parseInt(Pools.getPotionValues().get(goodsName).get(valueName));
            }
            else if (goodsKey.equals("Weaponry")) {
                ret = Pools.getWeaponryValues().get(goodsName).get(valueName);
            }
            else if (Pools.getSpellTypes().contains(goodsKey)) {
                ret = Pools.getSpellValues().get(goodsKey).get(goodsName).get(valueName);
            }
            else {
                Utils.stateWarning();
                System.out.printf("No goodsKey named %s\n", goodsKey);
            }
        }
        catch (NumberFormatException e) {
            Utils.stateError();
            System.out.println("Cannot become an integer value.");
        }
        catch (IllegalAccessError e) {
            Utils.stateError();
            System.out.println("Invalid Key or name");
        }
        if (ret == -1)
            System.err.println("Invalid query.");
        return ret;
    }

    public static int getPrice(String goodsKey, String goodsName) {
        int cost = getGoodsValue(goodsKey, goodsName, "cost");
        if (cost == -1)
            return Integer.MAX_VALUE;
        else
            return cost;
    }

    public static int getRequiredLevel(String goodsKey, String goodsName) {
        int rl;
        try {
            try {
                rl = getGoodsValue(goodsKey, goodsName, "required level");
            }
            catch (NullPointerException e) {
                rl = getGoodsValue(goodsKey, goodsName, "level");
            }
        } catch (NullPointerException e) {
            return 0;
        }
        if (rl == -1)
            return Integer.MAX_VALUE;
        else
            return rl;
    }
}


class FactoryArmory {

    public static Armory createArmory(String name) {
        assert Pools.getArmoryValues().containsKey(name);
        return new Armory(name, Pools.getArmoryValues().get(name));
    }
}


class FactoryPotion {

    public static Potion createPotion(String name) {
        assert Pools.getPotionValues().containsKey(name);
        return new Potion(name, Pools.getPotionValues().get(name));
    }
}


class FactorySpell {

    public static Spell createSpell(String type, String name) {
        assert Pools.getSpellValues().containsKey(type);
        assert Pools.getSpellValues().get(type).containsKey(name);
        return new Spell(type, name, Pools.getSpellValues().get(type).get(name));
    }
}


class FactoryWeaponry {

    public static Weaponry createWeaponry(String name) {
        assert Pools.getWeaponryValues().containsKey(name);
        return new Weaponry(name, Pools.getWeaponryValues().get(name));
    }
}
