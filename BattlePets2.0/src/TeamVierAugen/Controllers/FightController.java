/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: This class holds a Fight object to store data. It executes all logic that takes
 * place during a fight, including checking for winners and ties.
 */
package TeamVierAugen.Controllers;

import TeamVierAugen.AttackEventShootTheMoon;
import TeamVierAugen.FightStartEvent;
import TeamVierAugen.GameSegments.Fight;
import TeamVierAugen.GameSegments.Round;
import TeamVierAugen.Playables.Playable;
import TeamVierAugen.PlayerEventInfo;

import java.util.ArrayList;
import java.util.List;

public class FightController {

    private InputOutputController ioController;
    private Fight fight;

    /**
     * This is the default constructor that instantiates the
     * IO Controller and the Fight object.
     */
    public FightController(EventBusSubject eventBusSubject)
    {
        ioController = new InputOutputController();
        fight = new Fight(eventBusSubject);
    }

    /**
     * This method returns the fight controlled by the class.
     * @return fight belonging to this controller class
     */
    public Fight getFight() {
        return fight;
    }

    /**
     * This method performs the fight. The fight continues to execute Rounds until
     * the HP of a player's pet is at or below zero. At the end of the fight, winners
     * are detected for. In the case of a tie, each player is considered a winner.
     * @return the list of players who won the fight
     */
    public List<Playable> completeOneFight()
    {
        reset();
        boolean fightOver = false;
        int roundCount = 1;
        List<Playable> winningPlayers = new ArrayList<Playable>();

        // The while loop goes through one cycle of a round. Pet HP is checked after the round is completed, and if
        // they are both below 0, the pet with the highest HP wins.
        while(!fightOver)
        {
            // Go through a single round.
            ioController.displayFightNum(fight.getFightNumber());
            ioController.displayRoundNum(roundCount);

            for(int i = 0; i < fight.getPlayerList().size(); i++)
                ioController.displayPlayerStats(fight.getPlayerList().get(i));

            RoundController roundController = new RoundController(fight.getEventBusSubject(), roundCount);
            roundController.getRound().setPlayerList(fight.getPlayerList());
            fight.setPlayerList(roundController.completeOneRound());

            // Check Pet HPs.

            roundCount++;
            fightOver = hasWinner();
            if(!fightOver)
            {
                for (int i = 0; i < fight.getPlayerList().size(); i++)
                {
                    if(fight.getPlayerList().get(i).getCurrentHp() <= 0) {
                        ioController.displayAsleepPets(fight.getPlayerList().remove(i));
                        i--;
                    }
                }
            }
        }

        for(int i = 0; i < fight.getPlayerList().size(); i++)
            ioController.displayPlayerStats(fight.getPlayerList().get(i));

        if(hasTie())
        {
            winningPlayers = getTiedWinners();
        }
        else
        {
            winningPlayers.add(getWinner());
        }
        return winningPlayers;
    }

    /**
     * This method resets the fight. It refills the HP of all pets of each player and sets
     * all skill recharge times to zero.
     */
    public void reset()
    {
        List<Playable> playerList = fight.getPlayerList();
        List<PlayerEventInfo> playerEventInfoList = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++)
        {
            PlayerEventInfo playerEventInfo = new PlayerEventInfo.PlayerEventInfoBuilder()
                    .withPlayerType(playerList.get(i).getPlayerType())
                    .withPetInstance(playerList.get(i).getPlayableId())
                    .withPetName(playerList.get(i).getPetName())
                    .withPetType(playerList.get(i).getPetType())
                    .withStartingHp(playerList.get(i).getStartingHp())
                    .build();
            playerEventInfoList.add(playerEventInfo);
        }
        FightStartEvent event = new FightStartEvent(fight.getFightNumber(), playerEventInfoList);
        fight.getEventBusSubject().setEvent(event);
    }

    /**
     * This method checks for a winner of the fight. A player wins if the HP of their
     * pet is higher than their opponents' pet's HP when any pet's HP is at or below zero.
     * @return true if their is a winner of the fight, false otherwise
     */
    private boolean hasWinner()
    {
        int numberOfSurvivingPets = fight.getPlayerList().size();

        for (int i = 0; i < fight.getPlayerList().size(); i++)
        {
            if(fight.getPlayerList().get(i).getCurrentHp() <= 0) {
                numberOfSurvivingPets--;
            }
        }

        if(numberOfSurvivingPets <= 1)
            return true;
        return false;
    }

    /**
     * This method gets the player who won the fight.
     * @return the player who wins the fight
     */
    private Playable getWinner()
    {
        Playable winner = fight.getPlayerList().get(0);

        for(int i = 1; i < fight.getPlayerList().size(); i++)
        {
            if(fight.getPlayerList().get(i).getCurrentHp() > winner.getCurrentHp())
                winner = fight.getPlayerList().get(i);
        }

        return winner;
    }

    /**
     * This method gets the list of players who won the fight in the
     * case of a tie.
     * @return the list of players who won the tied fight
     */
    private List<Playable> getTiedWinners()
    {
        double winningHp = getWinner().getCurrentHp();
        List<Playable> tiedPlayers = new ArrayList<Playable>();

        for(int i = 0; i < fight.getPlayerList().size(); i++)
        {
            if(fight.getPlayerList().get(i).getCurrentHp() == winningHp)
                tiedPlayers.add(fight.getPlayerList().get(i));
        }

        return tiedPlayers;
    }

    /**
     * This method checks if their is a tie at the end of a fight. There is a tie
     * if player's pets have equal HP when their HP is at or below zero.
     * @return true if their is a tie, false otherwise
     */
    private boolean hasTie()
    {
        double winningHp = getWinner().getCurrentHp();
        int numberOfWinners = 0;

        for(int i = 0; i < fight.getPlayerList().size(); i++)
        {
            if(fight.getPlayerList().get(i).getCurrentHp() == winningHp)
                numberOfWinners++;
        }

        if(numberOfWinners > 1)
            return true;
        return false;
    }
}
