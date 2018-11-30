package battleship.game.battleship;

import battleship.game.battleship.controllers.Controller;
import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardGenerator;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.ShipsGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BattleshipApplicationTests {

	@Autowired
	Controller controller;

	@Test
	public void contextLoads() {
		List<List<BoardPoint>> ships = ShipsGenerator.generateShipsForPlayer1();
		List<BoardPoint> boardPoints = new ArrayList<>();
		boardPoints.add(new BoardPoint(9,4));
//		boardPoints.add(new BoardPoint(9,5));
		boardPoints.add(new BoardPoint(0,5));
		boardPoints.add(new BoardPoint(10,5));
		boardPoints.add(new BoardPoint(13,5));
		boardPoints.add(new BoardPoint(4,8));

		List<List<BoardPoint>> collect = ships.stream().filter(ship -> boardPoints.containsAll(ship)).collect(Collectors.toList());

		System.out.println("MOJA LISTA "+ collect);
	}

}
