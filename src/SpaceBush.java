public class SpaceBush extends Space {

    SpaceBush(World world) {
        this.world = world;
        symbol = 'B';
        color = "GREEN"; // You can choose the appropriate color
    }

    @Override
    public void update() {
        // Implement bush-specific logic here, if needed.
    }

    @Override
    public boolean accessible() {
        return true;
    }
}
