/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Commenter: Zexin Liu
 *
 * Purpose: The enumeration class for pet types.
 */
package TeamVierAugen.Playables;

import TeamVierAugen.Utils;

public enum PetTypes
{
    POWER,
    SPEED,
    INTELLIGENCE;

    @Override
    public String toString()
    {
        return Utils.convertEnumString(this.name());
    }
}
