package com.roshan.dev.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.roshan.dev.beans.EventModel;

import java.util.ArrayList;
import java.util.List;

import static com.roshan.dev.database.EventsTable.TABLE_NAME;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "events_db";
    private static DatabaseHelper mInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param ctx
     * @return
     */
    public static DatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // **** CRUD (Create, Read, Update, Delete) Operations ***** //
    // Adding new Events Details
    public void insertEventDetails(EventModel eventModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(EventsTable.COLUMN_EVENT_NAME, eventModel.getEventName());
        cValues.put(EventsTable.COLUMN_EVENT_DESCRIPTION, eventModel.getEventDescription());
        long newRowId = db.insert(TABLE_NAME, null, cValues);
        db.close();
    }

    // Get Event List
    public List<EventModel> getEventList() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<EventModel> eventsList = new ArrayList<>();
        String query = "SELECT * FROM " + EventsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            EventModel eventModel = new EventModel();
            eventModel.setEventId(cursor.getInt(cursor.getColumnIndex(EventsTable.COLUMN_ID)));
            eventModel.setEventName(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_EVENT_NAME)));
            eventModel.setEventDescription(cursor.getString(cursor.getColumnIndex(EventsTable.COLUMN_EVENT_DESCRIPTION)));
            eventsList.add(eventModel);
        }
        return eventsList;
    }

    // Delete Event
    public void deleteEvent(int eventRowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EventsTable.TABLE_NAME, EventsTable.COLUMN_ID + " = ?", new String[]{String.valueOf(eventRowId)});
        db.close();
    }

    // Update Event
    public int updateEventDetails(EventModel eventModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(EventsTable.COLUMN_EVENT_NAME, eventModel.getEventName());
        cValues.put(EventsTable.COLUMN_EVENT_DESCRIPTION, eventModel.getEventDescription());
        int count = db.update(EventsTable.TABLE_NAME, cValues, EventsTable.COLUMN_ID + " = ?", new String[]{String.valueOf(eventModel.getEventId())});
        return count;
    }
}