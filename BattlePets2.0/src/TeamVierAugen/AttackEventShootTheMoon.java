/**
 * Authors: Ross Baldwin, Jared Hollenberger
 *
 * Purpose: AttackEventShootTheMoon holds information for when a Playable chooses Shoot The Moon. It has its own
 * builder for easy instantiation.
 */
package TeamVierAugen;

import TeamVierAugen.Skills.Skills;

import java.util.Objects;

public class AttackEventShootTheMoon extends AttackEvent{

    private Skills predictedSkillEnum = null;

    /**
     * Constructor used by the builder. It stores values from the builder to this event object. It
     * utilizes its super class's constructor.
     * @param builder
     */
    private AttackEventShootTheMoon(AttackEventShootTheMoonBuilder builder)
    {
        super(builder.attackingPlayableUid, builder.victimPlayableUid, builder.attackingSkillChoice,
                builder.damage);
        this.predictedSkillEnum = builder.predictedSkillEnum;
    }

    /**
     * Checks if two objects are equal
     * @param o Object to compare to
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AttackEventShootTheMoon that = (AttackEventShootTheMoon) o;
        return predictedSkillEnum == that.predictedSkillEnum;
    }

    /**
     * Returns a unique hashcode representing the object
     * @return object's hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), predictedSkillEnum);
    }

    /**
     * Returns information about the object in String form
     * @return object in String form
     */
    @Override
    public String toString() {
        return "AttackEventShootTheMoon{" +
                "predictedSkillEnum=" + predictedSkillEnum +
                '}';
    }

    /**
     * Returns the Skill predicted by the Playable
     * @return Skill prediction of Playable
     */
    public Skills getPredictedSkillEnum() {
        return predictedSkillEnum;
    }

    /**
     * This is the builder for an AttackEventShootTheMoon. It is used to instantiate objects.
     */
    public static class AttackEventShootTheMoonBuilder
    {
        private int attackingPlayableUid;
        private int victimPlayableUid;
        private Skills attackingSkillChoice;
        private Damage damage;
        private Skills predictedSkillEnum = null;

        /**
         * Default constructor. Sets variables to defaults.
         */
        public AttackEventShootTheMoonBuilder()
        {

        }

        /**
         * Sets attackingSkillChoice to given Skill
         * @param attackingSkillChoice Skill choice
         * @return updated version of the builder
         */
        public AttackEventShootTheMoonBuilder withAttackingSkillChoice(Skills attackingSkillChoice)
        {
            this.attackingSkillChoice = attackingSkillChoice;
            return this;
        }

        /**
         * Sets damage to given Damage object
         * @param damage Damage object associated with event
         * @return updated version of the builder
         */
        public AttackEventShootTheMoonBuilder withDamage(Damage damage)
        {
            this.damage = damage;
            return this;
        }

        /**
         * Sets attackingPlayableUid to given Integer
         * @param attackingPlayableUid id of attacking Playable in event
         * @return updated version of the builder
         */
        public AttackEventShootTheMoonBuilder withAttackingPlayableUid (int attackingPlayableUid)
        {
            this.attackingPlayableUid = attackingPlayableUid;
            return this;
        }

        /**
         * Sets victimPlayableUid to given Integer
         * @param victimPlayableUid id of victim Playable in event
         * @return updated version of the builder
         */
        public AttackEventShootTheMoonBuilder withVictimPlayableUid (int victimPlayableUid)
        {
            this.victimPlayableUid = victimPlayableUid;
            return this;
        }

        /**
         * Sets predictedSkillEnum to given Skill
         * @param predictedSkillEnum skill prediction of attacking Playable
         * @return updated version of the builder
         */
        public AttackEventShootTheMoonBuilder withpredictedSkillEnum (Skills predictedSkillEnum)
        {
            this.predictedSkillEnum = predictedSkillEnum;
            return this;
        }

        /**
         * Uses the builder constructor in AttackEventShootTheMoon to generate an
         * AttackEventShootTheMoon using this builder's values.
         * @return AttackEventShootTheMoon with same values as this builder.
         */
        public AttackEventShootTheMoon build()
        {
            return new AttackEventShootTheMoon(this);
        }
    }
}
