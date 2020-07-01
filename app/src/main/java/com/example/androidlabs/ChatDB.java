package com.example.androidlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class ChatDB extends SQLiteOpenHelper {

    protected final static String DBNAME = "lab5_chat";

    public final static String  TABLE_MESSAGES = "messages",
                                COL_MESSAGE = "message",
                                COL_SENT = "sent",
                                COL_ID = "_id";

    protected final static int VERSION_NUM = 1;

    //Constructor
    public ChatDB(Context ctx)
    {
        super(ctx, DBNAME, null, VERSION_NUM);
    }

    //This function gets called if no database file exists.
    //Look on your device in the /data/data/package-name/database directory.
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_MESSAGES + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MESSAGE + " VARCHAR(255),"
                + COL_SENT  + " TINYINT(1));");  // add or remove columns

    }

    //this function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_MESSAGES);

        //Create the new table:
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {   //Drop the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

        //Create the new table:
        onCreate(db);
    }

}
