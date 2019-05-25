package com.roshan.dev.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.roshan.dev.beans.EventModel;
import com.roshan.dev.model.EventDataModel;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {

    private EventDataModel dataModel;

    public EventViewModel() {
        dataModel = new EventDataModel();
    }

    /**
     * @param mContext
     * @return
     */
    public List<EventModel> getEventsList(Context mContext) {
        return new ArrayList<>(dataModel.getEventList(mContext));
    }

    public void insertEvent(Context mContext, EventModel model) {
        dataModel.insertEvent(mContext, model);
    }

    public void updateEvent(Context mContext, EventModel model) {
        dataModel.updateEvent(mContext, model);
    }

    /**
     * @param mContext
     * @param eventRowId
     */
    public void deleteEvent(Context mContext, int eventRowId) {
        dataModel.removeEvent(mContext, eventRowId);
    }


}