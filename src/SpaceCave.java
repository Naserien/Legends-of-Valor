public class SpaceCave extends Space {

    SpaceCave(World world) {
        this.world = world;
        symbol = 'C';
        color = "Purple"; // You can choose the appropriate color
    }

    @Override
    public void update() {
        // Implement cave-specific logic here, if needed.
    }

    @Override
    public boolean accessible() {
        return true;
    }
}
