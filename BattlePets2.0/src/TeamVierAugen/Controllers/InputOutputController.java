/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: The InputOutputController is used to get all input from players during gameplay.
 * It is also used to output all information about the game to players.
 */
package TeamVierAugen.Controllers;

import TeamVierAugen.Damage;
import TeamVierAugen.Playables.PetTypes;
import TeamVierAugen.Playables.Playable;
import TeamVierAugen.Playables.PlayerTypes;
import TeamVierAugen.Skills.Skills;
import TeamVierAugen.Playables.PlayerInstance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputOutputController
{
    //constant default input variables
    private final int DEFAULT_INPUT = 1;
    private final int CUSTOM_INPUT = 2;
    //constant player variables
    private final int MIN_PLAYERS = 2;
    private final int HUMAN_TYPE = 1;
    private final int AI_TYPE = 2;
    //constant pet type variables
    private int MIN_PET_TYPE = 1;
    private int MAX_PET_TYPE = 3;
    //constant HP variables
    private final int MIN_HP = 1;
    private final double DEFAULT_HP = 100;
    //constant fight variables
    private final int MIN_FIGHTS = 1;
    private final int DEFAULT_FIGHTS = 5;
    //constant seed variables
    private final int MIN_SEED = 0; // temp maybe change???
    private final int DEFAULT_SEED = 6;
    //constant skill variables
    private final int MIN_SKILL_NUM = 1;
    private final int MAX_SKILL_NUM = 5;
    //constant play again variables
    private final int PLAY_AGAIN = 1;
    private final int QUIT = 2;

    //Local class variables
    private Scanner inputReader = new Scanner(System.in);
    private int numPlayers;
    private int numPlayersPerBattle;
    private int playerNamesGot;
    private int petNamesGot;

    /**
     * This constructor creates a new InputOutputController.
     */
    public InputOutputController()
    {
        numPlayers = 0;
        playerNamesGot = 1;
        petNamesGot = 1;
    }
    /**
     * Outputs to take a number of players
     * Takes input until a number is chosen
     * Resets playerNamesGot and petNamesGot for if a new game is started
     * @return 1 if one player, 2 if two players
     */
    public int getNumPlayers()
    {
        System.out.println("Please enter the number of players (MIN 2)");
        numPlayers = readInteger();
        inputReader.nextLine();
        while(numPlayers < MIN_PLAYERS)
        {
            System.out.println(numPlayers + " is not valid, please enter a valid number of players");
            numPlayers = readInteger();
        }
        playerNamesGot = 1;
        petNamesGot = 1;
        return numPlayers;
    }

    public int getNumPlayersPerBattle()
    {
        System.out.println("Please enter the number of players per battle (MIN 2)");
        numPlayersPerBattle = readInteger();
        inputReader.nextLine();
        while(numPlayersPerBattle < MIN_PLAYERS)
        {
            System.out.println(numPlayersPerBattle + " is not valid, please enter a valid number of players");
            numPlayersPerBattle = readInteger();
        }
        return numPlayersPerBattle;
    }

    /**
     * Gets the name of the player and outputs the correct player's number
     * @return the name of the player
     */
    public String getPlayerName()
    {
        System.out.println("Please enter player " + playerNamesGot + "'s name:");
        String playerName = inputReader.nextLine();
        while(playerName.equals(""))
        {
            System.out.println("Please enter a valid player name:");
            playerName = inputReader.nextLine();
        }
        playerNamesGot++;
        return playerName;
    }

    public PlayerTypes getPlayerType()
    {
        System.out.println("Please enter a number for the type of player.");
        System.out.println("| Human = 1 | Computer = 2 |");
        int temp = readInteger();
        while (temp < HUMAN_TYPE  || temp > AI_TYPE)
        {
            System.out.println((temp + " is not valid, please enter a valid player type number"));
            temp = readInteger();
        }
        if (temp == HUMAN_TYPE)
        {

            return PlayerTypes.HUMAN;
        }
        else
        {

            return PlayerTypes.COMPUTER;
        }
    }

    /**
     * Gets the name of the pet and outputs the correct player's pet number
     * * @return the name of the pet
     */
    public String getPetName()
    {
        System.out.println("Please enter player " + petNamesGot + "'s pet name:");
        String petName = inputReader.nextLine();
        petNamesGot++;
        return petName;
    }

    public PetTypes getPetType()
    {
        System.out.println("Please enter a number for the type of pet.");
        System.out.println("| Power = 1 | Speed = 2 | Intelligence = 3 |");
        int temp = readInteger();
        while (temp < MIN_PET_TYPE  || temp > MAX_PET_TYPE)
        {
            System.out.println((temp + " is not valid, please enter a valid pet type number"));
            temp = readInteger();
        }
        if (temp == 1)
            return PetTypes.POWER;
        else if (temp == 2)
            return PetTypes.SPEED;
        else
            return PetTypes.INTELLIGENCE;
    }

    /**
     * Outputs to ask a player the initial HP for the pets
     * Takes input until valid default is chosen or valid custom is chosen
     * @return DEFAULT_HP if the default value will be used, the custom HP if custom is used
     */
    public double getInitialHP()
    {
        System.out.println("For default HP (100) please enter 1, for other please enter 2");
        double temp = readInteger();
        inputReader.nextLine();
        while(temp < DEFAULT_INPUT || temp > CUSTOM_INPUT)
        {
            System.out.println(temp + " is not valid, please enter a valid number (1 or 2)");
            temp = inputReader.nextDouble();
            inputReader.nextLine();
        }
        if(temp == DEFAULT_INPUT)
        {
            return DEFAULT_HP; //returns  DEFAULT_HP const and default hp will be set to 100
        }
        else
        {
            System.out.println("Please enter the initial HP of the pets (MIN 1)");
            temp =  readDouble();
            inputReader.nextLine();
            while(temp < MIN_HP)
            {
                System.out.println(temp + " is not valid, please enter a valid number of starting HP (MIN 1)");
                temp = readDouble();
                inputReader.nextLine();
            }
            return temp;
        }
    }

    /**
     * Outputs for a user to enter the number of fights
     * Takes input until valid numbers are entered
     * @return DEFAULT_FIGHTS if the default value will be used, the custom number if custom entered
     */
    public int getNumFights()
    {
        System.out.println("For default number of fights (5) please enter 1, for other please enter 2");
        int temp = readInteger();
        inputReader.nextLine();
        while(temp < DEFAULT_INPUT || temp > CUSTOM_INPUT)
        {
            System.out.println(temp + " is not valid, please enter a valid number (1 or 2)");
            temp = readInteger();
            inputReader.nextLine();
        }
        if(temp == DEFAULT_INPUT)
        {
            return DEFAULT_FIGHTS; //returns DEFAULT_FIGHTS and default fights will be 5
        }
        else
        {
            System.out.println("Please enter the number of fights.");
            System.out.println("The mininum number of fights is 1.");
            temp = readInteger();
            inputReader.nextLine();
            while(temp < MIN_FIGHTS)
            {
                System.out.println(temp + " is not valid, please enter a valid number of fights");
                temp = readInteger();
                inputReader.nextLine();
            }
            return temp;
        }
    }

    /**
     * Outputs for a user to enter the RNG seed number that will be used
     * Takes input until valid numbers are entered
     * @return -DEFAULT_SEED if the default RNG seed will be used, custom number for custom input
     */
    public int getRandomSeed()
    {
        System.out.println("For default RNG seed (5) please enter 1, for other please enter 2");
        int temp = readInteger();
        inputReader.nextLine();
        while(temp < DEFAULT_INPUT || temp > CUSTOM_INPUT)
        {
            System.out.println(temp + " is not valid, please enter a valid number (1 or 2)");
            temp = readInteger();
            inputReader.nextLine();
        }
        if(temp == DEFAULT_INPUT)
        {
            return DEFAULT_SEED; //returns DEFAULT_SEED cosnt and default RNG seed will be DEFAULTSEED current = 5
        }
        else
        {
            System.out.println("Please enter the RNG seed number one or greater");
            temp = readInteger();
            inputReader.nextLine();
            while(temp <= MIN_SEED)
            {
                System.out.println(temp + " is not valid, please enter a valid RNG seed number one or greater");
                temp = readInteger();
                inputReader.nextLine();
            }
            return temp;
        }
    }

    /**
     * Outputs for a user to enter the skill number that they would like to choose
     * Takes input until a valid skill is chosen
     * @param player the player that is being questioned.
     * @return 1 if Rock Throw, 2 if Scissors Poke, 3 if Paper Cut
     */
    public Skills getSkill(Playable player)
    {
        System.out.println(player.getPlayerName() + ", please enter a number for a skill to use.");
        System.out.println("| Rock Throw = 1 | Scissors Poke = 2 | Paper Cut = 3 | Shoot the Moon = 4 | " +
                "Reversal of Fortune = 5 |");
        int temp = readInteger();
        inputReader.nextLine();

        boolean validChoice = false;
        Skills chosenSkill = Skills.ROCK_THROW;

        while (!validChoice)
        {
            if((temp < MIN_SKILL_NUM  || temp > MAX_SKILL_NUM)) {
                System.out.println((temp + " is not valid, please enter a valid skill number"));
                temp = readInteger();
                inputReader.nextLine();
            }
            else {
                if (temp == 1)
                    chosenSkill = Skills.ROCK_THROW;
                else if (temp == 2)
                    chosenSkill = Skills.SCISSORS_POKE;
                else if (temp == 3)
                    chosenSkill = Skills.PAPER_CUT;
                else if (temp == 4)
                    chosenSkill = Skills.SHOOT_THE_MOON;
                else if (temp == 5)
                    chosenSkill = Skills.REVERSAL_OF_FORTUNE;

                if (player.getSkillRechargeTime(chosenSkill) > 0)
                {
                    System.out.println((chosenSkill.toString() + " is currently on cooldown. Please choose another " +
                            "skill"));
                    temp = readInteger();
                    inputReader.nextLine();
                }
                else
                    validChoice = true;
            }
        }
        return chosenSkill;
    }

    /**
     * This method gets a skill prediction of a player. It is used after a player
     * choose the Shoot the Moon skill.
     * @param player player to get a skill prediction from
     * @return the skill prediction of the player
     */
    public Skills getSkillPrediction(Playable player)
    {
        System.out.println(player.getPlayerName() + ", please enter your skill prediction");
        System.out.println("| Rock Throw = 1 | Scissors Poke = 2 | Paper Cut = 3 | Shoot the Moon = 4 | " +
                "Reversal of Fortune = 5 |");
        int temp = readInteger();
        inputReader.nextLine();

        boolean validChoice = false;
        Skills chosenSkill = Skills.ROCK_THROW;

        while (!validChoice)
        {
            if((temp < MIN_SKILL_NUM  || temp > MAX_SKILL_NUM)) {
                System.out.println((temp + " is not valid, please enter a valid skill number"));
                temp = readInteger();
                inputReader.nextLine();
            }
            else {
                if (temp == 1)
                    chosenSkill = Skills.ROCK_THROW;
                else if (temp == 2)
                    chosenSkill = Skills.SCISSORS_POKE;
                else if (temp == 3)
                    chosenSkill = Skills.PAPER_CUT;
                else if (temp == 4)
                    chosenSkill = Skills.SHOOT_THE_MOON;
                else if (temp == 5)
                    chosenSkill = Skills.REVERSAL_OF_FORTUNE;
                validChoice = true;
            }
        }
        return chosenSkill;
    }


    /**
     * Outputs to ask a player if they would like to play again or quit
     * Reads the input until correct input is entered
     * @return 1 if play again, 2 if quit
     */
    public int getPlayAgain()
    {
        System.out.println("Battle concluded... would you like to play again?");
        System.out.println("Enter 1 for yes, 2 for no");
        int temp = readInteger();
        inputReader.nextLine();
        while(temp < PLAY_AGAIN || temp > QUIT)
        {
            System.out.println(temp + " is not valid, please enter a valid number (1 or 2)");
            temp = readInteger();
            inputReader.nextLine();
        }
        if(temp == PLAY_AGAIN)
        {
            return PLAY_AGAIN;
        }
        else
        {
            return QUIT;
        }
    }

    /**
     * Displays if a pet falls asleep.
     * @param player The player who's pet has fallen asleep.
     */
    public void displayAsleepPets(Playable player)
    {
        System.out.println(player.getPlayerName() + "'s pet " + player.getPetName() + " is now asleep!");
    }

    /**
     * Displays the current fight number
     * @param fightNum The fight number to output
     */
    public void displayFightNum(int fightNum)
    {
        System.out.println("The current fight number is: " + fightNum);
    }

    /**
     * Displays the winner(s) of the fight.
     * @param winningPlayers the winner(s) of the fight.
     */
    public void displayFightWinner(List<Playable> winningPlayers)
    {
        if (winningPlayers.size() > 1)
        {
            System.out.println("There was a tie for the fight between these players:");
            for (int i = 0; i < winningPlayers.size(); i++)
                System.out.println(winningPlayers.get(i).getPlayerName());
        }
        else
        {
            System.out.println(winningPlayers.get(0).getPlayerName() + " won the fight!");
        }
    }

    /**
     * Displays the winner of the battle.
     * @param winner the PlayerInstance that won the battle.
     */
    public void displayBattleWinner(Playable winner)
    {
        System.out.println(winner.getPlayerName() + " has won this battle!");
    }

    /**
     * Displays the current round number
     * @param roundNum The round number to output
     */
    public void displayRoundNum(int roundNum)
    {
        System.out.println("The current round number is: " + roundNum);
    }

    /**
     * Displays a player's pet's HP status.
     * @param player The player being displayed.
     */
    public void displayPlayerStats(Playable player)
    {
        StringBuilder build = new StringBuilder();
        build.append(player.getPlayerName());
        build.append(": \n" + player.getPetName() + " (" + player.getPetType().toString() + ")");
        build.append(" - " + player.getCurrentHp() + "/" + player.getStartingHp() + '\n');
        build.append("Skill Recharge Times: \n");
        build.append("| Rock Throw: "
                + player.getSkillRechargeTime(Skills.ROCK_THROW) + " turns | ");
        build.append("Scissors Poke: "
                + player.getSkillRechargeTime(Skills.SCISSORS_POKE) + " turns | ");
        build.append("Paper Cut: "
                + player.getSkillRechargeTime(Skills.PAPER_CUT) + " turns | ");
        build.append("Shoot the Moon: "
                + player.getSkillRechargeTime(Skills.SHOOT_THE_MOON) + " turns | ");
        build.append("Reversal of Fortune: "
                + player.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) + " turns |\n");
        System.out.println(build);
    }

    /**
     * Displays the random and conditional damage done to a player by the attacker.
     * @param damage the Damage object with correect damage values.
     * @param playerHit the PlayerInstance that took the damage.
     */
    public void displayDamage(Damage damage, Playable playerHit)
    {
        StringBuilder build = new StringBuilder();
        build.append(playerHit.getPetName() + " has been hit for "
                + damage.getRandomDamage() + " random damage ");
        build.append("and " + damage.getConditionalDamage() + " conditional damage,\n");
        build.append("for a total of " + damage.calculateTotalDamage() + " total damage!\n");
        System.out.println(build);
    }

    /**
     * Reads a input until a valid integer has been inputted to avoid input mismatch exceptions
     * @return the valid integer
     */
    private int readInteger()
    {
        while(!inputReader.hasNextInt())
        {
            inputReader.next();
            System.out.println("Please enter a valid integer.");
        }
        return inputReader.nextInt();
    }

    /**
     * Reads a input until a valid double has been inputted to avoid input mismatch exceptions
     * @return the valid integer
     */
    private double readDouble()
    {
        while(!inputReader.hasNextDouble())
        {
            inputReader.next();
            System.out.println("Please enter a valid double.");
        }
        return inputReader.nextDouble();
    }

    public int getGameChoice()
    {
        System.out.println("Enter 1 for regular mode or 2 for tournament mode");
        int choice = readInteger();
        while (choice != 1 && choice != 2)
            choice = readInteger();
        return choice;
    }

    /**
     * Displays the winner of the tournament.
     * @param winner the PlayerInstance that won the battle.
     */
    public void displayTournamentWinner(Playable winner)
    {
        System.out.println(winner.getPlayerName() + " has won the tournament!");
    }
}
