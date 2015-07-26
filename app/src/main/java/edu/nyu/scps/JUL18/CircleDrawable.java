package edu.nyu.scps.JUL18;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;

/**
 * This class draws a circle on the canvas
 */
public class CircleDrawable extends ShapeDrawable {

    public CircleDrawable(float x, float y, float radius, Paint paint) {
        setShape("Circle");
        SetColor(paint);
        setCenter(new PointF(x, y));
        setSize(radius);
        int b = (int) radius;
        setBounds(-b, b, b, -b); //left, top, right, bottom
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(getCenter().x, getCenter().y, getSize(), getPaint());
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
