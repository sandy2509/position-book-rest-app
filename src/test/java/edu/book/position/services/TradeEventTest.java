package edu.book.position.services;

import edu.book.position.model.TradeOrder;
import edu.book.position.model.TradeOrderResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TradeEventTest extends TestSetupService {

    @Test
    public void shouldCreateBuyOrder() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/events/order").build();
        TradeOrder tradeOrder = new TradeOrder("SEC2","ACC2",100,"buy");
        String jsonInString = mapper.writeValueAsString(tradeOrder);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPost request = new HttpPost(uri);
        request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == Response.Status.CREATED.getStatusCode());
        String jsonString = EntityUtils.toString(response.getEntity());
        TradeOrder responseBody = mapper.readValue(jsonString, TradeOrder.class);
        assertEquals("SEC2",responseBody.getSecurityId());
        assertEquals("ACC2",responseBody.getAccountId());
        assertNotNull(responseBody.getTradeId());
    }

    @Test
    public void shouldCreateSellOrder() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/events/order").build();
        TradeOrder tradeOrder = new TradeOrder("SEC3","ACC3",100,"sell");
        String jsonInString = mapper.writeValueAsString(tradeOrder);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPost request = new HttpPost(uri);
        request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == Response.Status.CREATED.getStatusCode());
        String jsonString = EntityUtils.toString(response.getEntity());
        TradeOrder responseBody = mapper.readValue(jsonString, TradeOrder.class);
        assertEquals("SEC3",responseBody.getSecurityId());
        assertEquals("ACC3",responseBody.getAccountId());
        assertNotNull(responseBody.getTradeId());
    }

    @Test
    public void shouldCancelExistingOrder() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/events/1/cancel").build();
        HttpPut request = new HttpPut(uri);
        request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == Response.Status.OK.getStatusCode());
        String jsonString = EntityUtils.toString(response.getEntity());
        TradeOrder responseBody = mapper.readValue(jsonString, TradeOrder.class);
        assertEquals("SEC1",responseBody.getSecurityId());
        assertEquals("ACC1",responseBody.getAccountId());
        assertEquals("cancel",responseBody.getTradeType());
    }

    @Test
    public void shouldGetTradeOrderFilterByAccountIdAndSecurityID() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/events/order/ACC1/SEC1").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == Response.Status.OK.getStatusCode());
        String jsonString = EntityUtils.toString(response.getEntity());
        TradeOrderResponse tradeOrderResponse = mapper.readValue(jsonString, TradeOrderResponse.class);
        assertEquals(100L,tradeOrderResponse.getQuantity().longValue());
    }

    @Test
    public void shouldCalculateTotalQuantity() throws IOException, URISyntaxException {


        URI uri = builder.setPath("/events/order/ACC1/SEC1").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == Response.Status.OK.getStatusCode());
        String jsonString = EntityUtils.toString(response.getEntity());
        TradeOrderResponse tradeOrderResponse = mapper.readValue(jsonString, TradeOrderResponse.class);
        assertEquals(0L,tradeOrderResponse.getQuantity().longValue());

    }
}