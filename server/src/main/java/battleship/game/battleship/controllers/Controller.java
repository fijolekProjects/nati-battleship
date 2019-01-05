package battleship.game.battleship.controllers;

import battleship.game.battleship.dto.GameDTO;
import battleship.game.battleship.dto.PlayerDTO;
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
    //tutaj jest za duzo tego stanu
    //normalnie w kontrolerze nie mielibysmy zadnego stanu, tutaj robimy wyjatek ze wzgledu na to, ze chcemy przechowywac stan gry
    //staralbym sie tutaj jako stan, miec tylko jeden obiekt np Game, przez ktory zachodzilyby wszystkie zmiany stane - to bylby taki root do zarzadzania cala gra

    private GameLogic gameLogic = null;
    private ComputerLogic computerLogic = null;
    private Player player = null;
    private Player computer = null;
    private PlayerDTO playerDTO = null;
    private PlayerDTO computerDTO = null;

    @RequestMapping(value = "newGame", method = RequestMethod.GET)
    public ResponseEntity<GameDTO> newGame() {
        gameLogic = new GameLogic();
        computerLogic = new ComputerLogic();
        Game game = gameLogic.getGame();
        player = game.getPlayer();
        computer = game.getComputer();
        playerDTO = new PlayerDTO(player);
        computerDTO = new PlayerDTO(computer);
        GameDTO gameDTO = new GameDTO(playerDTO, computerDTO, game.isGameRunning());
        return new ResponseEntity<>(gameDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "player", method = RequestMethod.POST)
    public ResponseEntity<GameDTO> player(@RequestBody(required = false) BoardPoint hittedBoardPoint) {
        Game game = gameLogic.playGame(player, computer, hittedBoardPoint);
        playerDTO = new PlayerDTO(game.getPlayer());
        computerDTO = new PlayerDTO(game.getComputer());
        GameDTO gameDTO = new GameDTO(playerDTO, computerDTO, game.isGameRunning());
        return new ResponseEntity<>(gameDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "computer", method = RequestMethod.POST)
    public ResponseEntity<GameDTO> computer() {
        BoardPoint boardPoint = computerLogic.hitBoardPoint(computer);
        Game game = gameLogic.playGame(computer, player, boardPoint);
        computerDTO = new PlayerDTO(game.getPlayer());
        playerDTO = new PlayerDTO(game.getComputer());
        GameDTO gameDTO = new GameDTO(computerDTO, playerDTO, game.isGameRunning());
        return new ResponseEntity<>(gameDTO, HttpStatus.OK);
    }

}
