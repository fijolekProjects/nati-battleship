package battleship.game.battleship.model;

import java.util.List;
import java.util.Objects;

public class Board {
    private List<List<BoardPoint>> board;

    public Board(List<List<BoardPoint>> board) {
        this.board = board;
    }

    public List<List<BoardPoint>> getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return Objects.equals(board, board1.board);
    }

    @Override
    public int hashCode() {

        return Objects.hash(board);
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + board +
                '}';
    }
}
