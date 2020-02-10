/**
 * Authors: Lincoln Schroeder
 *
 * Purpose: PlayerEventInfo holds info for the player events, and has its own builder for easy instantiation.
 */

package TeamVierAugen;

import TeamVierAugen.Playables.PetTypes;
import TeamVierAugen.Playables.PlayerTypes;
import TeamVierAugen.Skills.Skills;

import java.util.HashSet;
import java.util.Set;

public class PlayerEventInfo
{
    private int playableUid;
    private String petName;
    private PetTypes petType;
    private PlayerTypes playerType;
    private double startingHp;
    private Set<Skills> skillSet;

    /**
     * Constructor used by the builder. Copies values from the builder over to this PlayerEventInfo.
     * @param builder the builder to be copied from.
     */
    public PlayerEventInfo(PlayerEventInfoBuilder builder)
    {
        this.playableUid = builder.playableUid;
        this.petName = builder.petName;
        this.petType = builder.petType;
        this.playerType = builder.playerType;
        this.startingHp = builder.startingHp;
        this.skillSet = builder.skillSet;
    }

    /**
     * Copy constructor.
     * @param otherPlayerEventInfo the PlayerEventInfo to be copied from.
     */
    public PlayerEventInfo(PlayerEventInfo otherPlayerEventInfo)
    {
        this.playableUid = otherPlayerEventInfo.getPlayableUid();
        this.petName = otherPlayerEventInfo.getPetName();
        this.petType = otherPlayerEventInfo.getPetType();
        this.playerType = otherPlayerEventInfo.getPlayerType();
        this.startingHp = otherPlayerEventInfo.getStartingHp();
        this.skillSet = otherPlayerEventInfo.getSkillSet();
    }

    /**
     * Overriding the equals method to rely on playableUid.
     * @param o the object being compared.
     * @return true if playableUid is the same, false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        return false;
    }

    /**
     * Generates the hashcode for this PlayerEventInfo.
     * @return integer representing this PlayerEventInfo.
     */
    @Override
    public int hashCode()
    {
        return 0;
    }

    /**
     * Generates a string for this PlayerEventInfo.
     * @return string representing this PlayerEventInfo.
     */
    @Override
    public String toString()
    {
        return "PlayerEventInfo.toString() incomplete";
    }

    /**
     * Getter for the playableUid.
     * @return playableUid.
     */
    public int getPlayableUid()
    {
        return playableUid;
    }

    /**
     * Getter for petName.
     * @return petName.
     */
    public String getPetName()
    {
        return petName;
    }

    /**
     * Getter for petType.
     * @return petType.
     */
    public PetTypes getPetType()
    {
        return petType;
    }

    /**
     * Getter for playerType.
     * @return playerType.
     */
    public PlayerTypes getPlayerType()
    {
        return playerType;
    }

    /**
     * Getter for startingHp.
     * @return startingHp.
     */
    public double getStartingHp()
    {
        return startingHp;
    }

    /**
     * Getter for skillSet.
     * @return skillSet.
     */
    public Set<Skills> getSkillSet()
    {
        return skillSet;
    }

    /**
     * Builder for the PlayerEventInfo class. User should use this class to build/instantiate a PlayerEventInfo.
     */
    public static class PlayerEventInfoBuilder
    {
        private int playableUid;
        private String petName;
        private PetTypes petType;
        private PlayerTypes playerType;
        private double startingHp;
        private Set<Skills> skillSet;

        /**
         * Default constructor. Sets variables to defaults.
         */
        public PlayerEventInfoBuilder()
        {
            skillSet = new HashSet<>();
        }

        /**
         * Sets petInstanceId to given integer.
         * @param petInstanceId integer representing the petInstanceId.
         * @return an updated version of this builder.
         */
        public PlayerEventInfoBuilder withPetInstance(int petInstanceId)
        {
            playableUid = petInstanceId;
            return this;
        }

        /**
         * Sets petName to given string.
         * @param petName string representing the petName.
         * @return an updated version of this builder.
         */
        public PlayerEventInfoBuilder withPetName(String petName)
        {
            this.petName = petName;
            return this;
        }

        /**
         * Sets petType to given PetTypes.
         * @param petType PetTypes representing the petType.
         * @return an updated version of this builder.
         */
        public PlayerEventInfoBuilder withPetType(PetTypes petType)
        {
            this.petType = petType;
            return this;
        }

        /**
         * Sets playerType to given PlayerTypes.
         * @param playerType PlayerTypes representing the playerType.
         * @return an updated version of this builder.
         */
        public PlayerEventInfoBuilder withPlayerType(PlayerTypes playerType)
        {
            this.playerType = playerType;
            return this;
        }

        /**
         * Sets the startingHp to given double.
         * @param startingHp double representing the startingHp.
         * @return an updated version of this builder.
         */
        public PlayerEventInfoBuilder withStartingHp(double startingHp)
        {
            this.startingHp = startingHp;
            return this;
        }

        /**
         * Sets the skillSet to given Set of Skills.
         * @param skillSet Set of Skills representing the skillSet.
         * @return an updated version of this builder.
         */
        public PlayerEventInfoBuilder withSkillSet(Set<Skills> skillSet)
        {
            this.skillSet = skillSet;
            return this;
        }

        /**
         * Uses the builder constructor in PlayerEventInfo to generate a PlayerEventInfo using this builder's values.
         * @return PlayerEventInfo with same values as this builder.
         */
        public PlayerEventInfo build()
        {
            return new PlayerEventInfo(this);
        }
    }

}
