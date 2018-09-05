package edu.book.position;

import edu.book.position.controller.TradeEventController;
import edu.book.position.exception.GenericExceptionMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface InMemoryServer {

    Logger LOGGER = LoggerFactory.getLogger(InMemoryServer.class);

    int SERVER_PORT = 8080;
    String COMMA_DELIMETER = ",";
    String CONTEXT_ROOT = "/";
    String RELATIVE_CONTEXT_PATH = "/*";
    String REGISTER_JERSEY_RESOURCE_AND_PROVIDER = "jersey.config.server.provider.classnames";

    static void run() throws Exception {
        Server inMemoryJettyServer = new Server(SERVER_PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(CONTEXT_ROOT);
        inMemoryJettyServer.setHandler(context);

        ServletHolder servletHolder = context.addServlet(ServletContainer.class, RELATIVE_CONTEXT_PATH);

        servletHolder.setInitParameter(REGISTER_JERSEY_RESOURCE_AND_PROVIDER,
                String.join(COMMA_DELIMETER, TradeEventController.class.getCanonicalName(), GenericExceptionMapper.class.getCanonicalName()));
        try {
            inMemoryJettyServer.start();
            LOGGER.info("In-memory Server Started successfully");
            inMemoryJettyServer.join();
        } finally {
            LOGGER.info("Stopping in-memory server.");
            inMemoryJettyServer.destroy();
            LOGGER.info("Server stopped successfully.");
        }
    }
}
