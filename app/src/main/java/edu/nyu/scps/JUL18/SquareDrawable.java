package edu.nyu.scps.JUL18;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;

/**
 * This class draws a square on the canvas
 */
public class SquareDrawable extends ShapeDrawable {

    public SquareDrawable(float x, float y, float radius, Paint paint) {
        setShape("Square");
        SetColor(paint);
        setCenter(new PointF(x, y));
        setSize(radius);
        int b = (int) radius;
        setBounds(-b, b, b, -b); //left, top, right, bottom
    }

    @Override
    public void draw(Canvas canvas) {
        /*
        Assume X axis points right, Y axis points up.
        */

        float minX = getCenter().x - getSize();
        float minY = getCenter().y - getSize();

        float maxX = getCenter().x + getSize();
        float maxY = getCenter().y + getSize();

        canvas.drawRect(minX, minY, maxX, maxY, getPaint());
    }

    @Override
    public void setAlpha(int alpha) {
        getPaint().setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        getPaint().setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

}

