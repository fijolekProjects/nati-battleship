package battleship.game.battleship;

import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Ship;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BoardGeneratorTest {
    Board boardGenerator = new Board();

    @Test
    public void BoardShouldBeSize10x10() {

        Assert.assertEquals(boardGenerator.getBoard().size(), 10);
        Assert.assertEquals(boardGenerator.getBoard().get(0).size(), 10);

    }

}
