package fpt.edu.com.food.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fpt.edu.com.food.Contrants;

public class DatabaseHelper extends SQLiteOpenHelper implements Contrants {
    public DatabaseHelper(Context context) {
        super(context, "ORDER_DETAIL", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ORDER_DETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAIL);
        onCreate(db);
    }
}
