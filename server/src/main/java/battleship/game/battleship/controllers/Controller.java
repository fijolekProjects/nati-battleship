package battleship.game.battleship.controllers;

import battleship.game.battleship.logic.ComputerLogic;
import battleship.game.battleship.logic.GameLogic;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private GameLogic gameLogic = new GameLogic();
    private ComputerLogic computerLogic = new ComputerLogic(gameLogic);
    private List<Player> players = gameLogic.getPlayers();

    @RequestMapping(value = "player", method = RequestMethod.POST)
    public ResponseEntity<List<Player>> player(@RequestBody(required = false) BoardPoint hittedBoardPoint) {
        Player player = players.get(0);
        if (hittedBoardPoint != null) {
            if (checkIfTheGameIsOver(player.getHittedShips(), player.getShips())) {
                return new ResponseEntity<>(players, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(gameLogic.playGame(1, hittedBoardPoint), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(players, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "computer", method = RequestMethod.POST)
    public ResponseEntity<List<Player>> computer() {
        Player player = players.get(1);
        if (checkIfTheGameIsOver(player.getHittedShips(), player.getShips())) {
            return new ResponseEntity<>(players, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(gameLogic.playGame(2, computerLogic.hitBoardPoint(2)), HttpStatus.OK);
        }
    }

    private boolean checkIfTheGameIsOver(List<List<BoardPoint>> hittedShips, List<List<BoardPoint>> ships) {
        return hittedShips.size() == ships.size();
    }
}
