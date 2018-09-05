package edu.book.position.service;

import edu.book.position.dao.TradePositionDao;
import edu.book.position.dao.impl.TradePositionDaoImpl;
import edu.book.position.exception.TradeOrderException;
import edu.book.position.model.TradeOrder;
import edu.book.position.model.TradeOrderResponse;
import edu.book.position.validator.TradeOrderValidator;

import java.util.List;
import java.util.stream.Collectors;


public class TradePositionService {

    private TradeOrderValidator tradeOrderValidator = new TradeOrderValidator();
    private TradePositionDao tradePositionDao = new TradePositionDaoImpl();

    public TradeOrder executeTradeOrder(TradeOrder tradeOrder) throws TradeOrderException {
        tradeOrderValidator.validateTradeOrderModel(tradeOrder);

        final long tradeId = tradePositionDao.executeTradeOrder(tradeOrder);
        return tradePositionDao.getTradeDetailsByTradingId(tradeId);
    }

    public TradeOrderResponse getTradeOrderDetails(String accountId, String securityId) throws TradeOrderException {
        List<TradeOrder> totalTradeOrders = tradePositionDao.getTradeDetails(accountId, securityId);

        List<TradeOrder> filteredTradeOrder = totalTradeOrders.stream().filter(orders -> accountId.equals(orders.getAccountId()))
                .filter(order -> securityId.equals(order.getSecurityId()))
                .collect(Collectors.toList());


        Long buyOrder = calculateOrderQuantityByTradeType(filteredTradeOrder,"buy");
        Long sellOrder = calculateOrderQuantityByTradeType(filteredTradeOrder,"sell");

        Long remainingQuantity = buyOrder-sellOrder;
        return new TradeOrderResponse(remainingQuantity,filteredTradeOrder);
    }

    private Long calculateOrderQuantityByTradeType(List<TradeOrder> filteredTradeOrder,String tradeType) {
        return filteredTradeOrder.stream().filter(order->tradeType.equalsIgnoreCase(order.getTradeType())).collect(Collectors.summingLong(TradeOrder::getQuantity));
    }

    public TradeOrder cancel(long tradeId) throws TradeOrderException {
        tradeOrderValidator.validateTradeId(tradeId);
        tradePositionDao.updateTradeTypeByTradeId(tradeId);

        return tradePositionDao.getTradeDetailsByTradingId(tradeId);
    }
}
