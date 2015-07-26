package edu.nyu.scps.JUL18;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * The SketchPadView draws various shapes on a canvas using an OnTouchListener
 */
public class SketchPadView extends View {

    private ArrayList<ShapeDrawable> imageList;
    private float scale;
    private String shape;
    private Paint paint;
    private ShapeDrawable cursorImage;
    private String drawType;

    public SketchPadView(Context context) {
        super(context);

        setListeners();
    }

    // used when constructing object from layout activity_main.xml file
    public SketchPadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        setListeners();
    }

    // set up listeners for SketchPadView object
    private void setListeners() {

        // initialize class variables
        imageList = new ArrayList<ShapeDrawable>();
        scale = 0.1f;
        shape = "Circle";
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        setPaintColor(Color.WHITE);
        cursorImage = null;
        drawType = "Stamp";

        // anonymous class because we're only creating one listener
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float size;
                // copy paint object before inserting into array (to avoid pointing to original which will change)
                Paint tmpPaint = new Paint(paint);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // create new point object

                        size = scale * Math.min(getWidth(), getHeight());

                        cursorImage = buildImage(event.getX(), event.getY(), size, shape, tmpPaint);
                        invalidate();    //call onDraw method of TouchView

                        return true;    //do nothing else

                    case MotionEvent.ACTION_UP:
                        if (cursorImage != null) {
                            imageList.add(cursorImage);
                        }
                        cursorImage = null;
                        invalidate();    //call onDraw method of TouchView

                        return false;    //do nothing else

                    case MotionEvent.ACTION_MOVE:
                        size = scale * Math.min(getWidth(), getHeight());

                        cursorImage = buildImage(event.getX(), event.getY(), size, shape, tmpPaint);
                        if (drawType.equals("Brush")) {
                            if (cursorImage != null) {
                                imageList.add(cursorImage);
                            }
                        }

                        invalidate();    //call onDraw method of TouchView

                        return true;    //do nothing else

                    default:
                        return false;
                }
            }
        });
    }

    // set size of shapr
    public void setSize(float scale) {
        this.scale = scale;
    }

    // set type of shape
    public void setShape(String shape) {
        this.shape = shape;
    }

    // set type of brush used to draw shape
    public void setDrawType(String drawType) {
        this.drawType = drawType;
    }

    // build drawable object depending on shape parameter
    private ShapeDrawable buildImage(float xCoor, float yCoor, float size, String shape, Paint color) {
        if (shape.equals("Circle")) {
            return new CircleDrawable(xCoor, yCoor, size, color);
        } else if (shape.equals("Square")) {
            return new SquareDrawable(xCoor, yCoor, size, color);
        } else if (shape.equals("Triangle")) {
            return new TriangleDrawable(xCoor, yCoor, size, color);
        } else if (shape.equals("Star")) {
            return new StarDrawable(xCoor, yCoor, size, color);
        } else {
            return null;
        }
    }

    // draw images using data from the SQLite database
    public void loadData(Helper helper, String fileName) {
        Cursor cursor = helper.getCursor(fileName);

        try {
            int columnXCoor = cursor.getColumnIndex("xcoor");
            int columnYCoor = cursor.getColumnIndex("ycoor");
            int columnSize = cursor.getColumnIndex("radius");
            int columnShape = cursor.getColumnIndex("shape");
            int columnColor = cursor.getColumnIndex("color");

            do {
                float xCoor = cursor.getFloat(columnXCoor);
                float yCoor = cursor.getFloat(columnYCoor);
                float size = cursor.getFloat(columnSize);
                String shape = cursor.getString(columnShape);
                int colorName = cursor.getInt(columnColor);
                Paint tmpPaint = new Paint();
                tmpPaint.setColor(colorName);

                ShapeDrawable storedImage = buildImage(xCoor, yCoor, size, shape, tmpPaint);
                if (storedImage != null) {
                    imageList.add(storedImage);
                }
            } while (cursor.moveToNext());

            invalidate();    //call onDraw method of TouchView
        } finally {
            cursor.close();
        }
    }

    // save images to SQLite database
    public void saveData(Helper helper, String fileName) {
        Cursor cursor = helper.getCursor(fileName);

        SQLiteDatabase database = helper.getWritableDatabase();

        // delete all previous shapes before saving
        database.execSQL("DELETE FROM " + helper.getShapeTableName());

        try {
            for (ShapeDrawable savedImage : imageList) {
                float xCoor = savedImage.getXCoor();
                float yCoor = savedImage.getYCoor();
                float size = savedImage.getSize();
                String shape = savedImage.getShape();
                Paint tmpPaint = savedImage.getPaint();
                int color = tmpPaint.getColor();

                ContentValues contentValues = new ContentValues();
                contentValues.put("save_name", fileName);
                contentValues.put("xcoor", xCoor);
                contentValues.put("ycoor", yCoor);
                contentValues.put("radius", size);
                contentValues.put("shape", shape);
                contentValues.put("color", color);

                database.insert(helper.getShapeTableName(), null, contentValues);
                //changeCursor(helper.getCursor());
            }
        } finally {
            cursor.close();
        }
    }

    // Set color of paintbrush
    public void setPaintColor(int paintColor) {
        paint.setColor(paintColor);
    }

    // Clear all drawable objects from SketchPad
    public void eraseSketchPad() {
        imageList.clear();
        invalidate();    //call onDraw method of TouchView
    }

    // draw all the shapes onto the canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);    //background

        // read each SketchPad image from the list and draw it
        for (ShapeDrawable savedImage : imageList) {
            savedImage.draw(canvas);
        }

        if (cursorImage != null) {
            cursorImage.draw(canvas);
        }
    }

}
