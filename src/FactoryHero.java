/*Return hero*/


public class FactoryHero {

    public static Hero createHero(String type, String name) {
        assert Pools.getHeroValues().containsKey(type);
        assert Pools.getHeroValues().get(type).containsKey(name);
        return new Hero(type, name, Pools.getHeroValues().get(type).get(name));
    }
}
