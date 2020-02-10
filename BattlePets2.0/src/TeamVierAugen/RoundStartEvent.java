/**
 * Authors: Lincoln Schroeder
 *
 * Purpose: RoundStartEvent holds the current round number.
 */

package TeamVierAugen;

public class RoundStartEvent extends BaseEvent
{
    private int roundNumber;

    /**
     * Constructor. Takes an integer for roundNumber.
     * @param roundNumber round that this event represents.
     */
    public RoundStartEvent(int roundNumber)
    {
        super(EventTypes.ROUND_START);
        this.roundNumber = roundNumber;
    }

    /**
     * Getter for roundNumber.
     * @return roundNumber.
     */
    public int getRoundNumber()
    {
        return roundNumber;
    }

    /**
     * Generates the hashcode for this RoundStartEvent.
     * @return integer representing this RoundStartEvent.
     */
    @Override
    public int hashCode()
    {
        return 0;
    }

    /**
     * Overriding the equals method to rely on roundNumber.
     * @param o the object being compared.
     * @return true if roundNumber is the same, false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        return false;
    }

    /**
     * Generates a string for this RoundStartEvent.
     * @return string representing this RoundStartEvent.
     */
    @Override
    public String toString()
    {
        return "RoundStartEvent.toString() incomplete";
    }

}
