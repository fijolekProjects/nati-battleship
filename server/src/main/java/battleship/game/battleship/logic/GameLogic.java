package battleship.game.battleship.logic;

import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Game;
import battleship.game.battleship.model.Player;
import battleship.game.battleship.utils.*;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private Game game;

    public GameLogic() {
        List<BoardPoint> mishitsPlayer1 = new ArrayList<>();
        List<BoardPoint> mishitsPlayer2 = new ArrayList<>();
        List<BoardPoint> hittedShipPointsPlayer1 = new ArrayList<>();
        List<BoardPoint> hittedShipPointsPlayer2 = new ArrayList<>();
        List<List<BoardPoint>> hittedShipsPlayer1 = new ArrayList<List<BoardPoint>>();
        List<List<BoardPoint>> hittedShipsPlayer2 = new ArrayList<List<BoardPoint>>();
        Board board = BoardGenerator.generateBoard(10, 10);
        List<List<BoardPoint>> generateShipsForPlayer1 = new ShipsGenerator().getShips();
        List<List<BoardPoint>> generateShipsForPlayer2 = new ShipsGenerator().getShips();
        Player playerOne = new Player(1, board, generateShipsForPlayer1, true, mishitsPlayer1, hittedShipPointsPlayer1, hittedShipsPlayer1);
        Player playerTwo = new Player(2, board, generateShipsForPlayer2, false, mishitsPlayer2, hittedShipPointsPlayer2, hittedShipsPlayer2);
        game = new Game(playerOne, playerTwo, true);
    }

    public Game playGame(Player player, Player enemy, BoardPoint boardPoint) {
        List<List<BoardPoint>> ships = player.getShips();
        List<BoardPoint> flattenShips = ListOperation.flatLists(ships);
        List<BoardPoint> mishitsPlayer = player.getMishitPoints();
        ArrayList<BoardPoint> mishitsPlayerCopy = new ArrayList<>(mishitsPlayer);
        List<BoardPoint> flatBoard = ListOperation.flatLists(player.getBoard().getBoard());
        if (BoardsOperations.whetherTheListContainsBoardPoint(flattenShips, boardPoint)) {
            List<BoardPoint> hittedShipPoints = player.getHittedShipPoints();
            hittedShipPoints.add(boardPoint);
            List<List<BoardPoint>> hittedShips = BoardsOperations.whetherListOfListsContainsAllPoints(ships, hittedShipPoints);
            player.setHittedShips(hittedShips);
            player.setYourTurn(true);
            enemy.setYourTurn(false);
            mishitsPlayerCopy.addAll(SurroundedPointsLogic.diagonalPointsThatBelongsToBoard(flatBoard, boardPoint));
            player.setMishitPoints(mishitsPlayerCopy);
            List<List<BoardPoint>> hittedShip = BoardsOperations.whetherTheListOfListsContainsBoardPoint(hittedShips, boardPoint);
            if (hittedShip.size() != 0) {
                mishitsPlayerCopy.addAll(SurroundedPointsLogic.getSurroundedPointsIfHittedShip(flatBoard, hittedShip.get(0)));
                player.setMishitPoints(mishitsPlayerCopy);
            }
            if (player.isGameOver()) {
                game.setGameRunning(false);
            }
            return game;
        } else {
            mishitsPlayer.add(boardPoint);
            player.setYourTurn(false);
            enemy.setYourTurn(true);
            return game;
        }
    }

    public Game getGame() {
        return game;
    }
}
