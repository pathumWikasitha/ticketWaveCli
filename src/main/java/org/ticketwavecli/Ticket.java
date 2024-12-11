package org.ticketwavecli;

import java.util.Date;

public class Ticket {
    private final int eventID;
    private final String eventName;
    private final String eventType;
    private final Date eventDate;

    public Ticket(int eventID, String eventName, String eventType, Date eventDate) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "Event ID=" + eventID +
                ", Event Name='" + eventName + '\'' +
                ", Event Type='" + eventType + '\'' +
                ", Event Date=" + eventDate +
                '}';
    }


}
