/*Definition*/


import java.util.HashMap;

public class Config {

    static final int RecommendedMaxHeroes = 3;

    static final double ProbBattleInCommonSpace = 0.4;
//    static final double ProbBattleInCommonSpace = 0.16;
//    static final double ProbBattleInCommonSpace = 1;

    static final String DirFiles = "./Legends_Monsters_and_Heroes/";
    static String getFilePath(String filename) {
        return DirFiles + filename + ".txt";
    }

    static int FactorLevel2HP = 100;

//    static double FactorHeroAgilityToDodgeChance = 0.002;
static double FactorHeroAgilityToDodgeChance = 0.0002;
    static double FactorMonsterDodgeChanceToDodgeChance = 0.001;

    static double FactorHeroDamageBySpellProportion = 0.1;
    static double FactorHeroDamageBySpellAccordingToDexterity = 0.00001;

    static double FactorHeroDamageByWeaponAccordingToStrengthAndWeapon = 0.05;

    static double FactorSpellWeakenProportion = 0.9;

    static double FactorMPHPRecover = 1.1;

    static double FactorSkillIncreaseProportion = 0.05;
    static double FactorSkillExtraIncreaseProportion = 0.05;

    static int FactorLevelToRequiredExperienceToLevelUp = 10;

    static double FactorDieRewardPortion = 0.6;

    static HashMap<String, String> SpellTypeWeakMonsterAttr = new HashMap<>();
    static {
        SpellTypeWeakMonsterAttr.put("IceSpell", "damage");
        SpellTypeWeakMonsterAttr.put("FireSpell", "defense");
        SpellTypeWeakMonsterAttr.put("LightningSpell", "dodgeChance");
    }

    static int speed = 1;
    static int MonsterDamageAfterDefense(int damage, int defence, int level) {
        // hp = level * 100
        // defence max reduce 50% damage

        // p = proportion defence larger than the default hp (0-1)
        // damage -= (0.4 * p + 0.1) * damage

        return speed * (damage - (int) ((0.1 + Integer.max(0, defence - 100 * (level - 1)) / defence * 0.4) * damage));
    }

    static int HeroDamageAfter(int damage) {
        return speed * damage;
    }

}