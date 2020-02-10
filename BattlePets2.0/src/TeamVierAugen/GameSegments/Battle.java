/**
 * Authors: Lincoln Schroeder, Jared Hollenberger, Zexin Liu, Alex Ahlrichs, and Ross Baldwin
 *
 * Purpose: The Battle class defines a battle between the pets of players. A battle has
 * a user specified number of fightControllers, which must be an odd value. A player wins a battle
 * once they have won over fifty percent of the fightControllers of the battle.
 */
package TeamVierAugen.GameSegments;

import TeamVierAugen.Controllers.EventBusSubject;
import TeamVierAugen.Controllers.FightController;
import TeamVierAugen.Playables.Playable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battle {

    private List<FightController> fightControllers;
    private List<Playable> playerList;
    private Map<Playable, Integer> playerWins;
    private EventBusSubject eventBusSubject;

    /**
     * Constructor to create a new battle and read the number of fightControllers for the battle
     * Sets fightControllers ArrayList to create a new fight for the number entered
     */
    public Battle(int numFights, EventBusSubject eventBusSubject)
    {
        fightControllers = new ArrayList<>();
        this.eventBusSubject = eventBusSubject;
        for (int i = 0; i < numFights; i++)
        {
            fightControllers.add(new FightController(eventBusSubject));
        }
    }

    /**
     * This method gets the map of Playables to the number of wins held by the PlayerInstance.
     * @return map of Playables to the Playable's number of wins
     */
    public Map<Playable, Integer> getPlayerWins() {
        return playerWins;
    }

    /**
     * This method updates the number of wins for all Playables.
     * @param playerWins new map of Playables to number of wins
     */
    public void setPlayerWins(Map<Playable, Integer> playerWins) {
        this.playerWins = playerWins;
    }

    /**
     * This method returns the FightController object.
     * @return FightController object in the class
     */
    public List<FightController> getFightControllers() {
        return fightControllers;
    }

    /**
     * This method gets the list of players in the battle.
     * @return list of players in the battle
     */
    public List<Playable> getPlayerList() {
        return playerList;
    }

    /**
     * Sets the playerList and creates a HashMap for each player where the first spot is their name and second
     * is the amount of wins they have
     * @param playerList the list of players
     */
    public void setPlayerList(List<Playable> playerList)
    {
        this.playerList = playerList;
        playerWins = new HashMap<>();
        for(int i = 0; i < playerList.size(); i++)
            playerWins.put(playerList.get(i), 0);
    }

    /**
     * Adds a win counter to the player that won a fight a win
     * @param winners the list of winners where the winner will be awarded a win
     */
    public void addWin(List<Playable> winners)
    {
        for(int i = 0; i < winners.size(); i++)
            playerWins.put(winners.get(i), playerWins.get(winners.get(i)) + 1);
    }

    public EventBusSubject getEventBusSubject()
    {
        return eventBusSubject;
    }
}
