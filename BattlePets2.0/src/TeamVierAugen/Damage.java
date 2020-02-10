/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: This class defines the damage done by a pet during one round. It stores the random damage
 * and conditional damage. It calculates the conditional damage, but doesn't access the
 * RandomNumberGenerator.
 */
package TeamVierAugen;

import TeamVierAugen.Playables.Playable;
import TeamVierAugen.Skills.Skills;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Damage
{
    private static final int POWER_CONDITIONAL_MULTIPLIER = 5;
    private static final int SPEED_CONDITIONAL_DAMAGE = 10;
    private static final float SPEED_CONDITIONAL_HP_PERCENT_UPPER = 0.75f;
    private static final float SPEED_CONDITIONAL_HP_PERCENT_LOWER = 0.25f;
    private static final int INTELLIGENCE_CONDITIONAL_DAMAGE_HIGH = 3;
    private static final int INTELLIGENCE_CONDITIONAL_DAMAGE_LOW = 2;
    private static final int SHOOT_THE_MOON_BONUS_DAMAGE = 20;

    private double randomDamage;
    private double conditionalDamage;

    /**
     * Constructor for explicitly setting random and conditional damage.
     * @param randomDamage the random damage for this instance.
     * @param conditionalDamage the conditional damage for this instance.
     */
    public Damage(double randomDamage, double conditionalDamage)
    {
        this.randomDamage = randomDamage;
        this.conditionalDamage = conditionalDamage;
    }

    /**
     * Getter for randomDamage.
     * @return randomDamage.
     */
    public double getRandomDamage()
    {
        return randomDamage;
    }

    /**
     * Setter for randomDamage.
     * @param randomDamage the desired randomDamage.
     */
    public void setRandomDamage(double randomDamage)
    {
        this.randomDamage = round(randomDamage, 2);
    }

    /**
     * Getter for conditionalDamage.
     * @return conditionalDamage.
     */
    public double getConditionalDamage()
    {
        return conditionalDamage;
    }

    /**
     * Setter for conditionalDamage.
     * @param conditionalDamage the desired conditionalDamage.
     */
    public void setConditionalDamage(double conditionalDamage)
    {
        this.conditionalDamage = round(conditionalDamage, 2);
    }

    /**
     * Calculates the total damage for this turn.
     * @return total damage.
     */
    public double calculateTotalDamage()
    {
        return randomDamage + conditionalDamage;
    }

    /**
     * Calculates conditional damage for POWER type Pets.
     * @param usedSkill the skill that this pet is using.
     * @param enemyUsedSkill the skill that the enemy is using.
     * @return conditional damage for this turn.
     * @throws Exception for invalid skill types.
     */
    public double calculatePowerConditional(Skills usedSkill,  Skills enemyUsedSkill) throws Exception
    {
        double damage = 0;
        switch(usedSkill)
        {
            case ROCK_THROW:
                if(enemyUsedSkill == Skills.SCISSORS_POKE)
                    damage = randomDamage * POWER_CONDITIONAL_MULTIPLIER;
                break;
            case PAPER_CUT:
                if(enemyUsedSkill == Skills.ROCK_THROW)
                    damage = randomDamage * POWER_CONDITIONAL_MULTIPLIER;
                break;
            case SCISSORS_POKE:
                if(enemyUsedSkill == Skills.PAPER_CUT)
                    damage = randomDamage * POWER_CONDITIONAL_MULTIPLIER;
                break;
            default:
                throw new Exception("Invalid skill type!");
        }
        damage = round(damage, 2);
        this.conditionalDamage = damage;
        return damage;
    }

    /**
     * Calculates conditional damage for SPEED type Pets.
     * @param usedSkill the skill that this pet is using.
     * @param enemyUsedSkill the skill that the enemy is using.
     * @param enemyHpPercent the enemy's current hp percent.
     * @return conditional damage for this turn.
     * @throws Exception for invalid skill types.
     */
    public double calculateSpeedConditional(Skills usedSkill,  Skills enemyUsedSkill, double enemyHpPercent)
            throws Exception
    {
        double damage = 0;
        switch(usedSkill)
        {
            case ROCK_THROW:
                if(enemyHpPercent >= SPEED_CONDITIONAL_HP_PERCENT_UPPER)
                {
                    if(enemyUsedSkill == Skills.SCISSORS_POKE || enemyUsedSkill == Skills.PAPER_CUT)
                        damage = SPEED_CONDITIONAL_DAMAGE;
                }
                break;
            case PAPER_CUT:
                if(enemyHpPercent >= SPEED_CONDITIONAL_HP_PERCENT_LOWER && enemyHpPercent <
                        SPEED_CONDITIONAL_HP_PERCENT_UPPER)
                {
                    if(enemyUsedSkill == Skills.ROCK_THROW || enemyUsedSkill == Skills.PAPER_CUT)
                        damage = SPEED_CONDITIONAL_DAMAGE;
                }
                break;
            case SCISSORS_POKE:
                if(enemyHpPercent < SPEED_CONDITIONAL_HP_PERCENT_LOWER)
                {
                    if(enemyUsedSkill == Skills.SCISSORS_POKE || enemyUsedSkill == Skills.ROCK_THROW)
                        damage = SPEED_CONDITIONAL_DAMAGE;
                }
                break;
            default:
                throw new Exception("Invalid skill type!");
        }
        damage = round(damage, 2);
        this.conditionalDamage = damage;
        return damage;
    }

    /**
     * Calculates conditional damage for INTELLIGENCE type Pets.
     * @param usedSkill the skill that this pet is using.
     * @param enemyPlayer the skill that the enemy pet is using.
     * @return conditional damage for this turn.
     * @throws Exception for invalid skill types.
     */
    public double calculateIntelligenceConditional(Skills usedSkill, Playable enemyPlayer)
            throws Exception
    {
        double damage = 0;
        switch(usedSkill)
        {
            case ROCK_THROW:
                if(enemyPlayer.getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_HIGH;
                if(enemyPlayer.getSkillRechargeTime(Skills.ROCK_THROW) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_LOW;
                if(enemyPlayer.getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_LOW;
                break;
            case PAPER_CUT:
                if(enemyPlayer.getSkillRechargeTime(Skills.ROCK_THROW) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_HIGH;
                if(enemyPlayer.getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_LOW;
                if(enemyPlayer.getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_LOW;
                break;
            case SCISSORS_POKE:
                if(enemyPlayer.getSkillRechargeTime(Skills.PAPER_CUT) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_HIGH;
                if(enemyPlayer.getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_LOW;
                if(enemyPlayer.getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0)
                    damage += INTELLIGENCE_CONDITIONAL_DAMAGE_LOW;
                break;
            default:
                throw new Exception("Invalid skill type!");
        }
        damage = round(damage, 2);
        this.conditionalDamage = damage;
        return damage;
    }

    /**
     * Calculates conditional damage for the skill Shoot the moon.
     * @param predictedSkill skill that player thought the opponent used
     * @param usedSkill skill used by opponent
     * @return double value that is damage
     */
    public double calculateShootTheMoonConditional(Skills predictedSkill, Skills usedSkill)
    {
        double damage = 0;
        if(predictedSkill == usedSkill)
        {
            damage = SHOOT_THE_MOON_BONUS_DAMAGE;
        }
        this.conditionalDamage = damage;
        return damage;
    }

    /**
     * This method is used to round double values that hold the amount of damage
     * done by a pet.
     * @param value double value to round
     * @param places amount of decimal places to round to
     * @return double value that is rounded
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

