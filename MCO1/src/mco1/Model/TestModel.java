package mco1.Model;
import mco1.Model.Locations.*;
import java.util.*;

public class TestModel {
    public static void main(String[] args){
        int nSize = 8;
        Board board = new Board(8);
        // place Gold
        board.placeGold(4,5);
        // place Pit
        board.placePit(2,1);
        board.placePit(1,5);
        board.placePit(1,8);
        // place Beacon
        board.placeBeacon(2,5);
        board.placeBeacon(4,4);
        board.placeBeacon(4,6);

        // ============== SIMULATE ===============
        Stack stack = new Stack();
        // Set upper left square in grid as root node
        Node root = new Node(board.getSquare(0,0));
        root.visit();
        stack.push(root);
        // While Miner has not reached GoldenSquare or Pit && Miner ran out of moves
        while (board.getStatus() == 0 && !stack.empty()){
            // the current parent node / location
            Node currentNode = (Node) stack.pop();
            // scan 4 directions
            for (int counter = 1; counter <= 4; counter++){
                // farScan and check if a Beacon/GoldenSquare is in line of sight
                Location location = board.farScan();
                // if Beacon/GoldenSquare exists
                if (location instanceof Beacon || location instanceof GoldenSquare){
                    // Pop all and push Beacon/GoldenSquare
                    while(!stack.empty())
                        stack.pop();
                    // Push and break out of loop (scanning 4 directions)
                    stack.push(new Node(location, currentNode));
                    break;
                }
                // scan directly in front
                else{
                    location = board.nearScan();
                    if (location instanceof Empty)
                        stack.push(new Node(location, currentNode));
                }
                // rotate to next direction
                board.rotateMiner();
            }
            // rotate and move to desired next Location from stack (if any)
            if(!stack.empty()){
                Location currentLocation = currentNode.getLocation();
                Location nextLocation = ((Node)stack.peek()).getLocation();
                // if next Location is not adjacent to current Location, Miner backtracks until adjacent
                // (might need to check for out of bounds)
                while(currentLocation.getRow() != nextLocation.getRow() && currentLocation.getCol() != nextLocation.getCol()){
                    Location backtrackLocation = currentNode.getParent().getLocation();
                    // Miner rotates and moves to backtrackLocation
                    
                }

            }

        }

    }
}



// Check if scan is functional -> Good
        /*
        int boardSize = 6;
        Board board = new Board(boardSize);
        System.out.println(board.getMapSize());
        board.moveMiner();
        board.moveMiner();  // now at [1,3]
        board.placePit(1,2);
        board.placeGold(3,6);
        board.placeBeacon(3,3);
        Pit.getCounter();
        board.displayMap(); // first map display
        board.getMinerAgent().rotate();
        board.farScan(); // Downward scan
        board.getMinerAgent().rotate();
        board.farScan(); // Left scan
        Pit.getCounter();
        board.placePit(1,3);
        board.placePit(3,4);
        board.displayMap();
        Pit.getCounter();
        board.resetSquare(1,2);
        board.displayMap();
        Pit.getCounter();
*/

        /*
        // Check if placement of Locations is functional -> Good.
        System.out.println("Check if placement of Locations is functional");
        board.placeGold(3,3);
        board.placePit(1,6);
        board.placeBeacon(3, 6); // Need more conditions in checking if beacon placement is valid.
        board.displayMap();
        // Check if scan method is functional
        // Check if resetting of Locations is functional -> Good.
        System.out.println("Check if resetting of Locations is functional");
        board.resetSquare(1,6);
        board.displayMap();
        // Check if Miner movement is functional -> Good.
        System.out.println("Check if Miner movement is functional");
        board.moveMiner();
        board.getMinerAgent().displayPosition();
        board.getMinerAgent().rotate();
        board.moveMiner();
        board.getMinerAgent().displayPosition();
        board.getMinerAgent().rotate();
        board.moveMiner();
        board.getMinerAgent().displayPosition();
        board.moveMiner();  // Invalid move check -> Good.
        // Check if resetting of Miner's position is functional -> Good
        System.out.println("Check if resetting of Miner's position is functional");
        board.resetMiner();
        board.getMinerAgent().displayPosition();
        */