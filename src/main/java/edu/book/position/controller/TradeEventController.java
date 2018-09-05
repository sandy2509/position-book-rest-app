package edu.book.position.controller;

import edu.book.position.exception.TradeOrderException;
import edu.book.position.model.TradeOrder;
import edu.book.position.model.TradeOrderResponse;
import edu.book.position.service.TradePositionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static edu.book.position.constants.RestApiPathConstants.ORDER_API;
import static edu.book.position.constants.RestApiPathConstants.TRADE_EVENTS_API;

@Path(TRADE_EVENTS_API)
@Produces(MediaType.APPLICATION_JSON)
public class TradeEventController {

    private TradePositionService tradePositionService = new TradePositionService();

    /**
     * Executes an order
     *
     * @param tradeOrder
     * @return Response
     * @throws TradeOrderException
     */
    @POST
    @Path(ORDER_API)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response executeBuyOrder(TradeOrder tradeOrder) throws TradeOrderException {
        TradeOrder createdTradeOrder = tradePositionService.executeTradeOrder(tradeOrder);

        return Response.status(Response.Status.CREATED)
                .entity(createdTradeOrder)
                .build();
    }

    /**
     * Cancel existing previously issued
     * event by trade Id
     *
     * @param tradeId
     * @return Response
     * @throws TradeOrderException
     */
    @PUT
    @Path("/{tradeId}/cancel")
    public Response cancel(@PathParam("tradeId") long tradeId) throws TradeOrderException {

        TradeOrder tradeOrder = tradePositionService.cancel(tradeId);

        return Response.status(Response.Status.OK)
                .entity(tradeOrder)
                .build();
    }

    /**
     * GET by Trading AccountId
     * and SecurityId
     *
     * @param accountId
     * @param securityId
     * @return Response
     * @throws TradeOrderException
     */
    @GET
    @Path("/order/{accountId}/{securityId}")
    public Response getTradeOrderDetails(@PathParam("accountId") String accountId, @PathParam("securityId") String securityId) throws TradeOrderException {
        TradeOrderResponse tradeOrderResponse = tradePositionService.getTradeOrderDetails(accountId, securityId);

        return Response.status(Response.Status.OK)
                .entity(tradeOrderResponse)
                .build();
    }
}