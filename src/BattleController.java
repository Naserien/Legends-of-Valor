/*Control the battle
* parameters: world hero monster*/


import java.util.ArrayList;

public class BattleController{

    public static void heroesRecoverAndImprove(ArrayList<Hero> heroes, ArrayList<Monster> monsters, ArrayList<Integer> aliveHeroIndexes) {
        for (int i=0; i<heroes.size(); i++) {
            heroes.get(i).recoverAndImproveAfterFight(aliveHeroIndexes.contains(i), monsters);
        }
    }

    public static int selectAMonsterIndex(ArrayList<Monster> monsters, ArrayList<Integer> aliveMonsterIndexes, World world) {
        if (aliveMonsterIndexes.size() == 1)
            return aliveMonsterIndexes.get(0);
        for (int i=0; i<aliveMonsterIndexes.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, monsters.get(i));
        }
        Utils.print_instr_line();
        System.out.println("Please select a number or enter 'r' to randomly attack.");
        Utils.print_instr_line();
        String input;
        int index;
        while (true) {
            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;
            if (input.equalsIgnoreCase("r")) {
                return RandomMachine.selectAnElement(aliveMonsterIndexes);
            }
            if (input.equalsIgnoreCase("i")) {
                world.showStateInformation();
                continue;
            }
            try {
                index = Utils.no2index(Integer.parseInt(input));
                if (0 <= index && index < aliveMonsterIndexes.size())
                    return index;
                else {
                    Utils.stateWarning();
                    System.out.println("Number out of range.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                System.out.println("Please select a number or enter 'r' to randomly attack.");
            }
        }
    }

    public static boolean battleAndWin(World world) {
        Utils.print_state_line();
        world.showStateInformation(); // 不占轮次，world写一个吧
        Utils.print_state_line();

        ArrayList<Hero> heroes = world.getPlayer().getHeroes();
        ArrayList<Integer> aliveHeroIndexes = Utils.getRangeArrayList(heroes.size());
        ArrayList<Monster> monsters = world.getMonsters();
        ArrayList<Integer> aliveMonsterIndexes = Utils.getRangeArrayList(monsters.size());

        int targetIdx;
        int round = 1;
        while (true) {
            System.out.printf("\n\n********* ROUND %d *********\n\n", round);
            for (int attackIdx: aliveHeroIndexes) {
                Utils.print_state_line();
                System.out.printf("***** Hero <%s> is taking action.\n", heroes.get(attackIdx).getName());
                Utils.print_state_line();
                targetIdx = BattleController.selectAMonsterIndex(monsters, aliveMonsterIndexes, world);
                if (!BattleController.HeroAttackMonsterAndAlive(heroes.get(attackIdx), monsters.get(targetIdx), world)) {
                    // careful
                    aliveMonsterIndexes.remove(Integer.valueOf(targetIdx));
                    if (aliveMonsterIndexes.isEmpty()) {
                        break;
                    }
                }
            }

            if (aliveMonsterIndexes.isEmpty()) {
                // recover
                // may improve
                System.out.println("Congratulations! You win!");
                heroesRecoverAndImprove(heroes, monsters, aliveHeroIndexes);
                world.getPlayer().showStateInformation();
                return true;
            }

            for (int attackIdx: aliveMonsterIndexes) {
                Utils.print_state_line();
                System.out.printf("***** Monster <%s> is taking action.\n", monsters.get(attackIdx).getName());
                Utils.print_state_line();
                targetIdx = RandomMachine.selectAnElement(aliveHeroIndexes);
                if (!BattleController.MonsterAttackHeroAndAlive(monsters.get(attackIdx), heroes.get(targetIdx)))
                    aliveHeroIndexes.remove(Integer.valueOf(targetIdx));
            }

            if (aliveHeroIndexes.isEmpty()) {
                return false;
            }

            for (int aliveHeroIdx: aliveHeroIndexes) {
                heroes.get(aliveHeroIdx).recoverEveryRound();
            }

            Utils.print_state_line();
            world.showStateInformation();
            Utils.print_state_line();
        }
    }

    public static boolean HeroAttackMonsterAndAlive(Hero hero, Monster monster, World world) {

        String input;
        int choice;
        while (true) {

            Utils.print_instr_line();
            Hero.showBattleChoices();
            System.out.println("Please choose the number.");
            Utils.print_instr_line();

            input = InputScanner.input_string();
            if (input.isEmpty())
                continue;

            if (input.equalsIgnoreCase("i")) {
                world.showStateInformation();
                continue;
            }

            try {
                choice = Utils.no2index(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                Utils.stateWarning();
                System.out.println("Invalid operation.");
                System.out.println("You should fight");
                System.out.println("You shouldn't give up");
                System.out.println("Please choose the number");
                continue;
            }

            // corresponding to battleChoices
            switch (choice) {
                case 0:
                    if (monster.succeedInDodging()) {
                        System.out.println("Monster escapes from this attack");
                        return true;
                    }
                    return monster.receiveHarmAndAlive(hero.getDamageByWeapon());
                case 1:
                    if (monster.succeedInDodging()) {
                        System.out.println("Monster escapes from this attack");
                        return true;
                    }
                    Spell spell = hero.chooseSpell();
                    if (spell != null) {
                        System.out.printf("Hero <%s> uses a %s %s.\n", hero.getName(), spell.getType(), spell.getName());
                        if (monster.receiveHarmAndAlive(hero.getDamageBySpell(spell))) {
                            monster.weakenedBySpell(spell);
                            return true;
                        }
                        else
                            return false;
                    }
                    break;
                case 2:
                    if (hero.usePotion())
                        return true;
                    break;
                case 3:
                    if (hero.equipWeapon())
                        return true;
                    break;
                case 4:
                    if (hero.equipArmor())
                        return true;
                    break;
                default:
                    Utils.stateWarning();
                    System.out.println("Please choose a valid operation number.");
            }
        }
    }

    public static boolean MonsterAttackHeroAndAlive(Monster monster, Hero hero) {
        if (hero.succeedInDodging()) {
            System.out.println("Hero escapes from this attack");
            return true;
        }
        return hero.receiveHarmAndAlive(monster.getDamage());
    }
}
