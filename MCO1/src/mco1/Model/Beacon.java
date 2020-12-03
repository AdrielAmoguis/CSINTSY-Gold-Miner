package mco1.Model;

public class Beacon extends Location{
    /**
     * Distance from Beacon to Golden Square.
     */
    private int goldDistance;

    /**
     * Creates a Beacon at [row,col], and distance m from Beacon to Golden Square
     * @param row row of Beacon
     * @param col column of Beacon
     */
    public Beacon(int row, int col, int goldDistance) {
        super(row, col);
        this.goldDistance = goldDistance;
        System.out.println("[Beacon Class] Beacon Distance from Goal: " + goldDistance);
    }

    /**
     * Returns the distance from this beacon to Golden Square.
     * @return distance from this Beacon to Golden Square
     */
    public int getDistance(){
        return goldDistance;
    }
}
