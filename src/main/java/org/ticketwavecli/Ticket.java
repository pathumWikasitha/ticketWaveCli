package org.ticketwavecli;

import java.util.Date;

public class Ticket {
    private int eventID;
    private String eventName;
    private String eventType;
    private Date eventDate;

    public Ticket(int eventID, String eventName, String eventType, Date eventDate) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
    }

    public int getTicketId() {
        return eventID;
    }

    public void setTicketId(int ticketId) {
        this.eventID = ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
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
