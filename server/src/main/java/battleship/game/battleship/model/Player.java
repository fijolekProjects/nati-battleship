package battleship.game.battleship.model;

import java.util.List;

public class Player {
    private int playerId;
    private Board board;
    private List<Ship> ships;
    private boolean isYourTurn;
    private List<BoardPoint> mishitPoints;
    private List<BoardPoint> hittedShipPoints;
    private List<Ship> hittedShips;

    public Player(int playerId, Board board, List<Ship> ships, boolean isYourTurn, List<BoardPoint> mishitPoints, List<BoardPoint> hittedShipPoints, List<Ship> hittedShips) {
        this.playerId = playerId;
        this.board = board;
        this.ships = ships;
        this.isYourTurn = isYourTurn;
        this.mishitPoints = mishitPoints;
        this.hittedShipPoints = hittedShipPoints;
        this.hittedShips = hittedShips;
    }

    public boolean isGameOver() {
        return this.hittedShips.size() == this.ships.size();
    }

    public int getPlayerId() {
        return playerId;
    }

    public Board getBoard() {
        return board;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public List<BoardPoint> getMishitPoints() {
        return mishitPoints;
    }

    public List<BoardPoint> getHittedShipPoints() {
        return hittedShipPoints;
    }

    public void setHittedShips(List<Ship> hittedShips) {
        this.hittedShips = hittedShips;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Ship> getHittedShips() {
        return hittedShips;
    }

    public void setMishitPoints(List<BoardPoint> mishitPoints) {
        this.mishitPoints = mishitPoints;
    }
}
