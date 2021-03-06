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
    private GameLogic gameLogic;

    public ComputerLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public BoardPoint hitBoardPoint(int playerId) {
        Player player = gameLogic.getPlayer(playerId);
        List<BoardPoint> hittedShipPoints = player.getHittedShipPoints();
        List<BoardPoint> flatBoard = ListOperation.flatLists(player.getBoard().getBoard());
        List<BoardPoint> hittedShipsFlat = ListOperation.flatLists(player.getHittedShips());
        List<BoardPoint> hittedPoints = hittedPointButNotSunk(hittedShipPoints, hittedShipsFlat);
        List<BoardPoint> filteredBoard = RandomPointsGenerator.pointsAlreadyHitted(flatBoard, hittedShipPoints, player.getMishitPoints());
        if (!(hittedPoints.isEmpty() || filteredBoard.isEmpty())) {
            List<List<BoardPoint>> boardPoints = hittedPoints.stream().map(point -> SurroundedPointsLogic.getCrossSurroundedPoints(point.getRowId(), point.getColumnId())).collect(Collectors.toList());
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
