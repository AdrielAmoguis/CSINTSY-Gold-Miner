package mco1.Model.Locations;

public abstract class Location {
    /**
     * Represents at which row the location is at
     */
    protected int row;
    /**
     * Represents at which column the location is at
     */
    protected int col;
    /**
     * True if the Location has been visited. False otherwise.
     */
    private boolean visitedStatus;

    /**
     * Creates a Location at [row,col]
     * @param row row of Location
     * @param col column of Location
     */
    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of this Location.
     * @return the row of this Location.
     */
    public int getRow(){
        return row;
    }

    /**
     * Returns the column of this Location.
     * @return the column of this Location.
     */
    public int getCol(){
        return col;
    }

    /**
     * Returns true if the Location has been visited.
     * @return true if the Location has been visited
     */
    public boolean isVisited(){
        return visitedStatus;
    }

    /**
     * Sets this Location as visited.
     */
    public void visit(){
        visitedStatus = true;
    }

}
