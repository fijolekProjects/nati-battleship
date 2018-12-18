package battleship.game.battleship;

import battleship.game.battleship.model.*;
import battleship.game.battleship.utils.BoardGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoardGeneratorTest {
    Board boardGenerator = BoardGenerator.generateBoard(10, 10);

    @Test
    public void BoardShouldBeSize10x10() {

        Assert.assertEquals(boardGenerator.getBoard().size(), 10);
        Assert.assertEquals(boardGenerator.getBoard().get(0).size(), 10);

    }

    @Test
    public void flatShips() {
        List<BoardPoint> boardPointsShip1 = Arrays.asList(new BoardPoint(0,1), new BoardPoint(0,2));
        List<BoardPoint> boardPointsShip2 = Arrays.asList(new BoardPoint(2,3), new BoardPoint(3,3));
        Ship ship1 = new Ship(boardPointsShip1);
        Ship ship2 = new Ship(boardPointsShip2);
        List<Ship> ships = Arrays.asList(ship1, ship2);
        System.out.println(ships);
    }


    public static List<BoardPoint> flatShips(List<Ship> lists) {
        return lists.stream()
                .flatMap(ship -> ship.getShip().stream())
                .collect(Collectors.toList());
    }



}
