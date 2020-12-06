package mco1.Model;

import mco1.Model.Locations.Pit;

public class TestModel {
    public static void main(String[] args){
        // Check if scan is functional -> Good
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

    }
}
