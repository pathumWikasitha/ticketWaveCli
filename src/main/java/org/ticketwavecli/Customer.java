package org.ticketwavecli;

import org.apache.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer implements Runnable {
    private final int customerId;
    private final int retrievalRate;
    private final TicketPool ticketPool;
    private final Lock lock = new ReentrantLock();
    private static final Logger logger = Logger.getLogger(Customer.class);

    public Customer(int customerId, TicketPool ticketPool, int retrievalRate) {
        this.customerId = customerId;
        this.retrievalRate = retrievalRate;
        this.ticketPool = ticketPool;
    }


    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                int remainingTickets = ticketPool.getRemainingTickets();

                if (remainingTickets <= 0) {
                    logger.info("Customer " + customerId + " : No more tickets available to purchase.");
                    break; // Exit if no tickets are available to purchase.
                }

                int ticketsToPurchase = Math.min(retrievalRate, remainingTickets); // if remaining tickets less than customer retrieval rate. allow customer to purchase remaining tickets.
                logger.info("Customer " + customerId + " : No more tickets available to purchase.");
                for (int i = 0; i < ticketsToPurchase; i++) {
                    ticketPool.purchaseTicket(customerId);
                }
                logger.info("Customer " + customerId + " successfully purchased " + ticketsToPurchase + " ticket(s).");
                Thread.sleep(3000); // delay between purchases
            } catch (InterruptedException e) {
                logger.error("Customer " + customerId + " interrupted.");
                Thread.currentThread().interrupt();
                break; // Exit on interruption
            } finally {
                lock.unlock();
            }
        }
        logger.info("Customer " + customerId + ": Finished purchasing tickets.");
        Thread.currentThread().interrupt();
    }
}