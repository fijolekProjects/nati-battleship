package battleship.game.battleship;

import battleship.game.battleship.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BoardGeneratorTest {
    Board boardGenerator = BoardGenerator.generateBoard(10, 10);

    @Test
    public void BoardShouldBeSize10x10() {

        Assert.assertEquals(boardGenerator.getBoard().size(), 10);
        Assert.assertEquals(boardGenerator.getBoard().get(0).size(), 10);
    }

    @Test
    public void isItAShipPoint() {
        BoardPoint boardPoint = new BoardPoint(0,5);
        List<Ship> ships = ShipsGenerator.generateShipsForPlayer1();
        boolean isItAShipPoint = ships.stream().anyMatch(ship -> ship.getShipPoints().contains(boardPoint));

        Assert.assertTrue(isItAShipPoint);
    }

}
