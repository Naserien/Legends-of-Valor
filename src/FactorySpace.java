import java.util.HashMap;

public class FactorySpace {
    private static final HashMap<String, Double> typeProb;

    static {
        typeProb = new HashMap<>();
        typeProb.put("Bush", 0.2);
        typeProb.put("Cave", 0.2);
        typeProb.put("Koulou", 0.2);
        typeProb.put("Obstacle", 0.05);
        typeProb.put("Plain", 0.35);
        typeProb.put("Inaccessible", 0.0);
    }

    public static Space createSpaceRandom(World world) {

        String type = RandomMachine.RouletteSelect(typeProb);
        if (type == null)
            throw new NullPointerException("typeProb cannot produce null selection.");

        // Default to Plain if no other type matches (though this should never happen if probabilities are correctly set)
        return createSpace(world, type);
    }

    public static Space createSpace(World world, String type) {
//        System.out.println(type);
        switch (type) {
            case "Bush":
                return new SpaceBush(world);
            case "Cave":
                return new SpaceCave(world);
            case "Koulou":
                return new SpaceKoulou(world);
            case "Obstacle":
                return new SpaceObstacle(world);
            case "Plain":
                return new SpacePlain(world);
            case "Inaccessible":
                return new SpaceInaccessible(world);
            default:
                throw new IllegalArgumentException("Invalid space type: " + type);
        }
    }
}
