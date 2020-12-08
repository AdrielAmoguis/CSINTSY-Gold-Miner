package mco1.Model;
import mco1.Model.*;
import mco1.Model.Locations.*;
import java.util.*;

public class TestModel {
    public static void main(String[] args){
        int size = 32;
        Board mainBoard = new Board(32);
        mainBoard.placeGold(9,11);
        mainBoard.placePit(8, 7);mainBoard.placePit(8,8);
        mainBoard.placePit(8,14); mainBoard.placePit(8,15);
        mainBoard.placePit(9, 7); mainBoard.placePit(9, 15);
        mainBoard.placePit(10,7); mainBoard.placePit(10, 8);
        mainBoard.placePit(10, 14);mainBoard.placePit(10,15);
        mainBoard.placePit(4,8); mainBoard.placePit(4,9);
        mainBoard.placePit(4,10); mainBoard.placePit(4,11);
        mainBoard.placePit(4,12); mainBoard.placePit(4,13);mainBoard.placePit(4,14);


        mainBoard.placeBeacon(9,8); mainBoard.placeBeacon(9,14);

        Stack stack = new Stack();
        // Set upper left square in grid as root node
        Node root = new Node(mainBoard.getSquare(1,1));
        stack.push(root);
        // While Miner ongoing (not reached Gold/Pit) && Miner ran out of moves
        while (mainBoard.getStatus() == 0 && !stack.empty()){
            try{
                Thread.sleep(50);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            // the current parent node / location
            Node currentNode = (Node) stack.pop();
            System.out.println(currentNode.getLocation().getRow() + " | " + currentNode.getLocation().getCol());
            currentNode.getLocation().visit(); // set location as visited
            // scan 4 directions
            for (int counter = 1; counter <= 4; counter++){
                // farScan and check if a Beacon/GoldenSquare is in line of sight
                Location location = mainBoard.farScan();
                // if Beacon/GoldenSquare exists
                if (location instanceof GuidedLocation){
                    // Pop all and push Beacon/GoldenSquare
                    while(!stack.empty())
                        stack.pop();
                    // Push and break out of loop
                    stack.push(new Node(location, currentNode));
                    break;
                }
                // scan directly in front
                else{
                    location = mainBoard.nearScan();
                    // Push to stack if not a Pit and not visited
                    if (location instanceof Empty && !location.isVisited())
                        stack.push(new Node(location, currentNode));
                }
                // rotate to next direction
                mainBoard.rotateMiner();
            }
            // ROTATE and MOVE to desired NEXT LOCATION from stack (if any)
            if(!stack.empty()){
                // next Location details
                Location nextLocation = ((Node)stack.peek()).getLocation();
                int nextRow = nextLocation.getRow();
                int nextCol = nextLocation.getCol();

                // If a Beacon or GoldenSquare has been scanned
                if (nextLocation instanceof GuidedLocation){
                    // Continue moving GoldenSquare has been reached (no need to rotate)
                    // If Beacon was scanned, move to Beacon first
                    if (nextLocation instanceof Beacon){
                        int minerRow = mainBoard.getMinerAgent().getRow();
                        int minerCol = mainBoard.getMinerAgent().getCol();
                        while (!(mainBoard.getSquare(mainBoard.getMinerAgent().getRow()+1, mainBoard.getMinerAgent().getCol()+1) instanceof Beacon)){
                            mainBoard.moveMiner();
                            mainBoard.getMinerAgent().displayPosition();
                        }
                        // Scan for GoldenSquare
                        for (int counter = 1; counter <= 4; counter++) {
                            Location location = mainBoard.farScan();
                            if (location instanceof GoldenSquare)
                                break;
                            mainBoard.rotateMiner();
                        }
                    }
                }

                // Continue exploring to next Location
                else{
                    // Miner BACKTRACKS until adjacent if next Location is not adjacent to current Location
                    // (might need to check for out of bounds)
                    while(!mainBoard.isAdjacentTo(nextRow, nextCol)){
                        // Miner should rotate and move to backtrackLocation
                        Location backtrackLocation = currentNode.getParent().getLocation();
                        int expectedAngle = mainBoard.computeAngle(backtrackLocation.getRow(), backtrackLocation.getCol());
                        // rotate to desired angle
                        while(mainBoard.getMinerAgent().getFront() != expectedAngle)
                            mainBoard.rotateMiner();
                        // move
                        mainBoard.moveMiner();
                        mainBoard.getMinerAgent().displayPosition();
                        // backtracked to parent Node
                        currentNode = currentNode.getParent();
                    }
                    // after backtracking (if needed) rotate and move to next Location in stack
                    int expectedAngle = mainBoard.computeAngle(nextRow, nextCol);
                    while (mainBoard.getMinerAgent().getFront() != expectedAngle)
                        mainBoard.rotateMiner();
                    mainBoard.moveMiner();
                    mainBoard.getMinerAgent().displayPosition();
                }
            }
            else
                mainBoard.setNoSolution();
        }

    }

}
/*



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

    }
    */
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