public class
Main {
    public static void main(String[] args) {

        World world = new World(new Player(), Market.getMarket());
        world.print_map();

        world.play();
    }
}