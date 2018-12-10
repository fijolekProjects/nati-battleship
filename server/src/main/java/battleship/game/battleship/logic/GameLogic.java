package battleship.game.battleship.logic;

import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Player;
import battleship.game.battleship.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameLogic {

    private List<BoardPoint> mishitsPlayer1 = new ArrayList<>();
    private List<BoardPoint> mishitsPlayer2 = new ArrayList<>();
    private List<BoardPoint> hittedShipPointsPlayer1 = new ArrayList<>();
    private List<BoardPoint> hittedShipPointsPlayer2 = new ArrayList<>();
    private List<List<BoardPoint>> hittedShipsPlayer1 = new ArrayList<List<BoardPoint>>();
    private List<List<BoardPoint>> hittedShipsPlayer2 = new ArrayList<List<BoardPoint>>();
    private Board board = BoardGenerator.generateBoard(10, 10);
    private List<List<BoardPoint>> generateShipsForPlayer1 = new ShipsGenerator().getShips();
    private List<List<BoardPoint>> generateShipsForPlayer2 = new ShipsGenerator().getShips();
    private Player playerOne = new Player(1, board, generateShipsForPlayer1, true, mishitsPlayer1, hittedShipPointsPlayer1, hittedShipsPlayer1);
    private Player playerTwo = new Player(2, board, generateShipsForPlayer2, false, mishitsPlayer2, hittedShipPointsPlayer2, hittedShipsPlayer2);
    private List<Player> players = new ArrayList<Player>() {{
        add(playerOne);
        add(playerTwo);
    }};

    public List<Player> playGame(int playerId, BoardPoint boardPoint) {
        Player player = getPlayer(playerId);
        Player enemy = getEnemy(playerId);
        List<List<BoardPoint>> ships = player.getShips();
        List<BoardPoint> flattenShips = ListOperation.flatLists(ships);
        List<BoardPoint> mishitsPlayer = player.getMishitPoints();
        List<BoardPoint> flatBoard = ListOperation.flatLists(player.getBoard().getBoard());
        if (BoardsOperations.whetherTheListContainsBoardPoint(flattenShips, boardPoint)) {
            List<BoardPoint> hittedShipPoints = player.getHittedShipPoints();
            hittedShipPoints.add(boardPoint);
            List<List<BoardPoint>> hittedShips = BoardsOperations.whetherListOfListsContainsAllPoints(ships, hittedShipPoints);
            player.setHittedShips(hittedShips);
            player.setYourTurn(true);
            enemy.setYourTurn(false);
            mishitsPlayer.addAll(SurroundedPointsLogic.diagonalPointsThatBelongsToBoard(flatBoard, boardPoint));
            List<List<BoardPoint>> hittedShip = BoardsOperations.whetherTheListOfListsContainsBoardPoint(hittedShips, boardPoint);
            if (hittedShip.size() != 0) {
                mishitsPlayer.addAll(SurroundedPointsLogic.getSurroundedPointsIfHittedShip(flatBoard, hittedShip.get(0)));
            }
            return players;
        } else {
            mishitsPlayer.add(boardPoint);
            player.setYourTurn(false);
            enemy.setYourTurn(true);
            return players;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int playerId) {
        return players.stream().filter(p -> p.getPlayerId() == playerId).collect(Collectors.toList()).get(0);
    }

    private Player getEnemy(int playerId) {
        return players.stream().filter(p -> p.getPlayerId() != playerId).collect(Collectors.toList()).get(0);
    }
}
