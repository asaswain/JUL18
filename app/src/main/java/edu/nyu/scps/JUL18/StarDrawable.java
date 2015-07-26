package edu.nyu.scps.JUL18;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;

/**
 * This class draws a 6 pointed star on the canvas (simply drawing two triangles inverted on each other)
 */
public class StarDrawable extends ShapeDrawable {

    public StarDrawable(float x, float y, float radius, Paint paint) {
        setShape("Star");
        SetColor(paint);
        setCenter(new PointF(x, y));
        setSize(radius);
        int b = (int)radius;
        setBounds(-b, b, b, -b); //left, top, right, bottom
    }

    @Override
    public void draw(Canvas canvas) {
        int n = 3;	//number of vertices; try 5 for a pentagon
        Path path = new Path();

        /*
        Assume X axis points right, Y axis points up.
        Angles are measured counterclockwise from 3 o'clock.
        First vertex points up (to 90 degrees).
        Second vertex points to lower left (to 210 degrees).
        Third vertex points to lower right (to 330 degrees).
        */
        for (int i = 0; i < n; ++i) {
            float degrees = i * 360 / n - 90;
            float theta = (float)Math.toRadians(degrees); //convert degrees to radians
            float x = getCenter().x + getSize() * (float)Math.cos(theta);
            float y = getCenter().y + getSize() * (float)Math.sin(theta);

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        path.close();
        canvas.drawPath(path, getPaint());

        /*
        Assume X axis points right, Y axis points up.
        Angles are measured counterclockwise from 3 o'clock.
        First vertex points up (to 90 degrees).
        Second vertex points to lower left (to 210 degrees).
        Third vertex points to lower right (to 330 degrees).
        */
        for (int i = 0; i < n; ++i) {
            float degrees = i * 360 / n - 90;
            float theta = (float)Math.toRadians(degrees); //convert degrees to radians
            float x = getCenter().x + getSize() * (float)Math.cos(theta);
            float y = getCenter().y + getSize() * (float)Math.sin(theta) * -1;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        path.close();
        canvas.drawPath(path, getPaint());
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
