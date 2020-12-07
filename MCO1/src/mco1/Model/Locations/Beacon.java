package mco1.Model.Locations;

public class Beacon extends Location implements GuidedLocation{
    /**
     * Distance from Beacon to Golden Square.
     */
    private int goldDistance;

    /**
     * Number of beacons in map.
     */
    private static int counter = 0;

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

    /**
     * Resets counter back to 0.
     */
    public static void resetCounter(){
        counter = 0;
    }
    /**
     * Decrements counter by 1. Used when resetting a square.
     */
    public static void decrement(){
        counter -= 1;
    }

    /**
     * Increments counter by 1. Used when placing a Location.
     */
    public static void increment(){
        counter += 1;
    }

    /**
     * Returns true if counter is more than 0.
     * @return true if counter is more than 0.
     */
    public static boolean isSet(){
        return counter > 0;
    }
}
