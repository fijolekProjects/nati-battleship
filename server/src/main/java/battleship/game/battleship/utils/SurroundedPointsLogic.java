package battleship.game.battleship.utils;

import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardPoint;

import java.util.ArrayList;
import java.util.List;

public class SurroundedPointsLogic {

    public static List<BoardPoint> getSurroundedPointsIfHittedShip(Board board, List<BoardPoint> ship) {
        List<BoardPoint> surroundedPoints = new ArrayList<>();
        BoardPoint boardPointFirst = ship.get(0);
        BoardPoint boardPointLast = ship.get(ship.size() - 1);
        int rowIdFirst = boardPointFirst.getRowId();
        int rowIdLast = boardPointLast.getRowId();
        int columnIdFirst = boardPointFirst.getColumnId();
        int columnIdLast = boardPointLast.getColumnId();
        if (ship.size() == 1) {
            surroundedPoints.addAll(getCrossSurroundedPoints(boardPointFirst));
        } else if (rowIdFirst == rowIdLast) {
            surroundedPoints.add(new BoardPoint(rowIdFirst, columnIdFirst - 1));
            surroundedPoints.add(new BoardPoint(rowIdFirst, columnIdLast + 1));
        } else {
            surroundedPoints.add(new BoardPoint(rowIdFirst - 1, columnIdFirst));
            surroundedPoints.add(new BoardPoint(rowIdLast + 1, columnIdFirst));
        }
        return board.whetherPointsBelongToBoard(surroundedPoints);
    }

    public static List<BoardPoint> getCrossSurroundedPoints(BoardPoint boardPoint) {
        int rowId = boardPoint.getRowId();
        int columnId = boardPoint.getColumnId();
        List<BoardPoint> surroundedPoints = new ArrayList<>();
        surroundedPoints.add(new BoardPoint(rowId, columnId - 1));
        surroundedPoints.add(new BoardPoint(rowId, columnId + 1));
        surroundedPoints.add(new BoardPoint(rowId - 1, columnId));
        surroundedPoints.add(new BoardPoint(rowId + 1, columnId));
        return surroundedPoints;
    }

    public static List<BoardPoint> getDiagonallySurroundedPoints(Board board, BoardPoint boardPoint) {
        int rowId = boardPoint.getRowId();
        int columnId = boardPoint.getColumnId();
        List<BoardPoint> surroundedPoints = new ArrayList<>();
        surroundedPoints.add(new BoardPoint(rowId - 1, columnId - 1));
        surroundedPoints.add(new BoardPoint(rowId + 1, columnId - 1));
        surroundedPoints.add(new BoardPoint(rowId - 1, columnId + 1));
        surroundedPoints.add(new BoardPoint(rowId + 1, columnId + 1));
        return board.whetherPointsBelongToBoard(surroundedPoints);
    }

}
