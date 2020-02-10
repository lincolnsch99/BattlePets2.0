/**
 * A FightStartEvent is generated at the beginning of fight. It extends from BaseEvent.
 */
package TeamVierAugen;

import java.util.List;
import java.util.Objects;

public class FightStartEvent extends BaseEvent
{
    private int fightIndex;
    private List<PlayerEventInfo> playerEventInfo;

    public FightStartEvent(int fightIndex, List<PlayerEventInfo> playerEventInfo)
    {
        super(EventTypes.FIGHT_START);
        this.fightIndex = fightIndex;
        this.playerEventInfo = playerEventInfo;
    }

    /**
     * Gets the fight index.
     * @return fightindex
     */
    public int getFightIndex() {
        return fightIndex;
    }

    /**
     * Gets the event list
     * @return playerEventInfo
     */
    public List<PlayerEventInfo> getPlayerEventInfo() {
        return playerEventInfo;
    }

    /**
     * Checks if the object equals this.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FightStartEvent that = (FightStartEvent) o;
        return fightIndex == that.fightIndex &&
                Objects.equals(playerEventInfo, that.playerEventInfo);
    }

    /**
     * Generates the unique hashcode for FightStartevent
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fightIndex, playerEventInfo);
    }

    /**
     * Overrides toString for when FightStartEvent is printed out.
     * @return
     */
    @Override
    public String toString() {
        return "FightStartEvent{" +
                "fightIndex=" + fightIndex +
                ", playerEventInfo=" + playerEventInfo +
                '}';
    }
}
