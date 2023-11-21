public class SpacePlain extends Space {

    SpacePlain(World world) {
        this.world = world;
        symbol = '.';
        color = "WHITE"; // You can choose the appropriate color
    }

    @Override
    public void update() {
        // Implement plain space-specific logic here, if needed.
    }

    @Override
    public boolean accessible() {
        return true;
    }
}
