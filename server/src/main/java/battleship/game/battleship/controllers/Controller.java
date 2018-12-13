package battleship.game.battleship.controllers;

import battleship.game.battleship.logic.ComputerLogic;
import battleship.game.battleship.logic.GameLogic;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Game;
import battleship.game.battleship.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private GameLogic gameLogic = null;
    private ComputerLogic computerLogic = null;
    private Player player = null;
    private Player computer = null;

    @RequestMapping(value = "newGame", method = RequestMethod.GET)
    public ResponseEntity<Game> newGame() {
        gameLogic = new GameLogic();
        computerLogic = new ComputerLogic();
        Game game = gameLogic.getGame();
        player = game.getPlayer();
        computer = game.getComputer();
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @RequestMapping(value = "player", method = RequestMethod.POST)
    public ResponseEntity<Game> player(@RequestBody(required = false) BoardPoint hittedBoardPoint) {
        return new ResponseEntity<>(gameLogic.playGame(player, computer, hittedBoardPoint), HttpStatus.OK);
    }

    @RequestMapping(value = "computer", method = RequestMethod.POST)
    public ResponseEntity<Game> computer() {
        return new ResponseEntity<>(gameLogic.playGame(computer, player, computerLogic.hitBoardPoint(computer)), HttpStatus.OK);
    }
}
