
/**
 * Authors: Lincoln Schroeder
 *
 * Commenter: Zexin Liu
 *
 * Purpose: This class defines the enumerations for skill types.
 */
package TeamVierAugen.Skills;
import TeamVierAugen.Utils;

public enum Skills
{
    ROCK_THROW,
    SCISSORS_POKE,
    PAPER_CUT,
    SHOOT_THE_MOON,
    REVERSAL_OF_FORTUNE;

    @Override
    public String toString()
    {
        return Utils.convertEnumString(this.name());
    }

    public static Skills converIntToSkills(int skillNum)
    {
        switch (skillNum)
        {
            case 0:
                return ROCK_THROW;
            case 1:
                return SCISSORS_POKE;
            case 2:
                return PAPER_CUT;
            case 3:
                return SHOOT_THE_MOON;
            case 4:
                return REVERSAL_OF_FORTUNE;
        }
        return null;
    }


}
