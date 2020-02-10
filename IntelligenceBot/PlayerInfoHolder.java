package edu.dselent.CustomAi;

import edu.dselent.player.PetTypes;
import edu.dselent.player.PlayerTypes;
import edu.dselent.skill.Skill;
import edu.dselent.skill.SkillTypes;
import edu.dselent.skill.Skills;
import edu.dselent.skill.instances.SkillInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerInfoHolder
{
    private int playableUid;
    private String petName;
    private PetTypes petType;
    private PlayerTypes playerType;
    private double startingHp;
    private double currentHp;
    private double cumulativeRdDifference;
    private double lastRandomDamageTaken;
    private double lastRandomDamageDealt;
    private Map<Skills, SkillInstance> skills;

    public PlayerInfoHolder(int playableUid)
    {
        this.playableUid = playableUid;
        skills = initializeSkills();
        cumulativeRdDifference = 0;
    }

    private Map<Skills, SkillInstance> initializeSkills()
    {
        Map<Skills, SkillInstance> map = new HashMap<Skills, SkillInstance>();
        map.put(Skills.ROCK_THROW, new SkillInstance(new Skill(Skills.ROCK_THROW, SkillTypes.CORE, 1), 0));
        map.put(Skills.SCISSORS_POKE, new SkillInstance(new Skill(Skills.SCISSORS_POKE, SkillTypes.CORE, 1), 0));
        map.put(Skills.PAPER_CUT, new SkillInstance(new Skill(Skills.PAPER_CUT, SkillTypes.CORE, 1), 0));
        map.put(Skills.SHOOT_THE_MOON, new SkillInstance(new Skill(Skills.SHOOT_THE_MOON, SkillTypes.SPECIAL, 6), 0));
        map.put(Skills.REVERSAL_OF_FORTUNE, new SkillInstance(new Skill(Skills.REVERSAL_OF_FORTUNE, SkillTypes.SPECIAL, 6), 0));

        return map;
    }

    public int getSkillRechargeTime(Skills skill)
    {
        return skills.get(skill).getCurrentRechargeTime();
    }

    public void decrementRechargeTimes()
    {
        for(Map.Entry<Skills, SkillInstance> skillEntry : skills.entrySet())
        {
            skillEntry.getValue().decrementRecharge();
        }
    }

    public void setRechargeTime(Skills skill, int recharge)
    {
        skills.get(skill).setCurrentRechargeTime(recharge);
    }


    public int getPlayableUid()
    {
        return playableUid;
    }

    public void setPlayableUid(int playableUid)
    {
        this.playableUid = playableUid;
    }

    public String getPetName()
    {
        return petName;
    }

    public void setPetName(String petName)
    {
        this.petName = petName;
    }

    public PetTypes getPetType()
    {
        return petType;
    }

    public void setPetType(PetTypes petType)
    {
        this.petType = petType;
    }

    public PlayerTypes getPlayerType()
    {
        return playerType;
    }

    public void setPlayerType(PlayerTypes playerType)
    {
        this.playerType = playerType;
    }

    public double getStartingHp()
    {
        return startingHp;
    }

    public void setStartingHp(double startingHp)
    {
        this.startingHp = startingHp;
        this.currentHp = startingHp;
    }

    public double getCurrentHp()
    {
        return currentHp;
    }

    public void updateHp(double damage)
    {
        currentHp -= damage;
    }

    public double calculateHpPercent()
    {
        return (currentHp / startingHp);
    }

    public void updateCumulativeRd()
    {
        cumulativeRdDifference += (lastRandomDamageTaken - lastRandomDamageDealt);
    }

    public void setLastRandomDamageTaken(double lastRandomDamageTaken)
    {
        this.lastRandomDamageTaken = lastRandomDamageTaken;
    }

    public void setLastRandomDamageDealt(double lastRandomDamageDealt)
    {
        this.lastRandomDamageDealt = lastRandomDamageDealt;
    }

    public double getCumulativeRdDifference()
    {
        return cumulativeRdDifference;
    }

    public boolean isAsleep()
    {
        if(currentHp <= 0)
            return true;
        return false;
    }
}
