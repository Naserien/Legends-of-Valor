public class SpaceObstacle extends Space {

    SpaceObstacle(World world) {
        this.world = world;
        symbol = 'O';
        color = "RED"; // You can choose the appropriate color
    }

    @Override
    public void update() {
        // Implement obstacle-specific logic here
        // For example, if a hero tries to remove the obstacle, you can handle it here.
        // If removed, change this space to a plain space.
    }

    @Override
    public boolean accessible() {
        return false;
    }
}
