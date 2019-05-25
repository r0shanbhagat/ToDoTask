package com.roshan.dev.database;

public interface EventsTable {
    String TABLE_NAME = "events"; //Table Name

    String COLUMN_ID = "id";
    String COLUMN_EVENT_NAME = "eventName";
    String COLUMN_EVENT_DESCRIPTION = "eventDescription";

    // Create table SQL query
    String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EVENT_NAME + " TEXT,"
                    + COLUMN_EVENT_DESCRIPTION + " TEXT"
                    + ")";

}