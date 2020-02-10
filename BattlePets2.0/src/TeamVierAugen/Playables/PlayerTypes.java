/**
 * Authors: Lincoln Schroeder
 *
 * Commenter: Zexin Liu
 *
 * Purpose: This class defines the enumerations for player types.
 */
package TeamVierAugen.Playables;

import TeamVierAugen.Utils;

public enum PlayerTypes
{
    HUMAN,
    COMPUTER;

    @Override
    public String toString()
    {
        return Utils.convertEnumString(this.name());
    }
}

