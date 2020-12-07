package mco1.Model.Locations;

public class Miner extends Location{
    /**
     * Represents which direction the miner is currently facing.
     * Uses angle degree notation: 0 = facing right, 90 = facing up,, 180 = facing right, 270 = facing down
     */
    private int front;
    public static final int RIGHT = 0;
    public static final int UP = 90;
    public static final int LEFT = 180;
    public static final int DOWN = 270;

    /**
     * Creates a Miner at [row,col]. Should be instantiated at [0,0]
     * @param row row of Miner
     * @param col column of Miner
     */
    public Miner(int row, int col) {
        super(row, col);
        front = 0;
    }

    /**
     * Returns the direction where miner is currently facing in angle degree notation.
     * @return direction where miner is currently facing in angle degree notation.
     */
    public int getFront(){
        return front;
    }

    /**
     * Used for resetting Miner's position back to upper left corner of grid.
     */
    public void reset(){
        row = 0;
        col = 0;
    }

    public void setCol(int col){
        this.col = col;
    }
    /**
     * Rotates the Miner 90 degrees clockwise.
     */
    public void rotate(){
        if (front == 0)
            front = 270;
        else
            front -= 90;
    }

    /**
     * Moves the miner relative to his front / current direction.
     * NOTE: This method is called in Board class ONLY if Miner's move is valid.
     */
    public void move(){
        switch(front){
            // Facing right
            case 0: col += 1; break;
            // Facing up
            case 90: row -= 1; break;
            // Facing left
            case 180: col -= 1; break;
            // Facing down
            case 270: row += 1; break;
        }
    }

    /**
     * Checks Miner's current position. Used for debugging purposes.
     */
    public void displayPosition(){
        System.out.println("[Miner Class] Miner Current Position: [" + row + ", " + col + "]");
    }


}
