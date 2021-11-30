package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;
import dadm.scaffold.sound.GameEvent;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final int INITIAL_BOMBS_POOL_AMOUNT = 1;
    private static final long TIME_BETWEEN_BULLETS = 250;
    private static final long TIME_BETWEEN_BOMBS = 3000;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Bomb> bombs = new ArrayList<Bomb>();
    private long timeSinceLastFire;
    private long timeSinceLastBomb;

    private int maxX;
    private int maxY;
    private double speedFactor;


    public SpaceShipPlayer(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ship);
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
        checkFiringBomb(elapsedMillis, gameEngine);
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
        if (gameEngine.theInputController.isFiringBullet && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
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
            gameEngine.onGameEvent(GameEvent.LaserFired);
        } else {
            timeSinceLastBomb += elapsedMillis;
        }
    }


    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            gameEngine.removeGameObject(this);
            //gameEngine.stopGame();
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
        } else if (otherObject instanceof TankBullet) {
            gameEngine.removeGameObject(this);
            //gameEngine.stopGame();
            TankBullet t = (TankBullet) otherObject;
            t.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
        } else if (otherObject instanceof Tank) {
            gameEngine.removeGameObject(this);
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
        }
    }
}
