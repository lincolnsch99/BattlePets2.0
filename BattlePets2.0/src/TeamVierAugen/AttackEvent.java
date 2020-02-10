/**
 * This class defines an AttackEvent. AttackEvents happen when a Playable
 * attempts to damage another Playable. It extends from BaseEvent.
 */

package TeamVierAugen;

import TeamVierAugen.Skills.Skills;

import java.util.Objects;

public class AttackEvent extends BaseEvent {

    private int attackingPlayableUid;
    private int victimPlayableUid;
    private Skills attackingSkillChoice;
    private Damage damage;

    /**
     * This constructor initializes all class variables.
     * @param attackingPlayableUid id of attacking Playable
     * @param victimPlayableUid if of victim Playable
     * @param attackingSkillChoice Skill chosen by attacking Playable
     * @param damage Damage class associated with attack
     */
    public AttackEvent(int attackingPlayableUid, int victimPlayableUid,
                       Skills attackingSkillChoice, Damage damage)
    {
        super(EventTypes.ATTACK);
        this.attackingPlayableUid = attackingPlayableUid;
        this.victimPlayableUid = victimPlayableUid;
        this.attackingSkillChoice = attackingSkillChoice;
        this.damage = damage;
    }

    /**
     * Returns the id of attacking Playable
     * @return id of attacking Playable
     */
    public int getAttackingPlayableUid() {
        return attackingPlayableUid;
    }

    /**
     * Returns id of victim Playable
     * @return id of victim Playable
     */
    public int getVictimPlayableUid() {
        return victimPlayableUid;
    }

    /**
     * Returns skill choice of attacking Playable
     * @return skill choice of attacking Playable
     */
    public Skills getAttackingSkillChoice() {
        return attackingSkillChoice;
    }

    /**
     * Returns Damage class of attack
     * @return Damage class of this AttackEvent
     */
    public Damage getDamage() {
        return damage;
    }

    /**
     * This method checks if the AttackEvent is equal to another Object
     * @param o Object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AttackEvent that = (AttackEvent) o;
        return attackingPlayableUid == that.attackingPlayableUid &&
                victimPlayableUid == that.victimPlayableUid &&
                attackingSkillChoice == that.attackingSkillChoice &&
                Objects.equals(damage, that.damage);
    }

    /**
     * This method returns the unique hashcode of the AttackEvent
     * @return hashcode of the AttackEvent
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attackingPlayableUid, victimPlayableUid, attackingSkillChoice, damage);
    }

    /**
     * This method returns information about the AttackEvent in String form.
     * @return AttackEvent in String form
     */
    @Override
    public String toString() {
        return "AttackEvent{" +
                "attackingPlayableUid=" + attackingPlayableUid +
                ", victimPlayableUid=" + victimPlayableUid +
                ", attackingSkillChoice=" + attackingSkillChoice +
                ", damage=" + damage +
                '}';
    }
}
