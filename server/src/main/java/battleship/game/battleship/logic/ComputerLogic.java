package battleship.game.battleship.logic;

import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Player;
import battleship.game.battleship.utils.ListOperation;
import battleship.game.battleship.utils.RandomPointsGenerator;
import battleship.game.battleship.utils.SurroundedPointsLogic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ComputerLogic {

    public ComputerLogic() {
    }

    public BoardPoint hitBoardPoint(Player player) {
        List<BoardPoint> hittedShipPoints = player.getHittedShipPoints();
        List<BoardPoint> flatBoard = player.getBoard().flatBoard();
        List<BoardPoint> hittedShipsFlat = player.flatShips(player.getHittedShips());
        List<BoardPoint> hittedPoints = hittedPointButNotSunk(hittedShipPoints, hittedShipsFlat);
        List<BoardPoint> filteredBoard = RandomPointsGenerator.pointsAlreadyHitted(flatBoard, hittedShipPoints, player.getMishitPoints());
        if (!(hittedPoints.isEmpty() || filteredBoard.isEmpty())) {
            List<List<BoardPoint>> boardPoints = hittedPoints.stream().map(SurroundedPointsLogic::getCrossSurroundedPoints).collect(Collectors.toList());
            Optional<BoardPoint> boardPoint = ListOperation.flatLists(boardPoints).stream().filter(filteredBoard::contains).findFirst();
            return boardPoint.orElseGet(() -> RandomPointsGenerator.randomPoint(filteredBoard));
        } else {
            return RandomPointsGenerator.randomPoint(filteredBoard);
        }
    }

    private List<BoardPoint> hittedPointButNotSunk(List<BoardPoint> hittedShipPoints, List<BoardPoint> hittedShipsFlat) {
        return hittedShipPoints.stream().filter(point -> !hittedShipsFlat.contains(point)).collect(Collectors.toList());
    }
}
