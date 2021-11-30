package dadm.scaffold.space;

import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.GameLogic;
import dadm.scaffold.IkarugaState;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;
import dadm.scaffold.sound.GameEvent;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final int INITIAL_BOMBS_POOL_AMOUNT = 1;
    private static final long TIME_BETWEEN_BULLETS = 500;
    private static final long TIME_BETWEEN_BOMBS = 3000;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Bomb> bombs = new ArrayList<Bomb>();
    private long timeSinceLastFire;

    private boolean hitDelay = false;
    private long timer = 0;


    private long timeSinceLastBomb;

    private IkarugaState ikarugaState;


    private int maxX;
    private int maxY;
    private double speedFactor;


    public SpaceShipPlayer(GameEngine gameEngine) {
        super(gameEngine, GameLogic.GAME.getDrawable());
        ikarugaState = GameLogic.GAME.getPlayerIkarugaState();
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;

        initBulletPool(gameEngine);
        initBombPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    private void initBombPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BOMBS_POOL_AMOUNT; i++) {
            bombs.add(new Bomb(gameEngine));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    private Bomb getBomb() {
        if (bombs.isEmpty()) {
            return null;
        }
        return bombs.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    void releaseBomb(Bomb bomb) {
        bombs.add(bomb);
    }


    @Override
    public void startGame() {
        positionX = 0;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);

        checkPossibleHit(elapsedMillis);

        checkFiringBomb(elapsedMillis, gameEngine);

        checkIkarugaState(elapsedMillis, gameEngine);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width, positionY + height / 2);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        } else {
            timeSinceLastFire += elapsedMillis;
            timeSinceLastBomb += elapsedMillis;
        }
    }

    private void checkFiringBomb(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.isFiringBomb && timeSinceLastBomb > TIME_BETWEEN_BOMBS) {
            Bomb bomb = getBomb();
            if (bomb == null) {
                return;
            }
            bomb.init(this, positionX + width, positionY + height / 2);
            gameEngine.addGameObject(bomb);
            timeSinceLastBomb = 0;
            gameEngine.onGameEvent(GameEvent.AsteroidHitBomb);
        } else {
            timeSinceLastBomb += elapsedMillis;
        }
    }

    private void checkIkarugaState(long elapsedMillis, GameEngine gameEngine) {
        if (GameLogic.GAME.getPlayerIkarugaState() != ikarugaState) {
            ikarugaState = GameLogic.GAME.getPlayerIkarugaState();
            changeDrawable(gameEngine, GameLogic.GAME.getDrawable());

            //gameEngine.onGameEvent(GameEvent.AsteroidHitBomb);
        } else {
            timeSinceLastBomb += elapsedMillis;
        }
    }


    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            if (ikarugaState == IkarugaState.RED || ikarugaState == IkarugaState.WHITE) return;
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            checkDamage(gameEngine);
        } else if (otherObject instanceof TankBullet) {
            if (ikarugaState == IkarugaState.BLUE || ikarugaState == IkarugaState.BLACK) return;
            TankBullet t = (TankBullet) otherObject;
            t.removeObject(gameEngine);
            checkDamage(gameEngine);
        } else if (otherObject instanceof Tank) {
            checkDamage(gameEngine);
        }
        // GAME OVER
        if (GameLogic.GAME.getLives() <= 0) {
            gameEngine.removeGameObject(this);
            gameEngine.onGameEvent(GameEvent.GameOver);
        }
    }

    private void checkPossibleHit(long elapsedMillis) {
        if (hitDelay) {
            timer += elapsedMillis;
        }
        if (timer > 750) {
            hitDelay = false;
            timer = 0;
        }
    }

    private void checkDamage(GameEngine gameEngine){
        if (!hitDelay) {
            GameLogic.GAME.reduceLives();
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
            hitDelay = true;
            this.startGame();
        }
    }
}
