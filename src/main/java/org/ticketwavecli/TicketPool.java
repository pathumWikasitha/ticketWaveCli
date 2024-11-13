package org.ticketwavecli;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final int maxTicketCapacity;
    private int remainingTickets;
    private final List<Ticket> synTicketList;
    private static final Logger logger = Logger.getLogger(TicketPool.class);


    public TicketPool(int maxTicketCapacity, int totalTickets) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.synTicketList = Collections.synchronizedList(new ArrayList<>());
        this.remainingTickets = totalTickets;
    }

    public synchronized void addTicket(int vendorID, int ticketReleaseRate, Ticket ticket) throws InterruptedException {
        if (remainingTickets <= 0) {
            logger.info("Vendor " + vendorID + " : No more tickets to release.");
            return;
        }
        int ticketsToRelease = Math.min(ticketReleaseRate, remainingTickets);

        while (synTicketList.size() + ticketReleaseRate > maxTicketCapacity) {
            logger.info("Ticket Pool is full. Vendor" + vendorID + " is waiting.");
            wait();
        }

        for (int i = 0; i < ticketReleaseRate; i++) {
            synTicketList.add(ticket);
        }
        remainingTickets -= ticketsToRelease;
        logger.info("Vendor " + vendorID + " : " + ticketReleaseRate + " Tickets released. remainingTickets: " + remainingTickets);
        notifyAll();

    }

    public synchronized void purchaseTicket(int customerID) throws InterruptedException {
        while (synTicketList.isEmpty()) {
            if (remainingTickets <= 0) {
                logger.info("Customer " + customerID + " : No more tickets to purchase.");
                return;
            }
            logger.info("Customer" + customerID + " Waiting for tickets to purchase...");
            wait();
        }
        Ticket ticket = synTicketList.removeFirst();
        logger.info("Customer" + customerID + " :Ticket purchased: " + ticket.toString());
        notifyAll();
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

}
