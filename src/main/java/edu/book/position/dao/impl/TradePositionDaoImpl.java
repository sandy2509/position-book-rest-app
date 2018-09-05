package edu.book.position.dao.impl;

import edu.book.position.dao.TradePositionDao;
import edu.book.position.exception.TradeOrderException;
import edu.book.position.model.TradeOrder;
import edu.book.position.vendor.H2Database;
import org.apache.commons.dbutils.DbUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static edu.book.position.constants.DbQuery.*;


public class TradePositionDaoImpl implements TradePositionDao {

    private static Logger LOGGER = LoggerFactory.getLogger(TradePositionDaoImpl.class);

    public long executeTradeOrder(TradeOrder tradeOrder) throws TradeOrderException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = H2Database.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_TRADE_ORDER);
            preparedStatement.setString(1, tradeOrder.getAccountId());
            preparedStatement.setLong(2, tradeOrder.getQuantity());
            preparedStatement.setString(3, tradeOrder.getSecurityId());
            preparedStatement.setString(4, tradeOrder.getTradeType());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.error("Error executing Buy Order event. Trading Id cannot be generated for trade Order [{}]", tradeOrder);
                throw new TradeOrderException("Error executing Buy Order event. Trading Id cannot be created");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                LOGGER.error("Error executing Buy Order event. Trading ID cannot be generated for  trade Order[{}]", tradeOrder);
                throw new TradeOrderException("Error executing Buy Order event. Trading ID cannot be generated");
            }
        } catch (SQLException e) {
            LOGGER.error("Error executing Buy Order event for Trade Order [{}]  ", tradeOrder);
            throw new TradeOrderException("Error creating Buy Order event", e);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
    }


    public int updateTradeTypeByTradeId(long tradeId) throws TradeOrderException {
        Connection connection = null;
        PreparedStatement lockDbRecordStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet resultSet = null;
        TradeOrder tradeOrder = null;
        int updateCount = -1;
        try {
            connection = H2Database.getConnection();
            connection.setAutoCommit(false);
            lockDbRecordStatement = connection.prepareStatement(LOCK_TRADE_ORDER_BY_ID);
            lockDbRecordStatement.setLong(1, tradeId);
            resultSet = lockDbRecordStatement.executeQuery();
            if (resultSet.next()) {
                tradeOrder = new TradeOrder(resultSet.getLong("TradeId"), resultSet.getString("SecurityId"), resultSet.getString("AccountId"),
                        resultSet.getLong("Quantity"), resultSet.getString("TradeType"));
                LOGGER.debug("updating in progress trade Order to Cancel for TradeOrder: [{}]", tradeOrder);
            }

            if (Objects.isNull(tradeOrder)) {
                throw new TradeOrderException("Fail to execute trade order for trade Id : " + tradeId);
            }


            updateStatement = connection.prepareStatement(UPDATE_TRADE_TYPE);
            updateStatement.setString(1, "cancel");
            updateStatement.setLong(2, tradeId);
            updateCount = updateStatement.executeUpdate();
            connection.commit();

            return updateCount;
        } catch (SQLException se) {
            LOGGER.error("Updation Failed, rollback initiated for: [{}]", tradeId, se);
            try {
                if (Objects.nonNull(connection))
                    connection.rollback();
            } catch (SQLException re) {
                throw new TradeOrderException("Fail to rollback transaction", re);
            }
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(lockDbRecordStatement);
            DbUtils.closeQuietly(updateStatement);
        }
        return updateCount;
    }

    @Override
    public List<TradeOrder> getTradeDetails(String accountId, String securityId) throws TradeOrderException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TradeOrder> tradeOrderList = new ArrayList();
        try {
            connection = H2Database.getConnection();
            preparedStatement = connection.prepareStatement(GET_TRADE_ORDER);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TradeOrder tradeOrder = new TradeOrder(resultSet.getLong("TradeId"), resultSet.getString("SecurityId"), resultSet.getString("AccountId"),
                        resultSet.getLong("Quantity"), resultSet.getString("TradeType"));
                LOGGER.debug("Order retrieved from DB is [{}] ", tradeOrder);
                tradeOrderList.add(tradeOrder);
            }
            return tradeOrderList;
        } catch (SQLException e) {
            throw new TradeOrderException("Error reading account data from DB", e);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
    }

    public TradeOrder getTradeDetailsByTradingId(long tradeId) throws TradeOrderException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TradeOrder tradeOrder = null;
        try {
            connection = H2Database.getConnection();
            preparedStatement = connection.prepareStatement(GET_TRADE_DETAILS_BY_ID);
            preparedStatement.setLong(1, tradeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tradeOrder = new TradeOrder(resultSet.getLong("TradeId"), resultSet.getString("SecurityId"), resultSet.getString("AccountId"),
                        resultSet.getLong("Quantity"), resultSet.getString("TradeType"));
                LOGGER.debug("Trade Order Details fetched for Id [{}] is [{}]: ", tradeId, tradeOrder);
            }
            return tradeOrder;
        } catch (SQLException e) {
            throw new TradeOrderException("getTradeDetailsByTradingId(): Error reading TradeOrder data", e);
        } finally {
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
    }
}
