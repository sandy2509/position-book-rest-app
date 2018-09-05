package edu.book.position.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeOrder {

    @JsonIgnore
    private long tradeId;

    @JsonProperty(required = true)
    private String securityId;

    @JsonProperty(required = true)
    private String accountId;

    @JsonProperty(required = true)
    private long quantity;

    @JsonProperty(required = true)
    private String tradeType;


    public TradeOrder() {
    }

    public TradeOrder(String securityId, String accountId, long quantity,String tradeType) {
        this.securityId = securityId;
        this.accountId = accountId;
        this.quantity = quantity;
        this.tradeType = tradeType;
    }


    public TradeOrder(long tradeId, String securityId, String accountId, long quantity,String tradeType) {
        this.tradeId = tradeId;
        this.securityId = securityId;
        this.accountId = accountId;
        this.quantity = quantity;
        this.tradeType = tradeType;
    }

    @JsonProperty
    public long getTradeId() {
        return tradeId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public String getAccountId() {
        return accountId;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getTradeType() {
        return tradeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeOrder that = (TradeOrder) o;
        return tradeId == that.tradeId &&
                quantity == that.quantity &&
                Objects.equals(securityId, that.securityId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(tradeType, that.tradeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, securityId, accountId, quantity, tradeType);
    }

    @Override
    public String toString() {
        return "TradeOrder{" +
                "tradeId=" + tradeId +
                ", securityId='" + securityId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", quantity=" + quantity +
                ", tradeType='" + tradeType + '\'' +
                '}';
    }
}

