/*have a market*/

public class SpaceMarket extends Space {

    SpaceMarket(World world) {
        this.world = world;
        symbol = 'M';
        color = "YELLOW";
    }

    @Override
    public void update() {
        Utils.print_state_line();
        System.out.println("You are at a market");
        Utils.print_state_line();
        Utils.print_instr_line();
        System.out.println("Type M/m if you want to start trading.");
        System.out.println("Or type anything else to continue");
        Utils.print_instr_line();
        String input = InputScanner.input_string();
        if (input.isEmpty())
            return;
        if (Character.toLowerCase(input.charAt(0)) == 'm')
            world.trade();
    }

    @Override
    public boolean accessible() {
        return true;
    }
}
