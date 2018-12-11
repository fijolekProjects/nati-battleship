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


    //ten List<Player> powinien byc raczej obiektem np.
    //class Game {
//      Player player1;
//      Player player2;
    //}
//    ta lista to troche jednak nie pasuje, szczegolnie wyciaganie z niej jest niezreczne, bo wszedzie i tak zakladasz ze tam jest 2 graczy, wiec po co w sumie ta lista?
//    nie ma takiego slowa hitted :)


//    na frontend nie powinno byc zwracane `ships` z Player - bo mozna sobie podejrzec i wygrac gre :P
    @RequestMapping(value = "player", method = RequestMethod.POST)
    public ResponseEntity<List<Player>> player(@RequestBody(required = false) BoardPoint hittedBoardPoint) {
        Player player = players.get(0);
        if (hittedBoardPoint != null) {
            //w calym kodzie brakuje obiektowosci tzn. np ta metoda checkIfTheGameIsOver, powinna byc wewnatrz klasy Player, na pewno nie w kontrolerze
            //podobnie z prawie z wszystkimi metodami statycznymi - tam gdzie ich uzywasz to prawdopodobnie lepiej by bylo dodac taka metode do ktorejs z klas,
            //tak zeby nie przerzucac danych z klasy do przeliczania gdziestam, tylko zeby klasa sama wiedziala jak ma sobie costam obliczyc
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

    //konwencja jest taka ze metody ktore zwracaja boolean nazywa sie np. tak: isGameOver,
    // tzn daje sie prefix `is` - nawet jak generujesz gettera w idei do pola boolean to zamiast `get` daje prefix `is`
    private boolean checkIfTheGameIsOver(List<List<BoardPoint>> hittedShips, List<List<BoardPoint>> ships) {
        return hittedShips.size() == ships.size();
    }
}
