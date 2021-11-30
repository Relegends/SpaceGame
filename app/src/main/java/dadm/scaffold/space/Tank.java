package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.GameLogic;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Tank extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 10;
    private static final int TIME_BETWEEN_BULLETS = 500;
    private long currentMillis;
    private List<TankBullet> tankBulletPool = new ArrayList<TankBullet>();
    private int bulletsFired;

    public int health;

    public Tank(GameEngine gameEngine) {
        super(gameEngine, R.drawable.tank);
        // We initialize the pool of items now
        initBulletPool(gameEngine);

        positionX = gameEngine.width/2 + 300;
        positionY = gameEngine.height/2 + 100;
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            tankBulletPool.add(new TankBullet(this, gameEngine));
        }
    }

    @Override
    public void startGame() {
        health = 100;
        currentMillis = 0;
        bulletsFired = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;

        long waveTimestamp = bulletsFired * TIME_BETWEEN_BULLETS;
        if (currentMillis > waveTimestamp) {
            // Spawn a new enemy
            TankBullet t = tankBulletPool.remove(0);
            t.init(gameEngine, positionX, positionY);
            gameEngine.addGameObject(t);
            bulletsFired++;
            return;
        }
    }

    public void returnToPool(TankBullet tankBullet) {
        tankBulletPool.add(tankBullet);
    }


    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }


}
