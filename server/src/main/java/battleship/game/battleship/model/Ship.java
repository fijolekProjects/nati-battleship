package battleship.game.battleship.model;

import java.util.List;

public class Ship {
    private List<BoardPoint> shipPoints;

    public Ship(List<BoardPoint> shipPoints) {
        this.shipPoints = shipPoints;
    }

    public List<BoardPoint> getShipPoints() {
        return shipPoints;
    }
}
