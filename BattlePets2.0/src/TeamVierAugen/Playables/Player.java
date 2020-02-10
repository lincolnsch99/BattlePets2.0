/**
 * Authors: Lincoln Schroeder
 *
 * Commenter: Jared Hollenberger
 *
 * Purpose: The Player class stores the name and type of a player.
 */
package TeamVierAugen.Playables;

public class Player
{
    private String name;
    private PlayerTypes type;

    /**
     * Constructs the Person class
     * @param name
     * @param type
     */
    public Player(String name, PlayerTypes type)
    {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the player's name.
     * @return string name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the player's type.
     * Not used.
     * @return playerType type
     */
    public PlayerTypes getType()
    {
        return this.type;
    }
}
