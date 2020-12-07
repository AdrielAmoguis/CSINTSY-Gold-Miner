package mco1.Model;
import mco1.Model.Locations.*;

public class Node {
    private Location location;
    private Node parent;
    private boolean isVisited;

    /**
     * Constructor for root Node. Creates a Node with empty parent.
     * @param location Location to be stored in stack
     */
    public Node(Location location){
        this.location = location;
        isVisited = false;
    }

    /**
     * Constructor for child Node. Creates a Node with existing parent.
     * @param location Location to be stored in stack
     * @param parent Parent of node, to be used in backtracking.
     */
    public Node(Location location, Node parent){
        this(location);
        this.parent = parent;
    }

    /**
     * Returns the Location stored in this Node.
     * @return the Location stored in this Node.
     */
    public Location getLocation(){
        return location;
    }

    /**
     * Returns the parent (if any) of this Node.
     * @return the parent of this Node (if any)
     */
    public Node getParent(){
        return parent;
    }

}
