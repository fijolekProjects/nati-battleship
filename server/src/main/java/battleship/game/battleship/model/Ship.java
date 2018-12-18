package battleship.game.battleship.model;

import java.util.List;

public class Ship {
    private List<BoardPoint> ship;

    public Ship(List<BoardPoint> ship) {
        this.ship = ship;
    }

    public List<BoardPoint> getShip() {
        return ship;
    }
}
