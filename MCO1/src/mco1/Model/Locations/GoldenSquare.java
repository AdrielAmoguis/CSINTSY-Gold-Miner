package mco1.Model.Locations;

public class GoldenSquare extends Location implements GuidedLocation{

    /**
     * Creates a Golden Square at [row, col]
     * @param row row of Golden Square
     * @param col column of Golden Square
     */
    public GoldenSquare(int row, int col) {
        super(row, col);
    }

    /**
     * Number of beacons in map.
     */
    private static int counter = 0;

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
