package battleship.game.battleship.utils;

import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardPoint;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardGenerator {
    public static Board generateBoard(int rows, int columns) {
        List<List<BoardPoint>> board = IntStream.range(0, columns).mapToObj(column -> {
            return IntStream.range(0, rows).mapToObj(row -> new BoardPoint(column, row)).collect(Collectors.toList());
        }).collect(Collectors.toList());
        return new Board(board);
    }
}
