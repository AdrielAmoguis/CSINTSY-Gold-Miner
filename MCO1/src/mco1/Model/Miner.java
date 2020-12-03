package mco1.Model;

public class Miner extends Location{
    /**
     * Represents which direction the miner is currently facing.
     * Uses angle degree notation: 0 = facing right, 90 = facing up,, 180 = facing right, 270 = facing down
     */
    private int front;

    /**
     * Creates a Miner at [row,col]. Should be instantiated at [0,0]
     * @param row row where miner is at
     * @param col column where miner is at
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
     * Rotates the Miner 90 degrees clockwise.
     */
    public void rotate(){
        if (front == 0)
            front = 270;
        else
            front -= 90;
    }


}
