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
        super(gameEngine, R.drawable.bullet);
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
        // Asteroids initialize in the cannon of the tank
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speed * Math.sin(angle);
        speedY = speed * Math.cos(angle);
        // They rotate 4 times their angle in a second.
        rotationSpeed = angle*(180d / Math.PI)/250d;
        rotation = gameEngine.random.nextInt(360);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        rotation += rotationSpeed * elapsedMillis;
        if (rotation > 360) {
            rotation = 0;
        }
        else if (rotation < 0) {
            rotation = 360;
        }
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionX > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            tank.returnToPool(this);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
