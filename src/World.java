/*world
* subject
* contains player and monsters*/


import java.util.ArrayList;
import java.util.Arrays;

public class World implements Subject, StateInformation, OperationsHappen{

    private boolean over;
    public boolean gameOver() {
        return over;
    }

    private static final int default_height = 8;
    private static final int default_width = 8;

    private static final ArrayList<Integer> inaccessibleColumns = new ArrayList<>(Arrays.asList(2, 5));

    private static final int default_number_of_heroes_monsters = 2;

    private int number_of_heroes_monsters;

    private int height;
    private int width;
    private Space[][] map;

    private ArrayList<Monster> monsters = new ArrayList<>();
    public void generateMonsters() {
        int sumLevel = 0;
        for (Hero hero: player.getHeroes()) {
            sumLevel += hero.getMemberValue("level");
        }
        monsters =  HeroMonsterSelector.generateMonsters(sumLevel);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    private int level;
    public int getLevel() {
        return level;
    }

    private final Player player;
    public Player getPlayer() {
        return player;
    }

    private final Market market;
    public Market getMarket() {
        return market;
    }

    public void setPlayerPos(int height, int width) {
        int r, c;
        while (true) {
            r = RandomMachine.chooseInt(height);
            c = RandomMachine.chooseInt(width);
            if (map[r][c].accessible()) {
                player.setPos(r, c);
                break;
            }
        }
    }

    World(Player player, Market market) {
        this.player = player;
        this.market = market;

        init();
    }

    public void init() {
//        int[] hw = Utils.get_board_width_height(default_height, default_width);
//        height = hw[0];
//        width = hw[1];

        height = default_height;
        width = default_width;

        map = new Space[height][width];
        for (int r=0; r<height; r++) {
            for (int c=0; c<width; c++) {
                if (inaccessibleColumns.contains(c))
                    map[r][c] = FactorySpace.createSpace(this, "Inaccessible");
                else
                    map[r][c] = FactorySpace.createSpaceRandom(this);
            }
        }

        this.player.selectHeroes(default_number_of_heroes_monsters);

//        number_of_heroes_monsters = player.getNumHeroes();
//        setMonsters();

        setPlayerPos(height, width);

        level = 0;
        over = false;
    }

    private void print_split_row() {
        for (int c=0; c<width; c++)
            System.out.print('+' + "---");
        System.out.println('+');
    }

    public void print_map() {
//        ArrayList<int[]> legalPositions = getLegalPositions();
//        for (int[] position : legalPositions) {
//            System.out.println(Arrays.toString(position));
//        }
        print_split_row();
        for (int r=0; r<height; r++) {
            for (int c=0; c<width; c++) {
                System.out.print("| ");
                if (player.At(r, c))
                    System.out.print(player);
                else
                    System.out.print(map[r][c]);
                System.out.print(" ");
            }
            System.out.println("|");
            print_split_row();
        }
    }

    public ArrayList<Character> getLegalDirections() {
        ArrayList<Character> ret = new ArrayList<>();

        int[] rc = player.getRC();
        int r = rc[0];
        int c = rc[1];
        if (legalPos(r - 1, c))
            ret.add('w');
        if (legalPos(r + 1, c))
            ret.add('s');
        if (legalPos(r, c - 1))
            ret.add('a');
        if (legalPos(r, c + 1))
            ret.add('d');

        if (ret.isEmpty())
            System.out.println("Embarrassing! No way to go!");

        return ret;
    }

    public boolean AtLegalDirection(ArrayList<Character> legalDirections, char direction) {
        return legalDirections.contains(direction);
    }

    public ArrayList<int[]> getLegalPositions() {
        ArrayList<int[]> ret = new ArrayList<>();

        int[] rc = player.getRC();
        int r = rc[0];
        int c = rc[1];
        if (legalPos(r - 1, c))
            ret.add(new int[]{r - 1, c});
        if (legalPos(r + 1, c))
            ret.add(new int[]{r + 1, c});
        if (legalPos(r, c - 1))
            ret.add(new int[]{r, c - 1});
        if (legalPos(r, c + 1))
            ret.add(new int[]{r, c + 1});

        if (ret.isEmpty())
            System.out.println("Embarrassing! No way to go!");

        return ret;
    }

    public boolean AtLegalPositions(ArrayList<int[]> legalPositions, int r, int c) {
        int[] pos = new int[]{r, c};
        for (int[] legalPosition: legalPositions) {
            if (Arrays.equals(pos, legalPosition))
                return true;
        }
        return false;
    }

    public boolean legalPos(int r, int c) {
        if (r < 0 || r >= height || c < 0 || c >= width)
            return false;
        return map[r][c].accessible();
    }

    @Override
    public void printOperationInstructions() {
        Utils.print_instr_line();
        System.out.println("['w', 'a', 's', 'd'] to move;\n" +
                "'i' to show your properties;\n" +
                "'e' to equip armory, weapons or use potions.");
        Utils.print_instr_line();
    }

    public void stepOperation() {
        printOperationInstructions();
        // w a s d
        ArrayList<Character> legalDirections = getLegalDirections();
        String input;
        char direction;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("i")) {
                Utils.print_state_line();
                player.showStateInformation();
                Utils.print_state_line();
                printOperationInstructions();
                continue;
            }
            if (input.equalsIgnoreCase("e")) {
                player.equip();
                printOperationInstructions();
                break;
            }
            direction = input.charAt(0);
            if (AtLegalDirection(legalDirections, direction)) {
                if (player.updatePos(input))
                    break;
            } else {
                Utils.stateWarning();
                System.out.printf("%c is not in the legal directions %s\n", direction, legalDirections);
            }
        }
    }

    public void trade() {
        market.trade(player);
    }

    public void battle() {
        if (!BattleController.battleAndWin(this))
            over = true;
    }

    @Override
    public void showStateInformation() {
        System.out.println("Heroes:");
        player.showStateInformation();
        System.out.println("=====");
        System.out.println("Monsters:");
        for (Monster monster: monsters) {
            System.out.println("-----");
            monster.showStateInformation();
        }
    }

    @Override
    public void step() {

        if (over)
            return;

        System.out.println("You take action.");
        stepOperation();
        print_map();

        int[] playerRC = player.getRC();

        System.out.println("World update.");
        map[playerRC[0]][playerRC[1]].update();
        print_map();
    }

    public void play() {
        System.out.println("\n****************************Welcome to Legend Heroes and Monsters.\n****************************\n");
        while (!over)
            step();
    }
}
