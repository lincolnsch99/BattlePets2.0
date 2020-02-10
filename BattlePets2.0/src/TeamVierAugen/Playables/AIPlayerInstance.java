/**
 * This class represents an instance of an AI Player. AI Players choose their skills
 * randomly during rounds.
 */
package TeamVierAugen.Playables;

import TeamVierAugen.AttackEvent;
import TeamVierAugen.AttackEventShootTheMoon;
import TeamVierAugen.Controllers.InputOutputController;
import TeamVierAugen.FightStartEvent;
import TeamVierAugen.Skills.SkillInstance;
import TeamVierAugen.Skills.Skills;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIPlayerInstance implements Playable
{
    private AIPlayer player;
    private PetInstance pet;
    private int playableId;
    private static int DEFAULT_SEED = 4;
    private Random randomList;
    private Skills mostRecentSkillChoice;

    /**
     * Constructs the PlayerInstance class.
     * @param playerName
     * @param playerType
     * @param petName
     * @param petType
     */
    public AIPlayerInstance(String playerName, PlayerTypes playerType, String petName, PetTypes petType, int playableId)
    {
        this.player = new AIPlayer(playerName, playerType);
        this.pet = new PetInstance(petType, petName);
        this.playableId = playableId;
        randomList = new Random(DEFAULT_SEED);
    }
    /**
     * Updates cumlativeRD to the new amount
     * @param update
     */
    public void updateCumulativeRD(double update) { pet.setCumlativeRD(pet.getCumlativeRD() + update); }

    /**
     * Gets the cumlativeRD
     * @return cumlativeRD
     */
    public double getCumulativeRD() { return pet.getCumlativeRD(); }

    /**
     * Gets the player name.
     * @return player name
     */
    @Override
    public String getPlayerName()
    {
        return player.getName();
    }

    /**
     * Gets the pet name.
     * @return pet name
     */
    @Override
    public String getPetName()
    {
        return pet.getPetName();
    }

    /**
     * Gets the player type.
     * Not used yet
     * @return player type
     */
    @Override
    public PlayerTypes getPlayerType()
    {
        return player.getType();
    }

    /**
     * Gets the pet type.
     * @return pet type
     */
    @Override
    public PetTypes getPetType() {
        Pet pets = pet.getPet();
        return pets.getType();
    }

    /**
     * Gets the pet's current hp.
     * @return pet current hp
     */
    @Override
    public double getCurrentHp()
    {
        return pet.getCurrentHp();
    }

    /**
     * Has the player chose a skill.
     * @return skill chosen
     */
    @Override
    public Skills chooseSkill()
    {
        List<Skills> notOnCooldown = new ArrayList<>();
        for(int i = 0; i < pet.getSkills().size(); i++)
        {
            if(!pet.getSkills().get(Skills.converIntToSkills(i)).isOnCooldown())
            {
                notOnCooldown.add(Skills.converIntToSkills(i));
            }
        }
        int temp = randomList.nextInt(notOnCooldown.size());
        mostRecentSkillChoice = notOnCooldown.get(temp);
        return mostRecentSkillChoice;
    }

    /**
     * Updates the pet's current hp.
     * @param hp
     */
    @Override
    public void updateHp(double hp)
    {
        pet.setCurrentHp(round(pet.getCurrentHp() - hp, 2));
    }

    /**
     * Resets the pet's hp to starting hp.
     * Used for starting a new battle.
     */
    @Override
    public void resetHp()
    {
        pet.setCurrentHp(pet.getStartingHp());
    }

    /**
     * Sets the starting hp of the pet.
     * @param startingHp
     */
    public void setPetStartingHp (double startingHp) { pet.setStartingHp(startingHp); }

    /**
     * Returns if the pet is awake or not.
     * @return isAwake.
     */
    @Override
    public boolean isAwake()
    {
        return pet.isAwake();
    }

    /**
     * Not used right now.
     * @return null.
     */
    @Override
    public Skills getSkillPrediction()
    {
        Skills chosenSkill = null;
        List<SkillInstance> skillPrediction = new ArrayList<>();
        for(int i = 0; i < pet.getSkills().size(); i++)
        {
            skillPrediction.add(pet.getSkills().get(Skills.converIntToSkills(i)));
        }
        int temp = randomList.nextInt(skillPrediction.size());
        chosenSkill = skillPrediction.get(temp).getType();
        return chosenSkill;
    }

    /**
     * Gets the recharge time of a skill.
     * @param skill
     * @return skill recharge time.
     */
    @Override
    public int getSkillRechargeTime(Skills skill)
    {
        return pet.getSkills().get(skill).getRechargeTime();
    }

    /**
     * Calculates the pet's current hp percent
     * @return hp percent
     */
    @Override
    public double calculateHpPercent()
    {
        return round(pet.getCurrentHp()/pet.getStartingHp(), 2);
    }

    /**
     * Returns the pet's starting hp.
     * @return starting hp
     */
    @Override
    public double getStartingHp()
    {
        return pet.getStartingHp();
    }

    /**
     * Resets the pet's hp to starting and all recharge times to 0.
     */
    @Override
    public void reset()
    {
        resetHp();
        pet.setCumlativeRD(0.0);
        pet.getSkills().forEach((s,si)->setRechargeTime(s,0));
    }

    /**
     * Decrements the recharge time of all skills.
     */
    @Override
    public void decrementRechargeTimes()
    {
        pet.getSkills().forEach((s,si)->si.decrementRecharge());
    }

    /**
     * Sets the recharge time of a skill.
     * @param skill
     * @param rechargeTime
     */
    @Override
    public void setRechargeTime(Skills skill, int rechargeTime)
    {
        pet.getSkills().get(skill).setRechargeTime(rechargeTime);
    }

    /**
     * Gets PlayableId
     * @return playableId
     */
    @Override
    public int getPlayableId()
    {
        return playableId;
    }

    /**
     * Rounds the double value to a determined amount of places.
     * @param value
     * @param places
     * @return double value.
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    /**
     * Updated player information through event.
     * @param event holds information of player
     */
    @Override
    public void update(Object event)
    {
        if(event instanceof AttackEventShootTheMoon)
        {
            if(((AttackEvent) event).getAttackingPlayableUid() == playableId)
            {
                setRechargeTime(((AttackEvent) event).getAttackingSkillChoice(), 6);
            }
            else if(((AttackEvent) event).getVictimPlayableUid() == playableId)
            {
                if(((AttackEventShootTheMoon) event).getPredictedSkillEnum() == mostRecentSkillChoice)
                    ((AttackEventShootTheMoon) event).getDamage().setConditionalDamage(20);
                updateHp(((AttackEvent) event).getDamage().calculateTotalDamage());
            }
        }
        else if(event instanceof AttackEvent)
        {
            if(((AttackEvent) event).getAttackingPlayableUid() == playableId)
            {
                if(((AttackEvent) event).getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE)
                    setRechargeTime(((AttackEvent) event).getAttackingSkillChoice(), 6);
                else
                    setRechargeTime(((AttackEvent) event).getAttackingSkillChoice(), 1);
            }
            else if(((AttackEvent) event).getVictimPlayableUid() == playableId)
            {
                updateHp(((AttackEvent) event).getDamage().calculateTotalDamage());
            }
        }
        else if(event instanceof FightStartEvent)
        {
            reset();
        }
    }
}
