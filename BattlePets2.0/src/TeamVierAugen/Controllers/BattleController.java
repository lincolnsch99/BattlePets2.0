/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: This class holds a Battle object to store data. It executes all logic that takes
 * place during a Battle.
 */
package TeamVierAugen.Controllers;

import TeamVierAugen.GameSegments.Battle;
import TeamVierAugen.Playables.Playable;

import java.util.List;

public class BattleController {

    private InputOutputController io;
    private int numFightsPerBattle;
    private Battle battle;


    /**
     * This constructor instantiates the IO Controller. It also instantiates
     * a Battle object based on the number of fights per battle (specified
     * by the user) and the list of players participating in the battle.
     * @param playerList list of players in the battle
     */
    public BattleController (List<Playable> playerList, EventBusSubject eventBusSubject)
    {
        io = new InputOutputController();
        numFightsPerBattle = io.getNumFights();
        battle = new Battle(numFightsPerBattle, eventBusSubject);
        battle.setPlayerList(playerList);
    }

    public BattleController (List<Playable> playerList, int numFights, EventBusSubject eventBusSubject)
    {
        io = new InputOutputController();
        numFightsPerBattle = numFights;
        battle = new Battle(numFightsPerBattle, eventBusSubject);
        battle.setPlayerList(playerList);
    }

    /**
     * Performs a battle, doing each fight in the process
     * After each fight, it will check for a winner and will keep fighting until a winner has been found
     */
    public Playable performBattle()
    {
        List<FightController> fightControllers = battle.getFightControllers();
        List<Playable> playerList = battle.getPlayerList();
        // Set the list of players for each fight and perform the fight
        for (int i = 0; i < numFightsPerBattle; i++)
        {
            fightControllers.get(i).getFight().setPlayerList(playerList);
            fightControllers.get(i).getFight().setFightNumber(i + 1);
            List<Playable> winners = fightControllers.get(i).completeOneFight();
            io.displayFightWinner(winners);
            battle.addWin(winners);
            Playable potentialWinner = findWinner();
            if(potentialWinner != null)
            {
                io.displayBattleWinner(potentialWinner);
                return potentialWinner;
            }
        }
        return null;
    }

    /**
     * Checks to see if a player has won
     * @return the index of the player if they have won, null if no winner
     */
    private Playable findWinner()
    {
        int maxIndex = 0;
        int numWins = 0;
        for(int i = 0; i < battle.getPlayerWins().size(); i++)
        {
            numWins += battle.getPlayerWins().get(battle.getPlayerList().get(i));
            if(battle.getPlayerWins().get(battle.getPlayerList().get(i)) >
                    battle.getPlayerWins().get(battle.getPlayerList().get(maxIndex)))
                maxIndex = i;
        }
        if(checkIfMajority(battle.getPlayerWins().get(battle.getPlayerList().get(maxIndex))))
            return battle.getPlayerList().get(maxIndex);
        if(battle.getPlayerWins().get(battle.getPlayerList().get(maxIndex)) == numFightsPerBattle / 2 &&
                numFightsPerBattle == numWins)
            return battle.getPlayerList().get(maxIndex);
        return null;
    }

    /**
     * Checks to see if a player has won the majority of the total battles
     * If they have won more than 50% they win
     * @param wins the amount of wins a player has
     * @return true if player has the majority, false if the player has not won the majority
     */
    private boolean checkIfMajority(int wins)
    {
        if((float)wins / (float)numFightsPerBattle > 0.5f)
            return true;
        return false;
    }

}
