package dadm.scaffold.space;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class Background extends GameObject {

    private final GameEngine gameEngine;

    private final Bitmap background1;
    private final Bitmap background2;

    private final Rect src1;
    private final Rect src2;
    private final Rect src22;
    private final Rect dst;

    private long time = 0;

    public Background(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        dst= new Rect(0,0,gameEngine.width, gameEngine.height);

        background1 = setBitmap(R.drawable.fondo);
        background2 = setBitmap(R.drawable.clouds_2);

        src1 = new Rect(0,0, background1.getWidth(), background1.getHeight());
        src2 = new Rect(0,336, background2.getWidth(), background2.getHeight());
        src22 = new Rect(0,336, background2.getWidth(), background2.getHeight());
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        time += elapsedMillis;

        src2.left = (int) ((time/10) % background2.getWidth());
        src2.right = src2.left + background2.getWidth();
        src22.right = src2.left;
        src22.left = src22.right - background2.getWidth();
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(background2, src2, dst, null);
        canvas.drawBitmap(background2, src22, dst, null);
        canvas.drawBitmap(background1, src1, dst, null);

    }

    public Bitmap setBitmap(int resId) {
        Resources r = gameEngine.getContext().getResources();
        Drawable drawable = ResourcesCompat.getDrawable(r, resId, null);
        return drawable instanceof BitmapDrawable
                ? ((BitmapDrawable) drawable).getBitmap()
                : null;

    }


}
