package battleship.game.battleship.utils;

import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShipsGenerator {
    private List<Ship> ships = new ArrayList<Ship>();
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
        Ship ship;
        do {
            BoardPoint randomPoint = getBoardPoint();
            int rowId = randomPoint.getRowId();
            int columnId = randomPoint.getColumnId();
            ship = generateShip(rowId, columnId, shipSize, isVertical);
        } while (!properGeneratedShip(ship.getShip(), surroundedPoints));
        List<BoardPoint> shipPoints = BoardsOperations.whetherPointsBelongToBoard(board, ship.getShip());
        ships.add(new Ship(shipPoints));

        List<BoardPoint> surroundedPointsCross = ListOperation.flatLists(ship.getShip().stream().map(boardPoint ->
                SurroundedPointsLogic.getDiagonallySurroundedPoints(boardPoint.getRowId(), boardPoint.getColumnId())).collect(Collectors.toList()));
        List<BoardPoint> diagonallySurroundedPoints = SurroundedPointsLogic.getSurroundedPointsIfHittedShip(board, ship.getShip());

        surroundedPoints.addAll(BoardsOperations.whetherPointsBelongToBoard(board, surroundedPointsCross));
        surroundedPoints.addAll(diagonallySurroundedPoints);
    }

    private boolean properGeneratedShip(List<BoardPoint> ship, List<BoardPoint> points) {
        return ship.stream().allMatch(boardPoint -> BoardsOperations.whetherTheListContainsBoardPoint(board, boardPoint) && !points.contains(boardPoint));
    }

    private BoardPoint getBoardPoint() {
        if (ships.isEmpty()) {
            return RandomPointsGenerator.randomPoint(board);
        } else {
            List<BoardPoint> flatShips = ListOperation.flatShips(ships);
            List<BoardPoint> filteredBoardPoints = RandomPointsGenerator.pointsAlreadyHitted(board, flatShips, surroundedPoints);
            return RandomPointsGenerator.randomPoint(filteredBoardPoints);
        }
    }

    private static Ship generateShip(int row, int column, int size, boolean isVerticalShip) {
        return new Ship(IntStream.range(0, size).mapToObj(i -> {
            if (isVerticalShip) {
                return new BoardPoint(row + i, column);
            } else {
                return new BoardPoint(row, column + i);
            }
        }).collect(Collectors.toList()));
    }

    public List<Ship> getShips() {
        return ships;
    }

    private boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
