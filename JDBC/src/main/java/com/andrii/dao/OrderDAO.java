package com.andrii.dao;

import com.andrii.model.Order;
import lombok.Singleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton(style = Singleton.Style.HOLDER)
public class OrderDAO implements DAO {

    private static Connection connection;
    private static ResultSet result;
    private static Statement statement;
    private static ItemDAO itemDAO;

    public void createOrder(Order o) {
        int userId = o.getUserId();
        List<Integer> itemsList = o.getOrderedItemsId();
        final String createOrderQuery =
                "INSERT INTO \"order\" VALUES (user_id) " +
                "VALUES (" + userId + ");";
        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(createOrderQuery);
            // retrieve an id of newly inserted record
            result = statement.getGeneratedKeys();
            int orderId = result.getInt(1);
            insertItems(orderId, itemsList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }
    }

    /*
    Find orders of user
     */
    public Order getOrderByUserId(int userId) {
        Order o = new Order();
        List<Integer> itemsList = new ArrayList<>();
        final String getOrderQuery = "SELECT " +
                "user_id" +
                " FROM \"order\" o WHERE user_id=" + userId + " " +
                "INNER JOIN order_item oi " +
                "ON o.id = oi.order_id;";
        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getOrderQuery);
            /*
            Iterate through result and fill list
             */
            while (result.next()) {
                itemsList.add(result.getInt("item_id"));
            }
            o.setUserId(result.getInt("user_id"));
            o.setOrderedItemsId(itemsList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }
        return o;
    }

    /*
    Remove item from order if order canceled or finished
     */
    public void closeOrder(int orderId, int userId, int itemId, boolean buy) {
        final String removeItemQuery =
                "DELETE FROM \"order\"" +
                        "WHERE user_id=" + userId + " AND id=" + orderId + ";";
        final String reduceItemQuantityQuery =
                "UPDATE item " +
                        "ON quantity = ((SELECT quantity FROM item WHERE id=" + itemId + ") - 1) " +
                        "WHERE id=" + itemId + ";";
        final String getItemQuery =
                "SELECT " +
                        "*" +
                        " FROM item WHERE id=" + itemId + ";";
        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(removeItemQuery);
            if (buy)
                statement.executeUpdate(reduceItemQuantityQuery);
            result = statement.executeQuery(getItemQuery);
            if (result.getInt("quantity") <= 0)
                itemDAO.removeItem(itemId);
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }
    }

    private static void insertItems(int orderId, List<Integer> itemsList) {
        String createOrderQuery =
                "INSERT INTO order_item VALUES (order_id, item_id) " +
                        "VALUES (" + orderId + ", " + itemsList.get(0) + ") ";
        itemsList.remove(0);
        /*
        Add more items to order if it need to
         */
        for (int i:
                itemsList) {
            createOrderQuery = createOrderQuery.concat(
                    ", (" + orderId + ", " + i + ")"
            );
        }
        createOrderQuery = createOrderQuery.concat(";");
        try {
            statement.executeUpdate(createOrderQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
