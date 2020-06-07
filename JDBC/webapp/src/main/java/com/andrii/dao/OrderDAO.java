package com.andrii.dao;

import com.andrii.module.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends ConnectionCloser {

    public static void createOrder(Order o) {
        int userId = o.getUserId();
        List<Integer> itemsList = o.getOrderedItemsId();
        String createOrderQuery =
                "INSERT INTO order VALUES (user_id, item_id) " +
                "VALUES (\"" + userId + "\",\" " + itemsList.get(0) + "\") ";
        itemsList.remove(0);
        /*
        Add more items to order if it need to
         */
        for (int i:
            itemsList) {
            createOrderQuery = createOrderQuery.concat(
                    ", (\"" + userId + "\", \"" + i + "\")"
            );
        }
        createOrderQuery = createOrderQuery.concat(";");

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(createOrderQuery);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, result, statement);
        }
    }

    public static Order getOrderByUserId(int userId) {
        Order o = new Order();
        List<Integer> itemsList = new ArrayList<>();
        String getOrderQuery = "SELECT " +
                "user_id, " +
                "item_id, " +
                " FROM \"order\" WHERE user_id=\"" + userId + "\";";
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
            close(connection, result, statement);
        }
        return o;
    }

    public static void removeItemById(int userId, int itemId) {
        String removeItemQuery =
                "DELETE FROM \"order\"" +
                        "WHERE user_id=" + userId + " AND item_id=" + itemId + ";";
        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(removeItemQuery);
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, result, statement);
        }
    }
}
