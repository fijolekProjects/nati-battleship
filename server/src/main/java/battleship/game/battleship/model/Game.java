package battleship.game.battleship.model;

public class Game {
    private Player player;
    private Player computer;
    private boolean isGameRunning;

    public Game(Player player, Player computer, boolean isGameRunning) {
        this.player = player;
        this.computer = computer;
        this.isGameRunning = isGameRunning;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }
}
