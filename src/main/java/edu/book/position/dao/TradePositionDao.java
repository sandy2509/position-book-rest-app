package edu.book.position.dao;


import edu.book.position.exception.TradeOrderException;
import edu.book.position.model.TradeOrder;

import java.util.List;


public interface TradePositionDao {

    long executeTradeOrder(TradeOrder tradeOrder) throws TradeOrderException;

    TradeOrder getTradeDetailsByTradingId(long tradeId) throws TradeOrderException;

    int updateTradeTypeByTradeId(long tradeId) throws TradeOrderException;

    List<TradeOrder> getTradeDetails(String accountId, String securityId) throws TradeOrderException;
}
