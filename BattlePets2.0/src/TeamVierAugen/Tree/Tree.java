/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Purpose: The Tree class is a basic tree, but modified to generate its own branches and leaves
 * for the desired bracket.
 */

package TeamVierAugen.Tree;

import TeamVierAugen.Playables.Playable;

import java.util.*;


public class Tree implements Iterable<Node>
{

    private Node root;

    public Tree (Node root)
    {
        this.root = root;
    }

    /**
     * Uses Doug's algorithm to generate a tree representing the bracket of playables.
     * @param numPlayersPerBattle the number of playables per battle.
     * @throws Exception if the tree root is null, or if there aren't enough playables for the desired playables per
     * battle.
     */
    public void generateTree(int numPlayersPerBattle) throws Exception
    {
        // Double check to make sure all conditions are met to create a tree
        if(root == null)
            throw new Exception("Tree root is NULL");
        else if(numPlayersPerBattle > root.getPlayers().size())
            throw new Exception("Players per battle is too large for player pool");

        // Preset necessary variables
        int cumulativeNumPlayersPerBattle = numPlayersPerBattle;
        int cumulativeNumPlayers = root.getPlayers().size();
        Stack<Playable> cumulativePlayerStack = new Stack<>();
        for(Playable playable : root.getPlayers())
            cumulativePlayerStack.push(playable);
        Node parentNode = root;

        // Loop through the tree, creating new nodes based off of Doug's algorithm
        while(cumulativeNumPlayers > 0)
        {
            // Create a child for each player per battle
            for(int i = 0; i < numPlayersPerBattle; i++)
            {
                parentNode.addChild(new Node(parentNode));
            }

            // For each child, use Doug's algorithm to calculate how many playables should be put in each node. Then
            // pull from the playable stack in order to get those players.
            List<Node> childDuplicates = parentNode.getChildren();
            for(int i = 0; i < childDuplicates.size(); i++)
            {
                int numPlayersInNode = (int) Math.ceil((double) cumulativeNumPlayers / (double) cumulativeNumPlayersPerBattle);
                for (int j = 0; j < numPlayersInNode; j++)
                {
                    if(!cumulativePlayerStack.isEmpty())
                        childDuplicates.get(i).addPlayable(cumulativePlayerStack.pop());
                }
                cumulativeNumPlayers -= numPlayersInNode;
                cumulativeNumPlayersPerBattle--;
            }
            parentNode.setChildren(childDuplicates);

            // Find the next node that needs to be split into more battles. If null, cumulativeNumPlayers will stay
            // as 0, and the while loop shall stop running.
            parentNode = getNextParent(root, numPlayersPerBattle);
            if(parentNode != null)
            {
                cumulativeNumPlayers = parentNode.getPlayers().size();
                cumulativeNumPlayersPerBattle = numPlayersPerBattle;
                cumulativePlayerStack.clear();
                for(Playable playable : parentNode.getPlayers())
                    cumulativePlayerStack.push(playable);
            }
        }

        // Now that all the players have been split up, search through the nodes and clear any nodes
        // that have too many playables in them.
        Node deletionNode = getNextOverpopulatedNode(root, numPlayersPerBattle);
        while(deletionNode != null)
        {
            deletionNode.clearPlayers();
            deletionNode = getNextOverpopulatedNode(root, numPlayersPerBattle);
        }
    }

    /**
     * Uses recursion to search through the tree from the given root node, checking if any nodes have too many players
     * inside, thus needing to be split up. If it finds such a node, return it, otherwise returns null, signifying that
     * all nodes are split correctly.
     * @param thisRoot the root node to be searched through.
     * @param numPlayersPerBattle the number of playables per battle.
     * @return the next parent node that needs to be split, null if none are found.
     */
    private Node getNextParent(Node thisRoot, int numPlayersPerBattle)
    {
        if(thisRoot.getPlayers().size() > numPlayersPerBattle  && thisRoot.getChildren().size() == 0)
            return thisRoot;
        for(Node node : thisRoot.getChildren())
        {
            Node testParent = getNextParent(node, numPlayersPerBattle);
            if(testParent != null)
                return testParent;
        }
        return null;
    }

    /**
     * Uses recursion to search through the tree and find the next node that has too many players.
     * @param thisRoot the root node to be searched through.
     * @param numPlayersPerBattle the number of playables per battle.
     * @return the next parent node that is overpopulated.
     */
    private Node getNextOverpopulatedNode(Node thisRoot, int numPlayersPerBattle)
    {
        if(thisRoot.getPlayers().size() > numPlayersPerBattle)
            return thisRoot;
        for(Node node : thisRoot.getChildren())
        {
            Node testParent = getNextOverpopulatedNode(node, numPlayersPerBattle);
            if(testParent != null)
                return testParent;
        }
        return null;
    }

    /**
     * Creates a stack such that it pops in the correct order for conducting the tournament battles.
     * @return a stack containing all nodes in the tree, in order of reverse level traversal.
     */
    public Stack<Node> reverseLevelTraversal()
    {
        Stack<Node> playerStack = new Stack();
        List<Node> playerList = new ArrayList();
        int numLevels = 0;
        Queue<Node> playerQueue = new LinkedList<>();
        Node tempNode;
        List<Node> tempList;

        playerQueue.add(root);
        while(!playerQueue.isEmpty())
        {
            numLevels = playerQueue.size();
            while (numLevels > 0)
            {
                tempNode = playerQueue.remove();
                playerStack.push(tempNode);
                if (tempNode.getChildren().size() != 0)
                {
                    tempList = tempNode.getChildren();
                    for (int i = 0; i < tempList.size(); i++)
                    {
                        playerQueue.add(tempList.get(i));
                    }
                }
                numLevels--;
            }
        }
        return playerStack;
    }

    /**
     * Getter for the root.
     * @return the root of this tree.
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Setter for the root.
     * @param root the new root of the tree.
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * Overrides the Iterable.iterator.
     * @return a TreeIterator for this specific tree.
     */
    @Override
    public Iterator<Node> iterator()
    {
        return new TreeIterator(this);
    }
}
