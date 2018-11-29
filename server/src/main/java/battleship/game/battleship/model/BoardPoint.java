package battleship.game.battleship.model;

import java.util.Objects;

public class BoardPoint {
    private int rowId;
    private int columnId;

    public BoardPoint() {
    }

    public BoardPoint(int rowId, int columnId) {
        this.rowId = rowId;
        this.columnId = columnId;
    }

    public int getRowId() {
        return rowId;
    }

    public int getColumnId() {
        return columnId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardPoint that = (BoardPoint) o;
        return rowId == that.rowId &&
                columnId == that.columnId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(rowId, columnId);
    }

    @Override
    public String toString() {
        return "BoardPoint{" +
                "rowId=" + rowId +
                ", columnId=" + columnId +
                '}';
    }
}
