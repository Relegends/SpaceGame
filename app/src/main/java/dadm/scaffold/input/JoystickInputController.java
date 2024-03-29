package dadm.scaffold.input;

import android.view.MotionEvent;
import android.view.View;

import dadm.scaffold.GameLogic;
import dadm.scaffold.R;

public class JoystickInputController extends InputController {

    private float startingPositionX;
    private float startingPositionY;

    private final double maxDistance;

    public JoystickInputController(View view) {
        view.findViewById(R.id.joystick_main).setOnTouchListener(new JoystickTouchListener());
        view.findViewById(R.id.joystick_touch_color).setOnTouchListener(new FireButtonTouchListener());
        view.findViewById(R.id.joystick_touch_bomb).setOnTouchListener(new BombButtonTouchListener());

        double pixelFactor = view.getHeight() / 400d;
        maxDistance = 50*pixelFactor;
    }

    private class JoystickTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startingPositionX = event.getX(0);
                    startingPositionY = event.getY(0);
                    break;
                case MotionEvent.ACTION_UP:
                    horizontalFactor = 0;
                    verticalFactor = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // Get the proportion to the max
                    horizontalFactor = (event.getX(0) - startingPositionX) / maxDistance;
                    if (horizontalFactor > 1) {
                        horizontalFactor = 1;
                    }
                    else if (horizontalFactor < -1) {
                        horizontalFactor = -1;
                    }
                    verticalFactor = (event.getY(0) - startingPositionY) / maxDistance;
                    if (verticalFactor > 1) {
                        verticalFactor = 1;
                    }
                    else if (verticalFactor < -1) {
                        verticalFactor = -1;
                    }
                    break;
            }

            return true;
        }
    }

    private class FireButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                GameLogic.GAME.changeIkarugaState();
            }
            return true;
        }
    }

    private class BombButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                isFiringBomb = true;
            }
            else if (action == MotionEvent.ACTION_UP) {
                isFiringBomb = false;
            }
            return true;
        }
    }
}

