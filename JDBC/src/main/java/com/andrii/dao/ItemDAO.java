package com.andrii.dao;

import com.andrii.model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements DAO {

    private static Connection connection;
    private static ResultSet result;
    private static Statement statement;

    public static Item getItemById(int id) {
        Item i = new Item();
        final String getItemQuery =
                "SELECT " +
                        "*" +
                        " FROM item WHERE id=" + id + ";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getItemQuery);

            i.setItemName(result.getString("item_name"));
            i.setPrice(result.getFloat("price"));
            i.setQuantity(result.getInt("quantity"));
            i.setGroupId(result.getInt("group_id"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAO.close(connection);
            DAO.close(result);
            DAO.close(statement);
        }

        return i;
    }

    public static List<Item> getItemsByGroupId(int id) {
        List<Item> itemList = null;
        final String getItemsQuery =
                "SELECT " +
                        "*" +
                        " FROM item i " +
                        "INNER JOIN \"group\" g " +
                        "ON i.group_id = " + id + ";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getItemsQuery);
            itemList = getItemsFromResultSet(result, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAO.close(connection);
            DAO.close(result);
            DAO.close(statement);
        }

        return itemList;
    }

    public static void insert(Item item) {
        final String insertItemQuery =
                "INSERT INTO item (item_name, price, quantity, group_id) " +
                        "VALUES (\'" + item.getItemName() + "\', "
                        + item.getPrice() + ", "
                        + item.getQuantity() + ", "
                        + item.getGroupId() + ");";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertItemQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAO.close(connection);
            DAO.close(result);
            DAO.close(statement);
        }

    }

    public static void removeItem(int id) {
        final String deleteItemQuery =
                "DELETE FROM item " +
                        "WHERE id=" + id + ";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(deleteItemQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAO.close(connection);
            DAO.close(result);
            DAO.close(statement);
        }
    }

    private static List<Item> getItemsFromResultSet(ResultSet result, int groupId) {
        List<Item> itemList = new ArrayList<>();
        Item item;
        try {
            while (result.next()) {
                item = new Item();
                item.setItemName(result.getString("item_name"));
                item.setPrice(result.getFloat("price"));
                item.setQuantity(result.getInt("quantity"));
                item.setGroupId(groupId);
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;

    }

}
