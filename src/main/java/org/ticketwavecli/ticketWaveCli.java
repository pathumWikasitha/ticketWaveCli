package org.ticketwavecli;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class ticketWaveCli {
    Scanner scanner = new Scanner(System.in);
    Configuration configuration = new Configuration();
    private static final Logger logger = Logger.getLogger(ticketWaveCli.class);

    public Configuration loadConfigurationFile() {
        Configuration config = Configuration.loadFromFile();
        if (config != null) {
            logger.info("Configuration file loaded successfully.");
            this.configuration = config;
            return config;
        } else {
            logger.error("Failed to load configuration file.");
            return null;
        }
    }

    public Configuration newConfiguration() {
        logger.info("Starting new configuration creation.");

        configuration.setTotalTickets(getValidInput(scanner));
        logger.info("Set Total Tickets to " + configuration.getTotalTickets());

        configuration.setTicketReleaseRate(getValidInput(scanner, "Enter Ticket Release Rate. Tickets per second :", configuration.getTotalTickets()));
        logger.info("Set Ticket Release Rate to " + configuration.getTicketReleaseRate());

        configuration.setCustomerRetrievalRate(getValidInput(scanner, "Enter Customer Retrieval Rate. Tickets per second :", configuration.getTotalTickets()));
        logger.info("Set Customer Retrieval Rate to " + configuration.getCustomerRetrievalRate());

        configuration.setMaxTicketCapacity(getValidInput(scanner, "Enter Max Ticket Capacity :", configuration.getTotalTickets()));
        logger.info("Set Max Ticket Capacity to " + configuration.getMaxTicketCapacity());

        configuration.save();
        logger.info("Configuration file saved successfully.");

        return configuration;
    }

    private int getValidInput(Scanner scanner) {
        int input;
        while (true) {
            System.out.print("Enter Total Tickets Count :");
            try {
                input = Integer.parseInt(scanner.next());
                if (input > 0) {
                    return input;
                }
                System.out.println("Enter Positive Number");
                logger.warn("Enter Positive Number");

            } catch (Exception e) {
                System.out.println("Invalid Input. Try again.");
                logger.error("Error while entering Total Tickets: " + e.getMessage());
            }

        }

    }

    private int getValidInput(Scanner scanner, String s, int max) {
        int input;
        while (true) {
            System.out.print(s);
            try {
                input = Integer.parseInt(scanner.next());
                if (input >= 1 && input <= max) {
                    return input;
                }
                System.out.println("Invalid input. Enter a number between " + 1 + " and " + max);
                logger.warn("Invalid input: " + input + " Enter a number between " + 1 + " and " + max);

            } catch (Exception e) {
                System.out.println("Invalid Input. Try again.");
                logger.error("Error while entering input:: " + e.getMessage());
            }
        }

    }
}
