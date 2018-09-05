package edu.book.position.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.book.position.DatabaseFactory;
import edu.book.position.DatabaseVendorType;
import edu.book.position.controller.TradeEventController;
import edu.book.position.exception.GenericExceptionMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public abstract class TestSetupService {
    private static final int TEST_SERVER_PORT = 8081;
    private static final String TEST_HOST = "localhost";
    private static final String COLON_DELIMITER = ":";
    private static Server server = null;
    private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    public static HttpClient client;
    public ObjectMapper mapper = new ObjectMapper();
    public URIBuilder builder = new URIBuilder().setScheme("http").setHost(String.join(COLON_DELIMITER, TEST_HOST, String.valueOf(TEST_SERVER_PORT)));


    @BeforeClass
    public static void setup() throws Exception {
        DatabaseFactory.buildDbWithSampleData(DatabaseVendorType.H2);
        startTestServer();
        connManager.setDefaultMaxPerRoute(100);
        connManager.setMaxTotal(200);
        client = HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();

    }

    @AfterClass
    public static void closeClient() throws Exception {
        HttpClientUtils.closeQuietly(client);
    }


    private static void startTestServer() throws Exception {
        if (server == null) {
            server = new Server(TEST_SERVER_PORT);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
            servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                    TradeEventController.class.getCanonicalName() + "," +
                            GenericExceptionMapper.class.getCanonicalName());
            server.start();
        }
    }
}