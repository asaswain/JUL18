package edu.nyu.scps.JUL18;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

/**
 * This abstract class stores all the methods for setting the data for the various shapes we want to draw on the canvas
 */
public abstract class ShapeDrawable extends Drawable {

    private PointF center;
    private float size;
    private String shape;
    private Paint color;

    public PointF getCenter() {
        return center;
    }

    public float getXCoor() {
        return center.x;
    }

    public float getYCoor() {
        return center.y;
    }

    public float getSize() {
        return size;
    }

    public Paint getColor() {
        return color;
    }

    public String getShape() {
        return shape;
    }

    public void setCenter(PointF center) {
        this.center = center;
    }

    public void setXCoor(float xCoor) {
        this.center.x = xCoor;
    }

    public void setYCoor(float yCoor) {
        this.center.y = yCoor;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    abstract public void draw(Canvas canvas);
}
