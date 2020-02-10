/**
 * Title: Battle Pets
 *
 * Date: 10/11/2019
 *
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: The Main class is used to play Battle Pets. Games can be played between two
 * or more players. At the end of each game, a player can choose to play again or quit.
 */
package TeamVierAugen;

import TeamVierAugen.Controllers.GameController;
import TeamVierAugen.Controllers.InputOutputController;
import TeamVierAugen.Controllers.TournamentController;

public class Main
{
    private static GameController controller;
    private static TournamentController tournamentController;
    private static InputOutputController ioController = new InputOutputController();

    /**
     * This method creates GameController objects to run the game.
     * @param args system arguments
     */
    public static void main(String[] args)
    {
        int choice = ioController.getGameChoice();
        if (choice == 1) {
            // run game here
            controller = new GameController();
            controller.run();
        } else if (choice == 2) {
            // run tournament here
            tournamentController = new TournamentController();
            tournamentController.run();
        }
        while(ioController.getPlayAgain() == 1) {
            choice = ioController.getGameChoice();
            if (choice == 1) {
                // run game here
                controller = new GameController();
                controller.run();
            } else if (choice == 2) {
                // run tournament here
                tournamentController = new TournamentController();
                tournamentController.run();
            }
        }
        System.out.println("Thank you for playing!");
    }
}
