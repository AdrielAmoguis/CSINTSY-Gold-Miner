package mco1.Model;
import mco1.Model.Locations.*;

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
    private int nRotates;
    private int nScans;
    private int nMoves;
    /**
     * Represents the current status of the Miner.
     * 0 - ongoing
     * 1 - Goal reached
     * 2 - Fell in Pit
     * 3 - No solution
     */
    private int status;

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
        // Reset counters
        Beacon.resetCounter();
        GoldenSquare.resetCounter();
        Pit.resetCounter();
        status = 0; // ongoing
    }

    /**
     *  Returns the dimension of the map.
     *  @return the dimension of the map
     */
    public int getMapSize(){
        return map.size();
    }

    /**
     * Returns the Miner to access its methods.
     * @return the Miner
     */
    public Miner getMinerAgent() {
        return minerAgent;
    }

    /**
     * Returns the Location at a specific square.
     * @return the Location.
     */
    public Location getSquare(int row, int col) {
        return map.get(row-1).get(col-1);
    }

    /**
     * Returns the number of rotations.
     * @return the number of rotations
     */
    public int getnRotates(){
        return nRotates;
    }

    /**
     * Returns the number of scans.
     * @return the number of scans.
     */
    public int getnScans() {
        return nScans;
    }

    /**
     * Returns the number of moves done.
     * @return the number of moves done.
     */
    public int getnMoves() {
        return nMoves;
    }

    /**
     * Returns the status of the Miner.
     * @return 0 - ongoing, 1 - goal reached, 2 - fell in Pit, 3 - no solution
     */
    public int getStatus(){
        Location currentLocation = map.get(minerAgent.getRow()).get(minerAgent.getCol());
        if (currentLocation instanceof Empty || currentLocation instanceof Beacon)
            return 0;
        else if (currentLocation instanceof GoldenSquare)
            return 1;
        else
            return 2;
    }
    /**
     * Replaces the Location at [row - 1,col - 1] with GoldenSquare.
     * Can only place when there input is valid and there is no existing GoldenSquare.
     * @param row row of Location (in normal notation)
     * @param col column of Location (in normal notation)
     */
    public void placeGold(int row, int col){
        if(validPosition(row, col) && !GoldenSquare.isSet()){
            // decrement by 1 to follow index notation
            row -= 1;
            col -= 1;
            if (isEmpty(row, col)) {
                // place in map
                map.get(row).set(col, new GoldenSquare(row, col));
                // set in class attributes for easy access
                goal = (GoldenSquare) map.get(row).get(col);
                GoldenSquare.increment();
            }
        }
    }

    /**
     * Replaces the Location at [row - 1,col - 1] with Pit
     * @param row row of Location (in normal notation)
     * @param col column of Location (in normal notation)
     */
    public void placePit(int row, int col){
        if (validPosition(row, col) && !Beacon.isSet()){
            row -= 1;
            col -= 1;
            if (isEmpty(row, col)){
                map.get(row).set(col, new Pit(row, col));
                Pit.increment();
            }
        }
    }

    /**
     * Replaces the Location at [row - 1, col - 1] with Beacon (if valid).
     * @param row row of Location (in normal notation)
     * @param col column of Location (in normal notation)
     */
    public boolean placeBeacon(int row, int col) {
        // Check if GoldenSquare goal has been placed.
        if (validPosition(row, col) && GoldenSquare.isSet()){
            row -= 1;
            col -= 1;
            // If Location is Empty
            if (isEmpty(row, col)){
                // Exception handling for : (1) Beacon not in line with goal
                //                          (2) Pit obstructing Beacon from goal
                try{
                    int goldRow = goal.getRow();
                    int goldCol = goal.getCol();
                    int distance, direction;
                    // if Beacon is in same row as goal, calculate distance between their columns.
                    if (row == goldRow){
                        direction = goldCol - col;
                        distance = Math.abs(direction);
                        // Beacon at right of Gold, check for Pits
                        if (direction < 0){
                            for (int testCol = col; testCol > goldCol; testCol--)
                                if (map.get(row).get(testCol) instanceof Pit)
                                    throw new Exception("Pit blocking beacon to goal. Cannot place");
                        }
                        // Beacon at left of Gold, check for Pits
                        else{
                            for (int testCol = col; testCol < goldCol; testCol++)
                                if (map.get(row).get(testCol) instanceof Pit)
                                    throw new Exception("Pit blocking beacon to goal. Cannot place");
                        }
                    }

                    // if Beacon is in same column as goal, calculate distance between their rows.
                    else if (col == goldCol){
                        direction = goldRow - row;
                        distance = Math.abs(goldRow - row);
                        // Beacon is below Gold, check for Pits
                        if (direction < 0){
                            for (int testRow = row; testRow > goldRow; testRow--){
                                if (map.get(testRow).get(col) instanceof Pit)
                                    throw new Exception("Pit blocking beacon to goal. Cannot place");
                            }
                        }
                        // Beacon is above Gold, check for Pits
                        else{
                            for (int testRow = row; testRow < goldRow; testRow++){
                                if (map.get(testRow).get(col) instanceof Pit)
                                    throw new Exception("Pit blocking beacon to goal. Cannot place");
                            }
                        }
                    }
                    // Beacon not in line with goal
                    else{
                        throw new Exception("Beacon is not in line with goal");
                    }

                    // Check if there are any pits obstructing path from Beacon to Goal

                    map.get(row).set(col, new Beacon(row, col, distance));
                    Beacon.increment();
                }
                catch (Exception e){
                    System.out.println(e.toString());
                    //System.out.println("Initialize GoldenSquare (goal) first before placing other elements");
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Resets the square at [row - 1, col - 1] as Empty.
     * @param row row of Location (in normal notation)
     * @param col column of Location (in normal notation)
     */
    public void resetSquare(int row, int col){
        if (validPosition(row, col)){
            row -= 1;
            col -= 1;
            Location location = map.get(row).get(col);
            // if square is not already Empty, set as Empty
            if (!(location instanceof Empty)){
                // if Beacon
                if (location instanceof Beacon)
                    Beacon.decrement();
                // if GoldenSquare
                else if (location instanceof GoldenSquare)
                    GoldenSquare.decrement();
                // if Pit
                else if (location instanceof Pit)
                    Pit.decrement();
                map.get(row).set(col, new Empty(row, col));
            }
        }
    }

    /**
     * Returns the nearest Location in front of Miner (not including Empty).
     * Also increments nScans counter.
     * @return Nearest location in front of Miner. Returns null if none
     */
    public Location farScan(){
         int minerRow = minerAgent.getRow();
         int minerCol = minerAgent.getCol();
         List<Location> scannedLocations = new ArrayList<>();
         nScans += 1;
         switch(minerAgent.getFront()){
             // Facing right -> scan (Miner's Column, Row's Last Column]
             case 0:
                 if (minerAgent.getCol() != getMapSize() - 1)
                    scannedLocations = map.get(minerRow).subList(minerCol + 1, getMapSize());
                 break;
             // Facing left -> scan [Row's First Column, Miner's Column)
             case 180:
                 for (int col = minerCol - 1; col > 0; col--)
                     scannedLocations.add(map.get(minerRow).get(col));
                 break;
             // Facing up -> scan [Miner's Column, Column's First Row]
             case 90:
                 for (int row = minerRow - 1; row > 0; row--)
                     scannedLocations.add(map.get(row).get(minerCol));
                 break;
             // Facing down -> scan [Miner's Column, Column's Last Row]
             case 270:
                 for (int row = minerRow + 1; row < getMapSize(); row++)
                     scannedLocations.add(map.get(row).get(minerCol));
                 break;
         }
         for (int i = 0; i < scannedLocations.size(); i++){
             // returns the first Location that is not Empty
             if (!(scannedLocations.get(i) instanceof Empty)){
                 // System.out.println("[Board] Scanned Location: " + scannedLocations.get(i).getClass().getName());
                 return scannedLocations.get(i);
             }
         }
         // if no Locations scanned, return null
         // System.out.println("[Board] No scanned Locations");
         return null;
    }

    /**
     * Returns the Location directly in front of Miner.
     * @return the Location directly in front of Miner.
     */
    public Location nearScan(){
        switch(minerAgent.getFront()){
            case 0:
                // If miner is at right edge, no Location to be returned
                if (minerAgent.getCol() == getMapSize() - 1)
                    return null;
                else // return right side
                    return map.get(minerAgent.getRow()).get(minerAgent.getCol() + 1);
            case 90:
                // If miner is at upper edge, no Location to be returned
                if (minerAgent.getRow() == 0)
                    return null;
                else // return top side
                    return map.get(minerAgent.getRow() - 1).get(minerAgent.getCol());
            case 180:
                // If miner is at left edge, no Location to be returned
                if (minerAgent.getCol() == 0)
                    return null;
                else  // return left side
                    return map.get(minerAgent.getRow()).get(minerAgent.getCol() - 1);
            case 270:
                // If miner is at lower edge, no Location to be returned
                if (minerAgent.getRow() == getMapSize() - 1)
                    return null;
                else // return lower side
                    return map.get(minerAgent.getRow() + 1).get(minerAgent.getCol());
            default:
                System.out.println("[Board] Error in nearScan(), front value faulty");
                return null;
        }
        // Check for array index out of bounds
    }
    /**
     * Moves the miner one position to his front. Initially checks if move is valid.
     * Also increments nMoves counter.
     */
    public void moveMiner(){
        if (validMove()){
            minerAgent.move();
            nMoves += 1;
        }
        else{
            System.out.println("[Board Class] Invalid move.");
        }

    }

    /**
     * Rotates the miner by 90 degrees.
     * Also increments nRotates counter.
     */
    public void rotateMiner(){
        minerAgent.rotate();
        nRotates += 1;
    }

    /**
     * Resets the position of the miner to upper left corner of grid.
     * Also resets the counters.
     */
    public void resetMiner(){
        minerAgent.reset();
        nRotates = 0;
        nScans = 0;
        nMoves = 0;
        status = 0;
    }

    /**
     * Sets status as 3: No solution.
     * Used after searching the whole map and stack becomes empty.
     */
    public void setNoSolution(){
        status = 3;
    }

    /**
     * Returns true if the row & column input is valid.
     * Input is valid when: (1) Row & Column is within range of size of map IN NORMAL NOTATION [1, size]
     * @param row row of Location to be placed
     * @param col row of Location to be placed
     * @return true if input is valid, false if otherwise
     */
    private boolean validPosition(int row, int col){
        return (row >= 1 && row <= getMapSize()) && (col >= 1 && col <= getMapSize());
    }

    /**
     * Returns true if the Location is Empty.
     * @param row row of Location
     * @param col column of Location
     * @return true if the Location is Empty.
     */
    private boolean isEmpty(int row, int col){
        return map.get(row).get(col) instanceof Empty;
    }

    /**
     * Checks the Miner's current direction and position.
     * Returns true if the Miner does not exit map after moving relative to front and position.
     * @return true if the Miner does not exit map after moving.
     */
    private boolean validMove(){
        int minerDirection = minerAgent.getFront();
        boolean validMove = true;
        // If Miner is facing right and is at right edge of map
        if (minerDirection == 0 && minerAgent.getCol() == getMapSize() - 1)
            validMove = false;

        // If Miner is facing left and is at left edge of map
        else if (minerDirection == 180 && minerAgent.getCol() == 0)
            validMove = false;

        // If Miner is facing up and is at top edge of map
        else if (minerDirection == 90 && minerAgent.getRow() == 0)
            validMove= false;

        // If Miner is facing down
        else if (minerDirection == 270 && minerAgent.getRow() == getMapSize() - 1)
            validMove = false;

        return validMove;
    }

    public void displayMap(){
        for (int row = 0; row < getMapSize(); row++){
            for (int col = 0; col < getMapSize(); col++){
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
        System.out.println();
    }
}

