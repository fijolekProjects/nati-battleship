package battleship.game.battleship.utils;

import battleship.game.battleship.model.BoardPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShipsGenerator {
    private List<List<BoardPoint>> ships = new ArrayList<List<BoardPoint>>();
    private List<BoardPoint> surroundedPoints = new ArrayList<>();
    private List<BoardPoint> board = ListOperation.flatLists(BoardGenerator.generateBoard(10, 10).getBoard());

    public ShipsGenerator() {
        this.generateRandomShip();
    }

    private void generateRandomShip() {
        generateRandomShip(4, getRandomBoolean());
        generateRandomShip(3, getRandomBoolean());
        generateRandomShip(3, getRandomBoolean());
        generateRandomShip(2, getRandomBoolean());
        generateRandomShip(2, getRandomBoolean());
        generateRandomShip(2, getRandomBoolean());
        generateRandomShip(1, getRandomBoolean());
        generateRandomShip(1, getRandomBoolean());
        generateRandomShip(1, getRandomBoolean());
        generateRandomShip(1, getRandomBoolean());
    }

    private void generateRandomShip(int shipSize, boolean isVertical) {
        //LOGIC
        List<BoardPoint> ship;
        do {
            BoardPoint randomPoint = getBoardPoint();
            int rowId = randomPoint.getRowId();
            int columnId = randomPoint.getColumnId();
            ship = generateShip(rowId, columnId, shipSize, isVertical);
        } while (!properGeneratedShip(ship, surroundedPoints));
        ships.add(BoardsOperations.whetherPointsBelongToBoard(board, ship));

        List<BoardPoint> surroundedPointsCross = ListOperation.flatLists(ship.stream().map(boardPoint ->
                SurroundedPointsLogic.getDiagonallySurroundedPoints(boardPoint.getRowId(), boardPoint.getColumnId())).collect(Collectors.toList()));
        List<BoardPoint> diagonallySurroundedPoints = SurroundedPointsLogic.getSurroundedPointsIfHittedShip(board, ship);

        surroundedPoints.addAll(BoardsOperations.whetherPointsBelongToBoard(board,surroundedPointsCross));
        surroundedPoints.addAll(diagonallySurroundedPoints);
    }

    private boolean properGeneratedShip(List<BoardPoint> ship, List<BoardPoint> points) {
        return ship.stream().allMatch(boardPoint -> BoardsOperations.whetherTheListContainsBoardPoint(board, boardPoint) && !points.contains(boardPoint));
    }

    private BoardPoint getBoardPoint() {
        if (ships.isEmpty()) {
            return RandomPointsGenerator.randomPoint(board);
        } else {
            List<BoardPoint> flatShips = ListOperation.flatLists(ships);
            List<BoardPoint> filteredBoardPoints = RandomPointsGenerator.pointsAlreadyHitted(board, flatShips, surroundedPoints);
            return RandomPointsGenerator.randomPoint(filteredBoardPoints);
        }
    }

    private static List<BoardPoint> generateShip(int row, int column, int size, boolean isVerticalShip) {
        return IntStream.range(0, size).mapToObj(i -> {
            if (isVerticalShip) {
                return new BoardPoint(row + i, column);
            } else {
                return new BoardPoint(row, column + i);
            }
        }).collect(Collectors.toList());
    }

    public List<List<BoardPoint>> getShips() {
        return ships;
    }

    private boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
