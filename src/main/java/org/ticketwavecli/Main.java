package org.ticketwavecli;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static boolean run = false;
    private static final List<Thread> vendors = new ArrayList<>();
    private static final List<Thread> customers = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        ticketWaveCli ticketWaveCli = new ticketWaveCli();
        Configuration configuration;

        logger.info("Welcome to the Ticket Wave CLI");

        while (true) {

            System.out.print("\nEnter command (start/stop/exit): ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("start")) {

                if (!run) {
                    run = true;
                    logger.info("Starting the ticketing system...");

                    System.out.print("\nDo you want to load an existing configuration file? (Y/N): ");
                    String load = scanner.nextLine();

                    if (load.equalsIgnoreCase("Y")) {
                        configuration = ticketWaveCli.loadConfigurationFile();
                    } else if (load.equalsIgnoreCase("N")) {
                        configuration = ticketWaveCli.newConfiguration();
                    } else {
                        logger.warn("Invalid input for loading configuration.");
                        System.out.println("Invalid input. Try again.");
                        continue;
                    }

                    TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets());

                    // Start vendors
                    for (int i = 0; i < 3; i++) {
                        Vendor vendor = new Vendor(i + 1, ticketPool, configuration.getTicketReleaseRate(), new Ticket(100, "Aluth Kalawak", "Musical", new Date()));
                        Thread vendorThread = new Thread(vendor);
                        vendors.add(vendorThread);
                        vendorThread.start();
                    }

                    // Start customers
                    for (int i = 0; i < 3; i++) {
                        Customer customer = new Customer(i + 1, ticketPool, configuration.getCustomerRetrievalRate());
                        Thread customerThread = new Thread(customer);
                        customers.add(customerThread);
                        customerThread.start();
                    }

                    // Wait for vendors and customers to finish before allowing new input
                    for (Thread vendor : vendors) {
                        vendor.join();
                    }

                    for (Thread customer : customers) {
                        customer.join();
                    }

                    logger.info("Ticketing Simulation completed.");
                    System.out.println("Ticketing Simulation completed.");

                } else {
                    logger.warn("Trying to start the system when system already running.");
                    System.out.println("The ticketing system is already running.");
                }

            } else if (command.equalsIgnoreCase("stop")) {

                if (run) {
                    run = false;
                    logger.info("TicketWaveCLI stopping...");

                    for (Thread thread : vendors) {
                        thread.interrupt();
                    }
                    for (Thread thread : customers) {
                        thread.interrupt();
                    }

                    vendors.clear();
                    customers.clear();

                    logger.info("TicketWaveCLI stopped.");
                    System.out.println("TicketWaveCLI stopped.");
                } else {
                    logger.warn("TicketWaveCLI not running.");
                    System.out.println("TicketWaveCLI not running.");
                }

            } else if (command.equalsIgnoreCase("exit")) {
                run = false;
                logger.info("TicketWaveCli exiting...");
                System.out.println("TicketWaveCli exiting...");
                break;

            } else {
                logger.warn("Invalid command entered: " + command);
                System.out.println("Invalid command. Try again.");
            }
        }

        System.out.println("TicketWaveCLI Ended.");
        logger.info("TicketWaveCli ended.");
    }
}
