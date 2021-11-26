package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class TankBullet extends Sprite {

    private Tank tank;

    private double speed;
    private double speedX;
    private double speedY;
    private double rotationSpeed;

    public TankBullet(Tank tank, GameEngine gameEngine) {
        super(gameEngine, R.drawable.tankBullet);
        this.speed = 200d * gameEngine.pixelFactor/1000d;
        this.tank = tank;
    }

    @Override
    public void startGame() {}

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        tank.returnToPool(this);
    }

    public void init(GameEngine gameEngine, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speed * Math.sin(angle);
        speedY = speed * Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen horizontally
        positionX = gameEngine.random.nextInt(gameEngine.width/2)+gameEngine.width/4;
        // They initialize outside of the screen vertically
        positionY = -height;
        rotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their angle in a second.
        rotation = gameEngine.random.nextInt(360);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
