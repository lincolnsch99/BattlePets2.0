/**
 * Authors: Lincoln Schroeder
 *
 * Purpose: TreeIterator is used for iterating through the tournament tree.
 */

package TeamVierAugen.Tree;

import java.util.Iterator;
import java.util.Stack;

public class TreeIterator implements Iterator<Node>
{
    private Tree iterableTree;
    private Stack<Node> nodeStack;

    /**
     * Constructor. Initiates the nodeStack for keeping state.
     * @param iterableTree the tree that will be iterated over.
     */
    public TreeIterator(Tree iterableTree)
    {
        this.iterableTree = iterableTree;
        nodeStack = iterableTree.reverseLevelTraversal();
    }

    /**
     * Overrides the Iterator.hasNext.
     * @return true if there are more nodes left in the stack, false otherwise.
     */
    @Override
    public boolean hasNext()
    {
        if(nodeStack.empty())
            return false;
        return true;
    }

    /**
     * Overrides the Iterator.next.
     * @return the next node in the stack.
     */
    @Override
    public Node next()
    {
        return nodeStack.pop();
    }
}
