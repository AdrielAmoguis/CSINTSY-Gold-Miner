package mco1.Model;

public class Location {
    /**
     * Represents at which row the location is at
     */
    private int row;
    /**
     * Represents at which column the location is at
     */
    private int col;

    /**
     * Creates a Location at [row,col]
     * @param row row of Location
     * @param col column of Location
     */
    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

}
