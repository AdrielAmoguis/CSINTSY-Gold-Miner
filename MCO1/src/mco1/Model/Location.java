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
     * Initializes the current row and position of location
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
