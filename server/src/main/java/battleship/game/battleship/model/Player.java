package battleship.game.battleship.model;

import java.util.List;

public class Player {
    private int playerId;
    private Board board;
    private List<List<BoardPoint>> ships;
    private boolean isYourTurn;
    private List<BoardPoint> mishitPoints;
    private List<BoardPoint> hittedShipPoints;
    private List<List<BoardPoint>> hittedShips;

    public Player(int playerId, Board board, List<List<BoardPoint>> ships, boolean isYourTurn, List<BoardPoint> mishitPoints, List<BoardPoint> hittedShipPoints, List<List<BoardPoint>> hittedShips) {
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

    public List<List<BoardPoint>> getShips() {
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

    public List<List<BoardPoint>> getHittedShips() {
        return hittedShips;
    }

    public void setHittedShips(List<List<BoardPoint>> hittedShips) {
        this.hittedShips = hittedShips;
    }

    public void setMishitPoints(List<BoardPoint> mishitPoints) {
        this.mishitPoints = mishitPoints;
    }
}
