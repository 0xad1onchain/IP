package com.icucs.ip;

/**
 * Created by Adi on 19/04/17.
 */

public class EventRegisterData {
    public String job, eventName, uid, eventID;

    public EventRegisterData()
    {}

    public EventRegisterData(String job, String eventName, String uid, String eventID) {
        this.job = job;
        this.eventName = eventName;
        this.uid = uid;
        this.eventID = eventID;
    }

    public String getJob() {
        return job;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUid() {
        return uid;
    }

    public String getEventID() {
        return eventID;
    }
}
