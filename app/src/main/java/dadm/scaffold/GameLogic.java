package dadm.scaffold;

public class GameLogic {

    public static GameLogic GAME = new GameLogic();

    public boolean isPlaying;

    private int progress;

    private IkarugaState playerIkarugaState;

    private int playerLives;

    public GameLogic() {
        isPlaying = false;
        progress = 100;
        setPlayerIkarugaState(IkarugaState.WHITE);
        playerLives = 3;
    }

    public void reduceLives() {
        this.playerLives--;
    }

    public void resetProgress() {
        this.progress = 100;
        this.playerLives = 3;
        this.isPlaying = true;
    }

    public int getLives() {
        return playerLives;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setPlayerIkarugaState(IkarugaState playerIkarugaState) {
        this.playerIkarugaState = playerIkarugaState;
    }

    public IkarugaState getPlayerIkarugaState() {
        return playerIkarugaState;
    }

    public void changeIkarugaState() {
        switch (playerIkarugaState) {
            case RED:
                playerIkarugaState = IkarugaState.BLUE;
                break;
            case BLUE:
                playerIkarugaState = IkarugaState.RED;
                break;
            case WHITE:
                playerIkarugaState = IkarugaState.BLACK;
                break;
            case BLACK:
                playerIkarugaState = IkarugaState.WHITE;
                break;
        }
    }

    public int getDrawable() {
        switch (playerIkarugaState) {
            case RED:
                return R.drawable.red_plane;
            case BLUE:
                return R.drawable.blue_plane;
            case WHITE:
                return R.drawable.white_plane;
            case BLACK:
                return R.drawable.black_plane;
            default:
                return -1;
        }
    }
}