/**
 * Authors: Alex Ahlrichs, Jared Hollenberger, Lincoln Schroeder
 *
 * Purpose: Node is used for making the tournament bracket. Each node has a list of playables contained in the node,
 * its list of children, and its parent.
 */

package TeamVierAugen.Tree;

import TeamVierAugen.Playables.Playable;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private Node parent;
    private List<Node> children;
    private List<Playable> players;

    /**
     * Constructor.
     * @param parent the parent node for this node.
     */
    public Node(Node parent)
    {
        this.parent = parent;
        children = new ArrayList<>();
        players = new ArrayList<>();
    }

    /**
     * Adds a node to the child list.
     * @param child the node to be added.
     */
    public void addChild(Node child)
    {
        children.add(child);
    }

    /**
     * Adds a playable to the players list.
     * @param playable the playable to be added.
     */
    public void addPlayable(Playable playable)
    {
        players.add(playable);
    }

    /**
     * Getter for this node's parent node. Can be null.
     * @return this node's parent node.
     */
    public Node getParent()
    {
        return parent;
    }

    /**
     * Getter for the child list.
     * @return children.
     */
    public List<Node> getChildren()
    {
        return children;
    }

    /**
     * Getter for the player list.
     * @return players.
     */
    public List<Playable> getPlayers()
    {
        return players;
    }

    /**
     * Setter for the list of children.
     * @param children the new list to be set to.
     */
    public void setChildren(List<Node> children)
    {
        this.children = children;
    }

    /**
     * Attempts to remove the desired playable from the list, if it exists.
     * @param playable the playable that the user wants removed.
     * @return true if successfully removed, false otherwise.
     */
    public boolean removePlayable(Playable playable)
    {
        if(players.contains(playable))
        {
            players.remove(playable);
            return true;
        }
        else
            return false;
    }

    public void clearPlayers()
    {
        players.clear();
        players = new ArrayList<>();
    }
}
