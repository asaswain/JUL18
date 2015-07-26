package edu.nyu.scps.JUL18;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class stores the methods for creating the SQLite database and for selecting data to load/update in the SQLite database
 */

public class Helper extends SQLiteOpenHelper {

    private String databaseName;
    private final String shapeTableName = "saved_shapes";

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    public String getShapeTableName() {
        return shapeTableName;
    }

    public Helper(Context context, String databaseName) {
        super(context, databaseName, null, 2);
        this.databaseName = databaseName;
    }

    // this method only fires when the database is missing
    @Override
    public void onCreate(SQLiteDatabase db) {

        String[] statements = {
                "CREATE TABLE " + shapeTableName + " ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "save_name TEXT,"
                        + "xcoor REAL,"
                        + "ycoor REAL,"
                        + "radius REAL,"
                        + "shape TEXT,"
                        + "color TEXT"
                        + ");",
        };

        for (String statement: statements) {
            Log.d("sql", "" + statement);
            db.execSQL(statement);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // This method gets the cursor for loading/saving data
    public Cursor getCursor(String fileName) {
        SQLiteDatabase db = getReadableDatabase(); // the db passed to onCreate
        //can say "_id, name" instead of "*", but _id must be included.
        Cursor cursor = db.rawQuery("SELECT * FROM " + shapeTableName + " WHERE save_name = '" + fileName + "' ORDER BY _id;", null);
        cursor.moveToFirst();
        return cursor;
    }
}
