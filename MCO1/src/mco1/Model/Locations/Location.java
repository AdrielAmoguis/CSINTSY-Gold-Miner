package mco1.Model.Locations;

public class Location {
    /**
     * Represents at which row the location is at
     */
    protected int row;
    /**
     * Represents at which column the location is at
     */
    protected int col;

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

}
