package com.roshan.dev.model;

import android.content.Context;

import com.roshan.dev.beans.EventModel;
import com.roshan.dev.database.DatabaseHelper;

import java.util.List;

public class EventDataModel {

    public static final String TAG = EventDataModel.class.getSimpleName();

    /**
     * @param mContext
     * @return
     */
    public List<EventModel> getEventList(Context mContext) {
        return DatabaseHelper.getInstance(mContext).getEventList();
    }

    public void insertEvent(Context mContext, EventModel model) {
        DatabaseHelper.getInstance(mContext).insertEventDetails(model);
    }


    public void updateEvent(Context mContext, EventModel model) {
        DatabaseHelper.getInstance(mContext).updateEventDetails(model);
    }

    /**
     * @param mContext
     * @param eventId
     */
    public void removeEvent(Context mContext, int eventId) {
        DatabaseHelper.getInstance(mContext).deleteEvent(eventId);
    }


}