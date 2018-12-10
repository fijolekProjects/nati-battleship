package battleship.game.battleship;

import battleship.game.battleship.model.*;
import battleship.game.battleship.utils.BoardGenerator;
import org.junit.Assert;
import org.junit.Test;

public class BoardGeneratorTest {
    Board boardGenerator = BoardGenerator.generateBoard(10, 10);

    @Test
    public void BoardShouldBeSize10x10() {

        Assert.assertEquals(boardGenerator.getBoard().size(), 10);
        Assert.assertEquals(boardGenerator.getBoard().get(0).size(), 10);

        System.out.println(boardGenerator.getBoard());
    }



}
