package com.roshan.dev.beans;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.databinding.library.baseAdapters.BR;

public class EventModel extends BaseObservable implements Parcelable {
    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };
    private int eventId;
    private String eventName;
    private String eventDescription;
    private boolean isCreateEvent;

    public EventModel() {

    }

    protected EventModel(Parcel in) {
        eventId = in.readInt();
        eventName = in.readString();
        eventDescription = in.readString();
        isCreateEvent = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventId);
        dest.writeString(eventName);
        dest.writeString(eventDescription);
        dest.writeByte((byte) (isCreateEvent ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Bindable
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
        notifyPropertyChanged(BR.eventName);
    }

    @Bindable
    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
        notifyPropertyChanged(BR.eventDescription);
    }

    public boolean isCreateEvent() {
        return isCreateEvent;
    }

    public void setIsCreateEvent(boolean createEvent) {
        isCreateEvent = createEvent;
    }


}
