package dadm.scaffold;

public class GameLogic {

    public static GameLogic GAME = new GameLogic();

    public boolean isPlaying;

    private int progress;

    private IkarugaState playerIkarugaState;

    private IkarugaState marbleIkarugaState;

    private IkarugaState paperBallIkarugaState;

    private IkarugaState bulletIkarugaState;

    private int playerLives;

    private int paperBallsDestroyed;
    private int marblesDestroyed;

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
        paperBallsDestroyed = 0;
        marblesDestroyed = 0;
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

    public void setMarbleIkarugaState(IkarugaState marbleIkarugaState) {
        this.marbleIkarugaState = marbleIkarugaState;
    }

    public void setPaperBallIkarugaState(IkarugaState paperBallIkarugaState) {
        this.paperBallIkarugaState = paperBallIkarugaState;
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

    public int getDrawablePlane() {
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

    public int getDrawableMarble() {
        switch (marbleIkarugaState) {
            case BLUE:
                return R.drawable.blue_marble;
            case BLACK:
                return R.drawable.black_marble;
            default:
                return -1;
        }
    }

    public int getDrawablePaperBall() {
        switch (paperBallIkarugaState) {
            case RED:
                return R.drawable.red_paper_ball;
            case WHITE:
                return R.drawable.white_paper_ball;
            default:
                return -1;
        }
    }

    public int getDrawableBullet() {
        switch (playerIkarugaState) {
            case WHITE:
            case RED:
                return R.drawable.bullet;
            case BLACK:
            case BLUE:
                return R.drawable.bullet_invert;
            default:
                return -1;
        }
    }

    public int getMarblesDestroyed() {
        return marblesDestroyed;
    }

    public int getPaperBallsDestroyed() {
        return paperBallsDestroyed;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public void addMarbleDestroyed() {
        marblesDestroyed++;
    }

    public void addPaperBallDestroyed() {
        paperBallsDestroyed++;
    }
}