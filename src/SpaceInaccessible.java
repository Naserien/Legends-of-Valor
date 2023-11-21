public class SpaceInaccessible extends Space{

    SpaceInaccessible(World world) {
        this.world = world;
        symbol = '/';
        color = "RED";
    }

    @Override
    public void update() {
        Utils.stateError();
        System.err.println("Shouldn't reach SpaceInaccessible.update() !");
    }

    @Override
    public boolean accessible() {
        return false;
    }
}
