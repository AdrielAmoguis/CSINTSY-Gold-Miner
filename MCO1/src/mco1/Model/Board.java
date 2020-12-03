package mco1.Model;
import java.util.*;

public class Board {
    /**
     * 2D ArrayList Representation of board
     */
    private ArrayList<ArrayList<Location>> map;
    /**
     * Represents the miner.
     */
    private Miner minerAgent;
    /**
     * Represents the Golden Square.
     */
    private GoldenSquare goal;

    /**
     * Creates an nxn board with Empty locations.
     * @param size dimension of board.
     */
    public Board(int size){
        // Instantiate ArrayList
        map = new ArrayList<>();
        for (int row = 0; row < size; row++){
            // Add new row
            map.add(new ArrayList<>());
            for (int col = 0; col < size; col++)
                // Fill current row with Empty locations
                map.get(row).add(new Empty(row, col));
        }
        // Initialize miner and place at upper-left corner of grid
        minerAgent = new Miner(0,0);
    }

    /**
     *  Returns the dimension of board.
     *  @return the dimension of board
     */
    public int getBoardSize(){
        return map.size();
    }

    /**
     * Replaces the Location at [row - 1,col - 1] with GoldenSquare
     * @param row row of Location (in normal notation)
     * @param col column of Location (in normal notation)
     */
    public void placeGold(int row, int col){
        if(validPosition(row, col)){
            // decrement by 1 to follow index notation
            row -= 1;
            col -= 1;
            // place in map
            map.get(row).set(col, new GoldenSquare(row, col));
            // set in class attributes for easy access
            goal = (GoldenSquare)map.get(row).get(col);
        }
    }

    /**
     * Replaces the Location at [row - 1,col - 1] with Pit
     * @param row row of Location (in normal notation)
     * @param col column of Location (in normal notation)
     */
    public void placePit(int row, int col){
        if (validPosition(row, col)){
            row -= 1;
            col -= 1;
            map.get(row).set(col, new Pit(row, col));
        }
    }

    /**
     * Replaces the Location at [row - 1, col - 1] with Beacon (if valid).
     * @param row row of Location (in normal notation)
     * @param col column of Location (in normal notation)
     */
    public void placeBeacon(int row, int col){
        if (validPosition(row, col)){
            row -= 1;
            col -= 1;
            // Check if GoldenSquare goal has been placed.
            // Although GoldenSquare should be placed first, one more line of defence.
            try{
                int goldRow = goal.getRow();
                int goldCol = goal.getCol();
                int distance = -1;
                // if Beacon is in same row as goal, calculate distance between their columns.
                if (row == goldRow)
                    distance = Math.abs(goldCol - col);
                // if Beacon is in same column as goal, calculate distance between their rows.
                else if (col == goldRow)
                    distance = Math.abs(goldRow - row);
                else{
                    throw new Exception("Beacon is not in line with goal");
                }
                map.get(row).set(col, new Beacon(row, col, distance));

            }
            catch (Exception e){
                System.out.println(e.toString());
                System.out.println("Initialize GoldenSquare (goal) first before placing other elements");
            }
        }
    }

    /**
     * Returns true if the row & column input is valid.
     * Input is valid when: (1) Row & Column is within range of size of map IN NORMAL NOTATION [1, size]
     * @param row row of Location to be placed
     * @param col row of Location to be placed
     * @return true if input is valid, false if otherwise
     */
    private boolean validPosition(int row, int col){
        return (row >= 1 && row <= getBoardSize()) && (col >= 1 && col <= getBoardSize());
    }

    public void displayMap(){
        for (int row = 0; row < getBoardSize(); row++){
            for (int col = 0; col < getBoardSize(); col++){
                char ch = 'E';
                Location current = map.get(row).get(col);
                if (current instanceof Pit)
                    ch = 'P';
                else if (current instanceof GoldenSquare)
                    ch = 'G';
                else if (current instanceof Beacon)
                    ch = 'B';
                System.out.print(ch + "| ");
            }
            System.out.println();
        }
    }
}

