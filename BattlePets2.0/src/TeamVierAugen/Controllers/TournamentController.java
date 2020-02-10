package TeamVierAugen.Controllers;

import TeamVierAugen.Playables.*;
import TeamVierAugen.RandomNumberGenerator;
import TeamVierAugen.Tree.Node;
import TeamVierAugen.Tree.Tree;
import TeamVierAugen.Tree.TreeIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TournamentController {

    private List<Playable> players;
    private InputOutputController io;
    private int numPlayers;
    private int numPlayersPerBattle;
    private Tree bracket;
    private int numFights;
    private BattleController battle;
    private EventBusSubject eventBusSubject;

    /**
        Default constructor
     */
    public TournamentController()
    {
        io = new InputOutputController();
        RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator.getInstance();
        randomNumberGenerator.setSeed(io.getRandomSeed());
        numPlayers = io.getNumPlayers();
        numPlayersPerBattle = io.getNumPlayersPerBattle();
        eventBusSubject = new EventBusSubject();
    }

    /**
     * This method generates a tournament bracket and performs all battles in the bracket.
     */
    public void run()
    {
        players = new ArrayList<Playable>();
        for(int i = 0; i < numPlayers; i++)
        {
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
        generateBracket();
        numFights = io.getNumFights();
        tournamentBattle();
    }

    /**
     * This method generates a tournament bracket based on the number of playables
     * in the tournament and per battle.
     */
    private void generateBracket()
    {
        Node root = new Node(null);
        for(Playable playable : players)
            root.addPlayable(playable);
        bracket = new Tree(root);
        try
        {
            bracket.generateTree(numPlayersPerBattle);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method executes a tournament battle and stores the winner in the parent node.
     */
    private void tournamentBattle()
    {
        Iterator<Node> iterator = bracket.iterator();
        List<Playable> losers = new ArrayList<>();
        Playable winner = null;
        while (iterator.hasNext())
        {
            Node currentFight = iterator.next();
            eventBusSubject = new EventBusSubject();
            for (int i = 0; i < currentFight.getPlayers().size(); i++)
                eventBusSubject.addObserver(currentFight.getPlayers().get(i));

            if (currentFight.getPlayers().size() > 1)
            {
                battle = new BattleController(currentFight.getPlayers(), numFights, eventBusSubject);
                winner = battle.performBattle();

                if(currentFight.getParent() != null)
                    currentFight.getParent().addPlayable(winner);
            }
            else
            {
                currentFight.getParent().addPlayable(currentFight.getPlayers().get(0));
            }
        }
        io.displayTournamentWinner(winner);
    }
}
