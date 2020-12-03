package mco1.Model;

public class TestModel {
    public static void main(String[] args){
        int boardSize = 6;
        Board board = new Board(boardSize);
        System.out.println(board.getBoardSize());
        board.placeGold(3,3);
        board.placePit(1,6);
        board.placeBeacon(3, 6);
        board.placePit(2,7);
        board.displayMap();

    }
}
