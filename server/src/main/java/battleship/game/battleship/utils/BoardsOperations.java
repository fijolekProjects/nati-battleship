package battleship.game.battleship.utils;

import battleship.game.battleship.model.BoardPoint;

import java.util.List;
import java.util.stream.Collectors;

public class BoardsOperations {

    public static List<List<BoardPoint>> whetherTheListOfListsContainsBoardPoint(List<List<BoardPoint>> listOfLists, BoardPoint boardPoint) {
        return listOfLists.stream().filter(list -> BoardsOperations.whetherTheListContainsBoardPoint(list, boardPoint)).collect(Collectors.toList());
    }

    public static List<BoardPoint> whetherPointsBelongToBoard(List<BoardPoint> boardPoints, List<BoardPoint> points) {
        return points.stream().filter(point -> whetherTheListContainsBoardPoint(boardPoints, point)).collect(Collectors.toList());
    }

    public static boolean whetherTheListContainsBoardPoint(List<BoardPoint> list, BoardPoint boardPoint) {
        return list.contains(boardPoint);
    }

    public static List<List<BoardPoint>> whetherListOfListsContainsAllPoints(List<List<BoardPoint>> listOfLists, List<BoardPoint> boardPoints) {
        return listOfLists.stream().filter(boardPoints::containsAll).collect(Collectors.toList());
    }
}
