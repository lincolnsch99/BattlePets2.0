/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: This class gets the game settings from the user and creates a list
 * of players based on these settings. It instantiates a BattleController object to begin the game.
 */

package TeamVierAugen.Controllers;

import TeamVierAugen.GameSegments.Battle;
import TeamVierAugen.Playables.*;
import TeamVierAugen.PlayerEventInfo;
import TeamVierAugen.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class GameController
{
    private BattleController battleController;
    private InputOutputController io;
    private int numPlayers;
    private EventBusSubject eventBusSubject;
	 
    /**
     * Default constructor.
	  * Initialize an IO instance, a random generator instance and a battle instance. 
     */
    public GameController()
    {
        io = new InputOutputController();
        RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator.getInstance();
        randomNumberGenerator.setSeed(io.getRandomSeed());
        numPlayers = io.getNumPlayers();
        eventBusSubject= new EventBusSubject();
    }

    /**
     * Run the game after inputting some game parameters.
	  * Need to input the name of player, the type of player;
	  * Need to input the name of pet, the type of pet;
	  * Need to input initial HP value of player.
     */
    public void run()
    {
        List<Playable> players = new ArrayList<Playable>();
        for(int i = 0; i < numPlayers; i++)
        {
            PlayerEventInfo.PlayerEventInfoBuilder playerEventInfoBuilder = new PlayerEventInfo.PlayerEventInfoBuilder();
            String playerName = io.getPlayerName();
            String petName = io.getPetName();
            PetTypes petType = io.getPetType();
            PlayerTypes playerType = io.getPlayerType();
            if(playerType == PlayerTypes.HUMAN)
            {
                PlayerInstance player = new PlayerInstance(playerName, playerType, petName,
                        petType, i);
                player.setPetStartingHp(io.getInitialHP());
                players.add(player);
                eventBusSubject.addObserver(player);
            }
            else
            {
                AIPlayerInstance player = new AIPlayerInstance(playerName, playerType, petName,
                        petType, i);
                player.setPetStartingHp(io.getInitialHP());
                players.add(player);
                eventBusSubject.addObserver(player);
            }           
        }
        battleController = new BattleController(players, eventBusSubject);
        battleController.performBattle();
    }
}
