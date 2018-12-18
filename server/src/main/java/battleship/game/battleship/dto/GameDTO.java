package battleship.game.battleship.dto;

public class GameDTO {
    private PlayerDTO player;
    private PlayerDTO computer;
    private boolean isGameRunning;

    public GameDTO(PlayerDTO player, PlayerDTO computer, boolean isGameRunning) {
        this.player = player;
        this.computer = computer;
        this.isGameRunning = isGameRunning;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public PlayerDTO getComputer() {
        return computer;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }
}
