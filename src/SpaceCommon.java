public class SpaceCommon extends Space{
    private static final double battleProb = Config.ProbBattleInCommonSpace;

    SpaceCommon(World world) {
        this.world = world;
        symbol = ' ';
        color = "WHITE";
    }
    @Override
    public void update() {
        if (RandomMachine.get0to1() < battleProb) {
            System.out.println("Surprise! Monsters Coming.");
            world.generateMonsters();
            world.battle();
        }
    }

    @Override
    public boolean accessible() {
        return true;
    }
}
