public class SpaceKoulou extends Space {

    SpaceKoulou(World world) {
        this.world = world;
        symbol = 'K';
        color = "Yellow"; // You can choose the appropriate color
    }

    @Override
    public void update() {
        // Implement koulou-specific logic here, if needed.
    }

    @Override
    public boolean accessible() {
        return true;
    }
}
