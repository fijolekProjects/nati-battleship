package battleship.game.battleship.logic;

import battleship.game.battleship.model.*;
import battleship.game.battleship.utils.ShipsGenerator;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private Game game;

    public GameLogic() {
        List<BoardPoint> mishitsPlayer1 = new ArrayList<>();
        List<BoardPoint> mishitsPlayer2 = new ArrayList<>();
        List<BoardPoint> hittedShipPointsPlayer1 = new ArrayList<>();
        List<BoardPoint> hittedShipPointsPlayer2 = new ArrayList<>();
        List<Ship> hittedShipsPlayer1 = new ArrayList<Ship>();
        List<Ship> hittedShipsPlayer2 = new ArrayList<Ship>();
        Board board = new Board();
        List<Ship> generateShipsForPlayer1 = new ShipsGenerator().getShips();
        List<Ship> generateShipsForPlayer2 = new ShipsGenerator().getShips();
        Player playerOne = new Player(1, board, generateShipsForPlayer1, true, mishitsPlayer1, hittedShipPointsPlayer1, hittedShipsPlayer1);
        Player playerTwo = new Player(2, board, generateShipsForPlayer2, false, mishitsPlayer2, hittedShipPointsPlayer2, hittedShipsPlayer2);
        game = new Game(playerOne, playerTwo, true);
    }

    //ta metode `playGame` przenioslbym do Game a nastepnie kalse GameLogic przenazwilbym na GameFactory i zrobil tak,
    //zeby dalo sie zrobic cos takiego - Game game = GameFactory.newGame()
    public Game playGame(Player player, Player enemy, BoardPoint boardPoint) {
        if (player.isGameOver()) {
            game.setGameRunning(false);
            return game;
        } else {
            player.updatePlayerState(boardPoint, enemy);
            return game;
        }
    }

    public Game getGame() {
        return game;
    }
}
