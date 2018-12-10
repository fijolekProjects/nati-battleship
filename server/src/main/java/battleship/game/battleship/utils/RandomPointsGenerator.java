package battleship.game.battleship.utils;

import battleship.game.battleship.model.BoardPoint;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomPointsGenerator {
    public static BoardPoint randomPoint(List<BoardPoint> filteredBoard) {
        int randomBoardPoint = ThreadLocalRandom.current().nextInt(0, filteredBoard.size());
        return filteredBoard.get(randomBoardPoint);
    }

    public static List<BoardPoint> pointsAlreadyHitted(List<BoardPoint> board, List<BoardPoint> hittedShipPoints, List<BoardPoint> mishitPoints) {
        return board.stream().filter(boardPoint ->
                !(hittedShipPoints.contains(boardPoint) || mishitPoints.contains(boardPoint)))
                .collect(Collectors.toList());
    }
}
