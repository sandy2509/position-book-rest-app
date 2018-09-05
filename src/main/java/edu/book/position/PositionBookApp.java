package edu.book.position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static edu.book.position.DatabaseFactory.*;
import static edu.book.position.InMemoryServer.*;

/**
 * @author Sandeep Das
 * This Application will enable the Position Book
 * System
 */
public class PositionBookApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionBookApp.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Instantiate and Initialize DB with sample trade data.");
        buildDbWithSampleData(DatabaseVendorType.H2);
        LOGGER.info("DB Initialisation Complete.");
        run();
    }
}

