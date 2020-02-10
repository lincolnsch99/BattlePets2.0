/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: This class defines an instance of skills in game.
 */
package TeamVierAugen.Skills;

public class SkillInstance
{
    Skills type;
    int rechargeTime;

    public SkillInstance(Skills type)
    {
        this.type = type;
        rechargeTime = 0;
    }

    /**
     * Decrements the recharge time.
     */
    public void decrementRecharge()
    {
        if(rechargeTime > 0)
            rechargeTime--;
    }

    /**
     * Determines if a skill is on cooldown.
     * @return true if rechargeTime > 0 else false
     */
    public boolean isOnCooldown()
    {
        if(rechargeTime > 0)
            return true;
        return false;
    }

    /**
     * Sets the recharge time.
     * @param rechargeTime
     */
    public void setRechargeTime(int rechargeTime)
    {
        this.rechargeTime = rechargeTime;
    }

    /**
     * Gets the recharge time.
     * @return
     */
    public int getRechargeTime(){return rechargeTime;}

    /**
     * Returns the type of skill.
     * @return skill type
     */
    public Skills getType()
    {
        return type;
    }
}
