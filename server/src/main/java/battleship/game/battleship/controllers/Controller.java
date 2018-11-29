package battleship.game.battleship.controllers;

import battleship.game.battleship.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private List<BoardPoint> mishitsPlayer1 = new ArrayList<>();
    private List<BoardPoint> mishitsPlayer2 = new ArrayList<>();
    private List<BoardPoint> hittedShipsPlayer1 = new ArrayList<>();
    private List<BoardPoint> hittedShipsPlayer2 = new ArrayList<>();
    private Player playerOne = new Player(1, BoardGenerator.generateBoard(10, 10), ShipsGenerator.generateShipsForPlayer1(), true, mishitsPlayer1, hittedShipsPlayer1);
    private Player playerTwo = new Player(2, BoardGenerator.generateBoard(10, 10), ShipsGenerator.generateShipsForPlayer2(), false, mishitsPlayer2, hittedShipsPlayer2);
    private List<Player> players = new ArrayList<Player>() {{
        add(playerOne);
        add(playerTwo);
    }};

    @RequestMapping(value = "players", method = RequestMethod.POST)
    public ResponseEntity<List<Player>> boardPoint(@RequestBody(required = false) HittedBoardPoint hittedBoardPoint) {
        if (hittedBoardPoint != null) {
            int playerId = hittedBoardPoint.getPlayerId();
            BoardPoint boardPoint = hittedBoardPoint.getBoardPoint();
            Player player = players.stream().filter(p -> p.getPlayerId() == playerId).collect(Collectors.toList()).get(0);
            Player enemy = players.stream().filter(p -> p.getPlayerId() != playerId).collect(Collectors.toList()).get(0);
            List<Ship> ships = player.getShips();
            if (isItAShipPoint(ships, boardPoint)) {
                List<BoardPoint> hittedShipsPlayer = player.getHittedShipsPlayer();
                hittedShipsPlayer.add(boardPoint);
                player.setYourTurn(true);
                enemy.setYourTurn(false);
                return new ResponseEntity<>(players, HttpStatus.OK);
            } else {
                List<BoardPoint> mishitsPlayer = player.getMishitsPlayer();
                mishitsPlayer.add(boardPoint);
                player.setYourTurn(false);
                enemy.setYourTurn(true);
                return new ResponseEntity<>(players, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    private boolean isItAShipPoint(List<Ship> playerShips, BoardPoint boardPoint) {
        return playerShips.stream().anyMatch(ship ->
                ship.getShipPoints().contains(boardPoint));
    }
}
