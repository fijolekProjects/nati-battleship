package battleship.game.battleship.logic;

import battleship.game.battleship.model.Board;
import battleship.game.battleship.model.BoardPoint;
import battleship.game.battleship.model.Player;
import battleship.game.battleship.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameLogic {

//    ta inicjalizacja powinna byc w konstroktorze, tak zeby tylko `players` bylo zmienna w tej klasie (a najlepiej obiekt Game)
    private List<BoardPoint> mishitsPlayer1 = new ArrayList<>();
    private List<BoardPoint> mishitsPlayer2 = new ArrayList<>();
    private List<BoardPoint> hittedShipPointsPlayer1 = new ArrayList<>();
    private List<BoardPoint> hittedShipPointsPlayer2 = new ArrayList<>();
    private List<List<BoardPoint>> hittedShipsPlayer1 = new ArrayList<List<BoardPoint>>();
    private List<List<BoardPoint>> hittedShipsPlayer2 = new ArrayList<List<BoardPoint>>();
    private Board board = BoardGenerator.generateBoard(10, 10);
    private List<List<BoardPoint>> generateShipsForPlayer1 = new ShipsGenerator().getShips();
    private List<List<BoardPoint>> generateShipsForPlayer2 = new ShipsGenerator().getShips();
    private Player playerOne = new Player(1, board, generateShipsForPlayer1, true, mishitsPlayer1, hittedShipPointsPlayer1, hittedShipsPlayer1);
    private Player playerTwo = new Player(2, board, generateShipsForPlayer2, false, mishitsPlayer2, hittedShipPointsPlayer2, hittedShipsPlayer2);
    private List<Player> players = new ArrayList<Player>() {{
        add(playerOne);
        add(playerTwo);
    }};

    public List<Player> playGame(int playerId, BoardPoint boardPoint) {
        Player player = getPlayer(playerId);
        Player enemy = getEnemy(playerId);
        List<List<BoardPoint>> ships = player.getShips();
        List<BoardPoint> flattenShips = ListOperation.flatLists(ships);
        List<BoardPoint> mishitsPlayer = player.getMishitPoints();
        List<BoardPoint> flatBoard = ListOperation.flatLists(player.getBoard().getBoard());
        if (BoardsOperations.whetherTheListContainsBoardPoint(flattenShips, boardPoint)) {
            List<BoardPoint> hittedShipPoints = player.getHittedShipPoints();
            hittedShipPoints.add(boardPoint);
            List<List<BoardPoint>> hittedShips = BoardsOperations.whetherListOfListsContainsAllPoints(ships, hittedShipPoints);
            player.setHittedShips(hittedShips);
            player.setYourTurn(true);
            enemy.setYourTurn(false);
            //to mutowanie nie jest zbyt dobre, zauwaz ze pobierasz najpierw dane z playera (`player.getMishitPoints()`),
            // pozniej do tych danych dodajesz cos (mishitsPlayer.addAll(SurroundedPointsLogic.diag...) i po tej linijce zmienia sie rowniez
            // sam player i wywolanie `player.getMishitPoints()` zwroci co innego niz na poczatku!
            // dla mnie to jest mega nielogiczne i staram sie tak nie pisac
            //tutaj przydaloby sie zrobic najpierw kopie przynajmniej tej listy mishitsPlayer np. tak:
//            ArrayList<BoardPoint> mishitsPlayerCopy = new ArrayList<>(mishitsPlayer);
//            mishitsPlayerCopy.addAll(SurroundedPointsLogic.dia...
//            player.setMishitPoints(mishitsPlayerCopy)
            //powyzszy kod moze tez byc bardziej obiektowy, idealnie by bylo zeby klasa Player nie miala publicznych setterow i wszystkie wyliczenia dzialy sie w srodu klasy
            //getterow tez nie musi byc, bo jak widzisz w przykladzie z lista moga byc niebezpieczne (w javie :P)
            mishitsPlayer.addAll(SurroundedPointsLogic.diagonalPointsThatBelongsToBoard(flatBoard, boardPoint));
            List<List<BoardPoint>> hittedShip = BoardsOperations.whetherTheListOfListsContainsBoardPoint(hittedShips, boardPoint);
            if (hittedShip.size() != 0) {
                mishitsPlayer.addAll(SurroundedPointsLogic.getSurroundedPointsIfHittedShip(flatBoard, hittedShip.get(0)));
            }
            return players;
        } else {
            mishitsPlayer.add(boardPoint);
            player.setYourTurn(false);
            enemy.setYourTurn(true);
            return players;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int playerId) {
        return players.stream().filter(p -> p.getPlayerId() == playerId).collect(Collectors.toList()).get(0);
    }

    private Player getEnemy(int playerId) {
        return players.stream().filter(p -> p.getPlayerId() != playerId).collect(Collectors.toList()).get(0);
    }
}
