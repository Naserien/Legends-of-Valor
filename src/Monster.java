/*
* monster class
* many operations of monsters*/


import java.util.HashMap;

public class Monster extends HeroMonster implements StateInformation{

    private final int level;
    private int hp;
    private int damage;
    private int defense;
    private int dodgeChance;

    public int getMemberValue(String key) {
        switch (key) {
            case "level":
                return level;
            case "hp":
                return hp;
            case "damage":
                return damage;
            case "defense":
                return defense;
            case "dodgeChance":
                return dodgeChance;
            default:
                throw new IllegalArgumentException("Unsupported Member key: " + key);
        }
    }


    // Constructor, getters, setters, and other methods would go here

    Monster(String type, String name, HashMap<String, Integer> values) {
        this.type = type;
        this.name = name;

        level = values.get("level");
        hp = Config.FactorLevel2HP * level;
        damage = values.get("damage");
        defense = values.get("defense");
        dodgeChance = values.get("dodge chance");
    }

    @Override
    public void update() {

    }

    @Override
    public String toString() {
        return "Monster{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", hp=" + hp +
                ", damage=" + damage +
                ", defense=" + defense +
                ", dodgeChance=" + dodgeChance +
                '}';
    }

    @Override
    public void showStateInformation() {
        System.out.println(this);
    }

    @Override
    public int getDamage() {
        return getMemberValue("damage");
    }

    @Override
    public boolean succeedInDodging() {
        return RandomMachine.get0to1() < dodgeChance * Config.FactorMonsterDodgeChanceToDodgeChance;
    }

    @Override
    public boolean receiveHarmAndAlive(int raw_damage) {

        damage = Config.MonsterDamageAfterDefense(raw_damage, defense, level);
        hp = Integer.max(0, hp - damage);

        Utils.print_state_line();
        System.out.printf("Monster %s receives %d damage.\n", name, damage);
//        System.out.printf("(original harm=%d, defence=%d)\n", raw_damage, defense);
        System.out.printf("Monster %s: HP = %d.\n", name, hp);
        Utils.print_state_line();
        if (hp > 0)
            return true;
        else {
            System.out.printf("Monster %s die!\n", name);
            return false;
        }
    }

    public void weakenedBySpell(Spell spell) {
        System.out.println(spell);
        int oriValue;
        switch (spell.getType()) {
            case "IceSpells":
                oriValue = damage;
                damage = (int) (damage * Config.FactorSpellWeakenProportion);
                System.out.printf("(Monster) %s damage: %d -> %d\n", name, oriValue, damage);
                break;
            case "FireSpells":
                oriValue = defense;
                defense = (int) (damage * Config.FactorSpellWeakenProportion);
                System.out.printf("(Monster) %s defense: %d -> %d\n", name, oriValue, defense);
                break;
            case "LightningSpells":
                oriValue = dodgeChance;
                dodgeChance = (int) (dodgeChance * Config.FactorSpellWeakenProportion);
                System.out.printf("(Monster) %s dodging chance: %f -> %f\n", name, oriValue * Config.FactorMonsterDodgeChanceToDodgeChance, dodgeChance * Config.FactorMonsterDodgeChanceToDodgeChance);
                break;
        }
    }
}
