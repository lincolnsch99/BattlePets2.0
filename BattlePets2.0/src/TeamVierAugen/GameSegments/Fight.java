/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: The Fight class defines a fight between the pets of players. A
 * fight stores the list of players participating in the fight and the fight number,
 * which corresponds to the number of fights in a battle. When a fight is performed,
 * Rounds are continuously instantiated and executed until the pet of any player is
 * less than or equal to zero, at which time the fight is over. The player whose pet
 * with the highest HP at the end of the fight is the winner. Ties are also allowed,
 * and each player that wins on a tie is considered a winner.
 */
package TeamVierAugen.GameSegments;

import TeamVierAugen.Controllers.EventBusSubject;
import TeamVierAugen.Controllers.InputOutputController;
import TeamVierAugen.Playables.Playable;
import TeamVierAugen.Playables.PlayerInstance;

import java.util.ArrayList;
import java.util.List;

public class Fight {

    private int fightNumber;
    private List<Playable> playerList;
    private EventBusSubject eventBusSubject;


    /**
     * This constructor creates a Fight and sets its fight number to 1.
     */
    public Fight(EventBusSubject eventBusSubject)
    {
        this.eventBusSubject = eventBusSubject;
        fightNumber = 1;
    }

    /**
     * This method returns the list of players participating in a fight.
     * @return the list of players in the fight
     */
    public List<Playable> getPlayerList() {
        return playerList;
    }

    /**
     * This method updates the list of players participating in the fight.
     * @param playerList list of players participating in fight
     */
    public void setPlayerList(List<Playable> playerList)
    {
        this.playerList = playerList;
    }

    /**
     * This method returns the fight number.
     * @return fight number
     */
    public int getFightNumber() {
        return fightNumber;
    }

    /**
     * This method sets the fight number of the fight.
     * @param fightNumber the number of the fight in the current battle
     */
    public void setFightNumber(int fightNumber)
    {
        this.fightNumber = fightNumber;
    }

    public EventBusSubject getEventBusSubject()
    {
        return eventBusSubject;
    }
}
