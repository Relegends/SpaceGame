package dadm.scaffold;

public class GameLogic {

    public static GameLogic GAME = new GameLogic();

    private int progress = 100;

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }
}
