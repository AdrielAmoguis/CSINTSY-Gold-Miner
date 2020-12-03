package mco1.Model;
import java.util.*;

public class Board {
    /**
     * 2D ArrayList Representation of board
     */
    private ArrayList<ArrayList<Location>> map;

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
    }
}
