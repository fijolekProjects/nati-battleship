package battleship.game.battleship.utils;

import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Ship;

import java.util.List;
import java.util.stream.Collectors;

public class ListOperation {

    public static <T> List<T> flatLists(List<List<T>> lists) {
        return lists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<BoardPoint> flatShips(List<Ship> lists) {
        return lists.stream()
                .flatMap(ship -> ship.getShip().stream())
                .collect(Collectors.toList());
    }

}
