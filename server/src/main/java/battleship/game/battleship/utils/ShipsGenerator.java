package battleship.game.battleship.utils;

import battleship.game.battleship.model.Board;
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
    private Board board = new Board();

    public ShipsGenerator() {
        this.generateRandomShip();
    }

    private void generateRandomShip() {
        generateRandomShip(4);
        generateRandomShip(3);
        generateRandomShip(3);
        generateRandomShip(2);
        generateRandomShip(2);
        generateRandomShip(2);
        generateRandomShip(1);
        generateRandomShip(1);
        generateRandomShip(1);
        generateRandomShip(1);
    }

    private void generateRandomShip(int shipSize) {
        boolean isVertical = getRandomBoolean();
        Ship ship;
        do {
            BoardPoint randomPoint = getBoardPoint();
            int rowId = randomPoint.getRowId();
            int columnId = randomPoint.getColumnId();
            ship = generateShip(rowId, columnId, shipSize, isVertical);
        } while (!properGeneratedShip(ship.getShip(), surroundedPoints));
        List<BoardPoint> shipPoints = board.whetherPointsBelongToBoard(ship.getShip());
        ships.add(new Ship(shipPoints));

        List<BoardPoint> surroundedPointsCross = ListOperation.flatLists(ship.getShip().stream().map(boardPoint ->
                SurroundedPointsLogic.getDiagonallySurroundedPoints(board, boardPoint)).collect(Collectors.toList()));
        List<BoardPoint> diagonallySurroundedPoints = SurroundedPointsLogic.getSurroundedPointsIfHittedShip(board, ship.getShip());

        surroundedPoints.addAll(board.whetherPointsBelongToBoard(surroundedPointsCross));
        surroundedPoints.addAll(diagonallySurroundedPoints);
    }

    private boolean properGeneratedShip(List<BoardPoint> ship, List<BoardPoint> points) {
        return ship.stream().allMatch(boardPoint -> board.flatBoard().contains(boardPoint) && !points.contains(boardPoint));
    }

    private BoardPoint getBoardPoint() {
        if (ships.isEmpty()) {
            return RandomPointsGenerator.randomPoint(board.flatBoard());
        } else {
            List<BoardPoint> flatShips = ListOperation.flatShips(ships);
            List<BoardPoint> filteredBoardPoints = RandomPointsGenerator.pointsAlreadyHitted(board.flatBoard(), flatShips, surroundedPoints);
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
