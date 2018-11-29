package battleship.game.battleship.model;

import java.util.List;

public class Player {
    private int playerId;
    private Board board;
    private List<Ship> ships;
    private boolean isYourTurn;
    private List<BoardPoint> mishitsPlayer;
    private List<BoardPoint> hittedShipsPlayer;

    public Player(int playerId, Board board, List<Ship> ships, boolean isYourTurn, List<BoardPoint> mishitsPlayer, List<BoardPoint> hittedShipsPlayer) {
        this.playerId = playerId;
        this.board = board;
        this.ships = ships;
        this.isYourTurn = isYourTurn;
        this.mishitsPlayer = mishitsPlayer;
        this.hittedShipsPlayer = hittedShipsPlayer;
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

    public List<BoardPoint> getMishitsPlayer() {
        return mishitsPlayer;
    }

    public List<BoardPoint> getHittedShipsPlayer() {
        return hittedShipsPlayer;
    }

}
