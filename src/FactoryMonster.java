/*
* return monsters*/

public class FactoryMonster {

    public static Monster createMonster(String type, String name) {
        assert Pools.getMonsterValues().containsKey(type);
        assert Pools.getMonsterValues().get(type).containsKey(name);
        return new Monster(type, name, Pools.getMonsterValues().get(type).get(name));
    }
}
