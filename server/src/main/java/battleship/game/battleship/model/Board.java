package battleship.game.battleship.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Nie rozumiem, dlaczego uzywamy tej klasy w shipGeneratorze - nie powinno tak byc
//Poza tym to generateBoard() przenioslbym tzn. Board board = BoardFactory.createBoard(10, 10);
public class Board {
    private List<List<BoardPoint>> board;

    public Board() {
        this.board = generateBoard();
    }

    public List<List<BoardPoint>> getBoard() {
        return board;
    }

    public List<BoardPoint> whetherPointsBelongToBoard(List<BoardPoint> points) {
        return points.stream().filter(point -> flatBoard().contains(point)).collect(Collectors.toList());
    }

    public List<BoardPoint> flatBoard() {
        return board.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<List<BoardPoint>> generateBoard() {
        return IntStream.range(0, 10).mapToObj(column -> {
            return IntStream.range(0, 10).mapToObj(row -> new BoardPoint(column, row)).collect(Collectors.toList());
        }).collect(Collectors.toList());
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
