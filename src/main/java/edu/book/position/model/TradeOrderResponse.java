package edu.book.position.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TradeOrderResponse {
    @JsonProperty(required = true)
    private Long quantity;
    @JsonProperty(required = true)
    private List<TradeOrder> tradeOrder;

    public TradeOrderResponse(){}

    public TradeOrderResponse(Long quantity, List<TradeOrder> tradeOrder) {
        this.quantity = quantity;
        this.tradeOrder = tradeOrder;
    }

    public Long getQuantity() {
        return quantity;
    }

    public List<TradeOrder> getTradeOrder() {
        return tradeOrder;
    }
}
