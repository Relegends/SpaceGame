package dadm.scaffold;

public class GameLogic {

    public static GameLogic GAME = new GameLogic();
    public boolean isPlaying = false;

    private int progress = 100;


    private int playerLives = 3;

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
}
