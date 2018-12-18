package battleship.game.battleship.utils;

import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Ship;

import java.util.List;
import java.util.stream.Collectors;

public class BoardsOperations {

    public static List<Ship> whetherTheListOfListsContainsBoardPoint(List<Ship> listOfLists, BoardPoint boardPoint) {
        return listOfLists.stream().filter(list -> BoardsOperations.whetherTheListContainsBoardPoint(list.getShip(), boardPoint)).collect(Collectors.toList());
    }

    public static List<BoardPoint> whetherPointsBelongToBoard(List<BoardPoint> boardPoints, List<BoardPoint> points) {
        return points.stream().filter(point -> whetherTheListContainsBoardPoint(boardPoints, point)).collect(Collectors.toList());
    }

    public static boolean whetherTheListContainsBoardPoint(List<BoardPoint> list, BoardPoint boardPoint) {
        return list.contains(boardPoint);
    }

    public static List<Ship> whetherListOfListsContainsAllPoints(List<Ship> listOfLists, List<BoardPoint> boardPoints) {
        return listOfLists.stream().filter(ship -> boardPoints.containsAll(ship.getShip())).collect(Collectors.toList());
    }
}
