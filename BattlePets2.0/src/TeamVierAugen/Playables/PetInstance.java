/**
 * Authors: Lincoln Schroeder, Jared Hollenberger
 *
 * Purpose: An instance of a pet used in the game.
 */
package TeamVierAugen.Playables;

import TeamVierAugen.Skills.SkillInstance;
import TeamVierAugen.Skills.Skills;

import java.util.HashMap;
import java.util.Map;

public class PetInstance
{
    private Pet pet;
    private double startingHp;
    private double currentHp;
    private double cumlativeRD;
    private Map<Skills, SkillInstance> skills;
    private boolean awake;

    /**
     * Constructs the PetInstance class.
     * @param type
     * @param name
     */
    public PetInstance(PetTypes type, String name)
    {
        pet = new Pet(name, type);
        awake = true;
        skills = initializeSkills();
        cumlativeRD = 0.0;
    }

    /**
     * Gets the pet name
     * @return pet name
     */
    public String getPetName()
    {
        return pet.getName();
    }

    /**
     * Gets the pet
     * @return pet
     */
    public Pet getPet()
    {
        return pet;
    }

    /**
     * Gets starting hp
     * @return starting hp
     */
    public double getStartingHp()
    {
        return startingHp;
    }

    /**
     * Sets starting hp.
     * @param startingHp
     */
    public void setStartingHp(double startingHp)
    {
        this.startingHp = startingHp;
    }

    /**
     * Gets the current hp.
     * @return current hp
     */
    public double getCurrentHp()
    {
        return currentHp;
    }

    /**
     * Sets current hp.
     * @param currentHp
     */
    public void setCurrentHp(double currentHp)
    {
        this.currentHp = currentHp;
    }

    /**
     * Sets cumlativeRD
     * @param randomDamage
     */
    public void setCumlativeRD(double randomDamage) {this.cumlativeRD = randomDamage;}

    /**
     * Returns the skills map.
     * @return skills map
     */
    public Map<Skills, SkillInstance> getSkills()
    {
        return skills;
    }

    /**
     * Sets what skills the pet has.
     * @param skills
     */
    public void setSkills(Map<Skills, SkillInstance> skills)
    {
        this.skills = skills;
    }

    /**
     * Initializes the skills map
     * @return skills map
     */
    private Map<Skills, SkillInstance> initializeSkills()
    {
        Map<Skills, SkillInstance> map = new HashMap<Skills, SkillInstance>();
        map.put(Skills.ROCK_THROW, new SkillInstance(Skills.ROCK_THROW));
        map.put(Skills.SCISSORS_POKE, new SkillInstance(Skills.SCISSORS_POKE));
        map.put(Skills.PAPER_CUT, new SkillInstance(Skills.PAPER_CUT));
        map.put(Skills.SHOOT_THE_MOON, new SkillInstance(Skills.SHOOT_THE_MOON));
        map.put(Skills.REVERSAL_OF_FORTUNE, new SkillInstance(Skills.REVERSAL_OF_FORTUNE));

        return map;
    }

    /**
     * Determines if the pet is awake or not.
     * @return boolean awake
     */
    public boolean isAwake()
    {
        if (currentHp < 0)
            awake = false;
        return awake;
    }

    /**
     * Gets the CumlativeRD
     * @return cumlativeRD
     */
    public double getCumlativeRD() {
        return cumlativeRD;
    }
}
