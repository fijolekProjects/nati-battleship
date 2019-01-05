package battleship.game.battleship.model;

import battleship.game.battleship.utils.SurroundedPointsLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private int playerId;
    private Board board;
    private List<Ship> ships;
    private boolean isYourTurn;
    private List<BoardPoint> mishitPoints;
    private List<BoardPoint> hittedShipPoints;
    private List<Ship> hittedShips;

    public Player(int playerId, Board board, List<Ship> ships, boolean isYourTurn, List<BoardPoint> mishitPoints, List<BoardPoint> hittedShipPoints, List<Ship> hittedShips) {
        this.playerId = playerId;
        this.board = board;
        this.ships = ships;
        this.isYourTurn = isYourTurn;
        this.mishitPoints = mishitPoints;
        this.hittedShipPoints = hittedShipPoints;
        this.hittedShips = hittedShips;
    }

    //Wydaje mi sie ze bedzie troche czytalniej jak:
    //- przeniesiesz wyliczanie mishitsPlayerCopy do osobnej metody tzn:
    // mishitPoints = currentMishitPoints(); albo mishitPoints.addAll(newMishitPoints()) zeby bylo spojne z `mishitPoints.add(boardPoint)` ktore jest ponizej
    // - nazwiesz `flatShips(ships).contains(boardPoint)` jako np. shipWasHit
    // - wynieszesz zmiane flag isYourTurn do metody np. swapTurn()
    public void updatePlayerState(BoardPoint boardPoint, Player enemy) {
        ArrayList<BoardPoint> mishitsPlayerCopy = new ArrayList<>(mishitPoints);
        if (flatShips(ships).contains(boardPoint)) {
            hittedShipPoints.add(boardPoint);
            filterShipsFromHittedPoints();
            isYourTurn = true;
            enemy.isYourTurn = false;
            mishitsPlayerCopy.addAll(SurroundedPointsLogic.getDiagonallySurroundedPoints(board, boardPoint));
            List<Ship> hittedShip = whetherTheListOfListsContainsBoardPoint(boardPoint);
            if (hittedShip.size() != 0) {
                mishitsPlayerCopy.addAll(SurroundedPointsLogic.getSurroundedPointsIfHittedShip(board, hittedShip.get(0).getShip()));
            }
            mishitPoints = mishitsPlayerCopy;
        } else {
            mishitPoints.add(boardPoint);
            isYourTurn = false;
            enemy.isYourTurn = true;
        }
    }

    private List<Ship> whetherTheListOfListsContainsBoardPoint(BoardPoint boardPoint) {
        return hittedShips.stream().filter(list -> list.getShip().contains(boardPoint)).collect(Collectors.toList());
    }

    private void filterShipsFromHittedPoints() {
        hittedShips = ships.stream().filter(ship -> hittedShipPoints.containsAll(ship.getShip())).collect(Collectors.toList());
    }

    public List<BoardPoint> flatShips(List<Ship> ships) {
        return ships.stream()
                .flatMap(ship -> ship.getShip().stream())
                .collect(Collectors.toList());
    }

    public boolean isGameOver() {
        return this.hittedShips.size() == this.ships.size();
    }

    public int getPlayerId() {
        return playerId;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public List<BoardPoint> getMishitPoints() {
        return mishitPoints;
    }

    public List<BoardPoint> getHittedShipPoints() {
        return hittedShipPoints;
    }

    public List<Ship> getHittedShips() {
        return hittedShips;
    }
}
