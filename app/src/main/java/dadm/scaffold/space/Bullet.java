package dadm.scaffold.space;

import dadm.scaffold.GameLogic;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Bullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;

    public Bullet(GameEngine gameEngine) {
        super(gameEngine, R.drawable.bullet);

        speedFactor = gameEngine.pixelFactor * 300d / 1000d;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += speedFactor * elapsedMillis;
        if ((positionX) > gameEngine.width) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width / 2;
        positionY = initPositionY - height / 2;
        parent = parentPlayer;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.AsteroidHit);
            // Add some score
        } else if (otherObject instanceof TankBullet) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            TankBullet t = (TankBullet) otherObject;
            t.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.AsteroidHit);
            // Add some score
        } else if (otherObject instanceof Tank) {
            removeObject(gameEngine);
            Tank t = (Tank) otherObject;
            t.health--;
            gameEngine.onGameEvent(GameEvent.TankHit);
            GameLogic.GAME.setProgress(t.health);
            if (t.health < 50) t.changeDrawable(gameEngine, R.drawable.tank_sad);
            if (t.health <= 0) {
                gameEngine.removeGameObject(otherObject);
                gameEngine.onGameEvent(GameEvent.TankDestroyed);
                gameEngine.onGameEvent(GameEvent.Win);
            }
        }
    }
}
