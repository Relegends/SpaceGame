package dadm.scaffold.space;

import dadm.scaffold.GameLogic;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Bomb extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;

    private double speedX;
    private double speedY;
    private double rotationSpeed;
    private double gravity;

    public Bomb(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);

        gravity = 0.001;

        speedX = 1;
        speedY = gameEngine.random.nextFloat() - 0.5f;
        speedY = 1 - 0.5f;


        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        angle = 1*Math.PI/6d;
        rotationSpeed = angle * (180d / Math.PI) / 250d;
        rotation = gameEngine.random.nextInt(360);
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
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
            parent.releaseBomb(this);
            speedY = 1 - 0.5f;
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentPlayer;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBomb(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.AsteroidHitBomb);
            // Add some score
        }
        else if (otherObject instanceof Tank) {
            removeObject(gameEngine);
            Tank t = (Tank) otherObject;
            t.health -= 15;
            GameLogic.GAME.setProgress(t.health);
            if (t.health <= 0) {
                gameEngine.removeGameObject(otherObject);
                //Add sound
            }
        }
    }
}
