package mco1.Model;
import mco1.Model.Locations.*;
import java.util.*;

public class TestModel {
    public static int computeAngle(Miner miner, int nextRow, int nextCol){
        int expectedAngle;
        // If same row, check if right or left for rotation
        if (nextRow == miner.getRow()){
            // If backtrack location at right side
            if(nextCol > miner.getCol())
                expectedAngle = Miner.RIGHT;
            else
                expectedAngle = Miner.LEFT;
        }
        // If same column, check if up or down for rotation
        else{
            // If backtrack location is above
            if (nextRow < miner.getRow())
                expectedAngle = Miner.UP;
            else
                expectedAngle = Miner.DOWN;
        }
        return expectedAngle;
    }

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
        Node root = new Node(board.getSquare(1,1));
        stack.push(root);
        // While Miner ongoing (not reached Gold/Pit) && Miner ran out of moves
        while (board.getStatus() == 0 && !stack.empty()){
            // the current parent node / location
            board.getMinerAgent().displayPosition();
            Node currentNode = (Node) stack.pop();
            currentNode.getLocation().visit(); // set location as visited
            // scan 4 directions
            for (int counter = 1; counter <= 4; counter++){
                // farScan and check if a Beacon/GoldenSquare is in line of sight
                Location location = board.farScan();
                // if Beacon/GoldenSquare exists
                if (location instanceof Beacon || location instanceof GoldenSquare){
                    // Pop all and push Beacon/GoldenSquare
                    while(!stack.empty())
                        stack.pop();
                    // Push and break out of loop
                    stack.push(new Node(location, currentNode));
                    break;
                }
                // scan directly in front
                else{
                    location = board.nearScan();
                    // Push to stack if not a Pit and not visited
                    if (location instanceof Empty && !location.isVisited())
                        stack.push(new Node(location, currentNode));
                }
                // rotate to next direction
                board.rotateMiner();
            }
            // ROTATE and MOVE to desired NEXT LOCATION from stack (if any)
            if(!stack.empty()){
                // next Location details
                Location nextLocation = ((Node)stack.peek()).getLocation();
                int nextRow = nextLocation.getRow();
                int nextCol = nextLocation.getCol();
                /*
                // If a Beacon or GoldenSquare has been scanned
                if (nextLocation instanceof GuidedLocation){
                    // Continue moving GoldenSquare has been reached (no need to rotate)
                    // If Beacon was scanned, move to Beacon first
                    if (nextLocation instanceof Beacon){
                        int minerRow = board.getMinerAgent().getRow();
                        int minerCol = board.getMinerAgent().getCol();
                        while (!(board.getSquare(board.getMinerAgent().getRow(), board.getMinerAgent().getCol()) instanceof Beacon))
                            board.moveMiner();
                        // Scan for GoldenSquare
                        for (int counter = 1; counter <= 4; counter++) {
                            Location location = board.farScan();
                            if (location instanceof GoldenSquare)
                                break;
                            board.rotateMiner();
                        }
                    }
                    while (!(board.getSquare(board.getMinerAgent().getRow(), board.getMinerAgent().getCol()) instanceof GoldenSquare))
                        board.moveMiner();
                }

                 */
                // Continue exploring to next Location
                //else{
                    // Miner BACKTRACKS until adjacent if next Location is not adjacent to current Location
                    // (might need to check for out of bounds)
                    while(board.getMinerAgent().getRow() != nextRow && board.getMinerAgent().getCol() != nextCol){
                        // Miner should rotate and move to backtrackLocation
                        Location backtrackLocation = currentNode.getParent().getLocation();
                        int expectedAngle = computeAngle(board.getMinerAgent(), backtrackLocation.getRow(), backtrackLocation.getCol());
                        // rotate to desired angle
                        while(board.getMinerAgent().getFront() != expectedAngle)
                            board.rotateMiner();
                        // move
                        board.moveMiner();
                    }
                    // after backtracking (if needed) rotate and move to next Location in stack
                    int expectedAngle = computeAngle(board.getMinerAgent(), nextRow, nextCol);
                    while (board.getMinerAgent().getFront() != expectedAngle)
                        board.rotateMiner();
                    board.moveMiner();
               // }
            }
            else
                board.setNoSolution();
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