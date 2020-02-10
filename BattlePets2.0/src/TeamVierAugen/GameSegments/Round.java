/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Commenter: Zexin Liu
 *
 * Purpose: The Round class is used to simulate a single round of a fight. Each player chooses what skill they would
 * like to use, then damage is calculated and displayed.
 */
package TeamVierAugen.GameSegments;

import TeamVierAugen.Controllers.EventBusSubject;
import TeamVierAugen.Controllers.InputOutputController;
import TeamVierAugen.Damage;
import TeamVierAugen.Playables.Playable;
import TeamVierAugen.Playables.PlayerInstance;
import TeamVierAugen.RandomNumberGenerator;
import TeamVierAugen.Skills.Skills;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round
{

    private List<Playable> playerList;
    private Map<Playable, Damage> playerDamages;
    private Map<Playable, Skills> skillPredictions;
    private int roundNumber;
    private EventBusSubject eventBusSubject;


    /**
     * Default constructor.
     */
    public Round(EventBusSubject eventBusSubject)
    {
        playerDamages = new HashMap<>();
        skillPredictions = new HashMap<>();
        this.eventBusSubject = eventBusSubject;
    }

    /**
     * This method returns the list of players participating in the round.
     * @return the list of players in the round
     */
    public List<Playable> getPlayerList() {
        return playerList;
    }

    /**
     * Set the players into a list to participate in this round.
	  * @param playerList is the participant list
     */
    public void setPlayerList(List<Playable> playerList)
    {
        this.playerList = playerList;
    }

    /**
     * This method sets the map of Playables to their Damage objects created during the Round.
     * @param playerDamages map of Playables to Damage objects
     */
    public void setPlayerDamages(Map<Playable, Damage> playerDamages)
    {
        this.playerDamages = playerDamages;
    }

    /**
     * This method adds a new element to the map of Playables and Damages
     * @param player player key for the map
     * @param damage Damage object as value
     */
    public void addPlayerDamage(Playable player, Damage damage)
    {
        playerDamages.put(player, damage);
    }

    /**
     * This method gets the map of Playables to Damages
     * @return map of Playables to Damages
     */
    public Map<Playable, Damage> getPlayerDamages()
    {
        return playerDamages;
    }

    /**
     * This method resets all player skill predictions.
     */
    public void resetSkillPredictions()
    {
        playerList.forEach(player -> skillPredictions.put(player, null));
    }

    /**
     * This method adds a new skill prediction to the existing map.
     * @param player player as key
     * @param prediction skill prediction as value
     */
    public void addSkillPrediction(Playable player, Skills prediction)
    {
        skillPredictions.put(player, prediction);
    }

    /**
     * This method gets the skill predictions of players.
     * @return map of Playables to Skills
     */
    public Map<Playable, Skills> getSkillPredictions()
    {
        return skillPredictions;
    }

    public int getRoundNumber()
    {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber)
    {
        this.roundNumber = roundNumber;
    }

    public EventBusSubject getEventBusSubject()
    {
        return eventBusSubject;
    }
}
