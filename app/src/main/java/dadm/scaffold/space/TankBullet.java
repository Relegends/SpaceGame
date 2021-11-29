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

    private double angle;
    private double gravity;

    long currentTime;

    public TankBullet(Tank tank, GameEngine gameEngine) {
        super(gameEngine, R.drawable.a10000);
        this.tank = tank;
        currentTime = 0;
    }

    @Override
    public void startGame() {
    }

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        tank.returnToPool(this);
    }

    public void init(GameEngine gameEngine, double initPositionX, double initPositionY) {
        gravity = 0.001;
        // Tank bullets initialize in the cannon of the tank
        positionX = initPositionX;
        positionY = initPositionY;
        // They initialize in a [-30, 30] degrees angle
        speedX = -1;
        speedY = -gameEngine.random.nextFloat() - 0.5f;
        // They rotate 4 times their angle in a second.
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        rotationSpeed = angle * (180d / Math.PI) / 250d;
        rotation = gameEngine.random.nextInt(360);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentTime += elapsedMillis;

        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        speedY += gravity * elapsedMillis;


        rotation += rotationSpeed * elapsedMillis;
        if (rotation > 360) {
            rotation = 0;
        } else if (rotation < 0) {
            rotation = 360;
        }
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            tank.returnToPool(this);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
