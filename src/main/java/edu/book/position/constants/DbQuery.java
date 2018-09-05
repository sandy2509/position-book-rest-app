package edu.book.position.constants;

public class DbQuery {
    private DbQuery(){}

    public static final String GET_TRADE_DETAILS_BY_ID = "SELECT * FROM TradeOrder WHERE TradeId = ? ";
    public static final String LOCK_TRADE_ORDER_BY_ID = "SELECT * FROM TradeOrder WHERE TradeId = ? FOR UPDATE";
    public static final String CREATE_TRADE_ORDER = "INSERT INTO TradeOrder (AccountId, Quantity, SecurityId,TradeType) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_TRADE_TYPE = "UPDATE TradeOrder SET TradeType = ? WHERE TradeId = ? ";
    public static final String GET_TRADE_ORDER = "SELECT * FROM TradeOrder";
}
