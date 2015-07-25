package edu.nyu.scps.JUL18;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;

/**
 * This class draws a circle on the canvas
 */
public class CircleDrawable extends ShapeDrawable {

    public CircleDrawable(float x, float y, float radius, Paint paint) {
        setShape("Circle");
        setColor(paint);
        setCenter(new PointF(x, y));
        setSize(radius);
        int b = (int) radius;
        setBounds(-b, b, b, -b); //left, top, right, bottom
    }

    @Override
    public void draw(Canvas canvas) {
        Path path = new Path();

        canvas.drawCircle(getCenter().x, getCenter().y, getSize(), getColor());
    }

    @Override
    public void setAlpha(int alpha) {
        getColor().setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        getColor().setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

}
