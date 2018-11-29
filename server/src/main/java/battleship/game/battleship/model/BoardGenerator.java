package battleship.game.battleship.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardGenerator {
    public static Board generateBoard(int rows, int columns) {
        List<List<BoardPoint>> board = IntStream.range(0, columns).mapToObj(column -> {
            return IntStream.range(0, rows).mapToObj(row -> new BoardPoint(column, row)).collect(Collectors.toList());
        }).collect(Collectors.toList());
        return new Board(board);
    }

    //TODO algorytm statków do zrobienia jak bedzie cala logika gry
//    public static BoardPoint checkEndShipPoint(Ship ship, Board board) {
//        int columnId = ship.getStart().getColumnId();
//        int rowId = ship.getStart().getRowId();
//        int size = ship.getSize() - 1;
//
//        //TODO ta lista musi się też dynamicznie zmieniać!!
//        List<BoardPoint> endShipPoints = new ArrayList<>();
//        endShipPoints.add(new BoardPoint(rowId, columnId + size));
//        endShipPoints.add(new BoardPoint(rowId, columnId - size));
//        endShipPoints.add(new BoardPoint(rowId  + size, columnId));
//        endShipPoints.add(new BoardPoint(rowId  - size, columnId));
//
//        //TODO Nowa plansza zmniejszona za każdym razem
//        List<BoardPoint> flatBoard = flatBoard(board);
//
//        return flatBoard.stream().filter(endShipPoints::contains).collect(Collectors.toList()).get(0);
//    }
//
//    private static List<BoardPoint> flatBoard(Board board) {
//        return board.getBoard().stream()
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
//    }
//
//    public static List<BoardPoint> generateShipPoints(Ship ship, BoardPoint shipEndPoint) {
//        List<BoardPoint> shipPoints = new ArrayList<>();
//        BoardPoint startPoint = ship.getStart();
//        int startRowId = startPoint.getRowId();
//        int startColumnId = startPoint.getColumnId();
//        shipPoints.add(startPoint);
//        int endRowId = shipEndPoint.getRowId();
//        int endColumnId = shipEndPoint.getColumnId();
//
//        for(int i = 1; i < ship.getSize(); i ++) {
//            if(startRowId == endRowId) {
//                shipPoints.add(new BoardPoint(startRowId, startColumnId + i));
//            } else {
//                shipPoints.add(new BoardPoint(startRowId + i, startColumnId));
//            }
//        }
////        shipPoints.add(shipEndPoint);
//
//        return shipPoints;
//    }
//
//    private List<BoardPoint> generateSurroundedPoints(List<BoardPoint> shipPoints, Board board) {
//        List<BoardPoint> shipSurroundedAreaPoints = new ArrayList<>();
//        List<BoardPoint> flatBoard = flatBoard(board);
//
//            shipPoints.stream().map(shipPoint -> {
//                IntStream.range(1, 4).mapToObj(i -> {
//
//                })
//            })
//
//    }
}
