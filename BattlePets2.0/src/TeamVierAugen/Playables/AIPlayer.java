/**
 * This class represents values for an AIPlayer.
 */
package TeamVierAugen.Playables;

import TeamVierAugen.Skills.Skills;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer
{
    private final int RANDOM_SEED = 5;

    private String name;
    private PlayerTypes type;

    public AIPlayer(String name, PlayerTypes type)
    {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the AI's name.
     * @return string name
     */
    public String getName() { return name; }

    /**
     * Gets the AI's type.
     * @return playerType type
     */
    public PlayerTypes getType() { return type; }
}
