package com.andrii.dao;

import com.andrii.model.Order;
import lombok.Singleton;

import java.sql.Connection;
import java.sql.ResultSet;
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
        String createOrderQuery =
                "INSERT INTO order VALUES (user_id, item_id) " +
                "VALUES (" + userId + ", " + itemsList.get(0) + ") ";
        itemsList.remove(0);
        /*
        Add more items to order if it need to
         */
        for (int i:
            itemsList) {
            createOrderQuery = createOrderQuery.concat(
                    ", (" + userId + ", " + i + ")"
            );
        }
        createOrderQuery = createOrderQuery.concat(";");

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(createOrderQuery);

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
                "user_id, " +
                "item_id, " +
                " FROM \"order\" WHERE user_id=" + userId + ";";
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
    public void closeOrder(int userId, int itemId, boolean buy) {
        final String removeItemQuery =
                "DELETE FROM \"order\"" +
                        "WHERE user_id=" + userId + " AND item_id=" + itemId + ";";

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

}
