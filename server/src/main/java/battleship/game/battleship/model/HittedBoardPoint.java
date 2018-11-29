package battleship.game.battleship.model;

import java.util.Objects;

public class HittedBoardPoint {
    private int playerId;
    private BoardPoint boardPoint;

    public HittedBoardPoint() {
    }

    public HittedBoardPoint(int playerId, BoardPoint boardPoint) {
        this.playerId = playerId;
        this.boardPoint = boardPoint;
    }

    public int getPlayerId() {
        return playerId;
    }

    public BoardPoint getBoardPoint() {
        return boardPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HittedBoardPoint that = (HittedBoardPoint) o;
        return playerId == that.playerId &&
                Objects.equals(boardPoint, that.boardPoint);
    }

    @Override
    public int hashCode() {

        return Objects.hash(playerId, boardPoint);
    }
}
