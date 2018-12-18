package battleship.game.battleship.dto;

import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Player;
import battleship.game.battleship.model.Ship;

import java.util.List;

public class PlayerDTO {
    private int playerId;
    private Board board;
    private boolean isYourTurn;
    private List<BoardPoint> mishitPoints;
    private List<BoardPoint> hittedShipPoints;
    private List<Ship> hittedShips;

    public PlayerDTO(Player player) {
        this.playerId = player.getPlayerId();
        this.board = player.getBoard();
        this.isYourTurn = player.isYourTurn();
        this.mishitPoints = player.getMishitPoints();
        this.hittedShipPoints = player.getHittedShipPoints();
        this.hittedShips = player.getHittedShips();
    }

    public int getPlayerId() {
        return playerId;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public List<BoardPoint> getMishitPoints() {
        return mishitPoints;
    }

    public List<BoardPoint> getHittedShipPoints() {
        return hittedShipPoints;
    }

    public List<Ship> getHittedShips() {
        return hittedShips;
    }
}
