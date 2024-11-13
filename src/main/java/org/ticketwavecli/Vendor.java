package org.ticketwavecli;

import org.apache.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Vendor implements Runnable {
    private final int vendorID;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;
    private final Ticket tickets;
    private final Lock lock = new ReentrantLock();
    private static final Logger logger = Logger.getLogger(Vendor.class);

    public Vendor(int vendorID, TicketPool ticketPool, int ticketReleaseRate, Ticket tickets) {
        this.vendorID = vendorID;
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.tickets = tickets;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                int remainingTickets = ticketPool.getRemainingTickets();

                if (remainingTickets <= 0) {
                    logger.info("Vendor " + vendorID + ": No more tickets to release.");
                    break; // Exit the loop if no tickets are left to release
                }

                int ticketsToRelease = Math.min(ticketReleaseRate, remainingTickets); // stop release more than remaining tickets
                logger.info("Vendor " + vendorID + " releasing " + ticketsToRelease + " ticket(s).");
                ticketPool.addTicket(vendorID, ticketsToRelease, tickets);

                Thread.sleep(3000); // Simulate delay
            } catch (InterruptedException e) {
                logger.error("Vendor " + vendorID + " interrupted.");
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
        logger.info("Vendor " + vendorID + ": Finished releasing tickets.");
    }
}
