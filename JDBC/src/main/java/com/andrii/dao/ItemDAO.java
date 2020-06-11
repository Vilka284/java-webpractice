package com.andrii.dao;

import com.andrii.model.Item;
import lombok.Singleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton(style = Singleton.Style.HOLDER)
public class ItemDAO implements DAO {

    private static Connection connection;
    private static ResultSet result;
    private static Statement statement;

    public Item getItemById(int id) {
        Item i = new Item();
        final String getItemQuery =
                "SELECT " +
                        "*" +
                        " FROM item WHERE id=" + id + ";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getItemQuery);

            i.setName(result.getString("item_name"));
            i.setPrice(result.getFloat("price"));
            i.setQuantity(result.getInt("quantity"));
            i.setGroupId(result.getInt("group_id"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }

        return i;
    }

    public List<Item> getItemsByGroupId(int id) {
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
            close(result);
            close(statement);
            close(connection);
        }

        return itemList;
    }

    public void insert(Item item) {
        final String insertItemQuery =
                "INSERT INTO item (item_name, price, quantity, group_id) " +
                        "VALUES (\'" + item.getName() + "\', "
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
            close(result);
            close(statement);
            close(connection);
        }

    }

    public void removeItem(int id) {
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
            close(result);
            close(statement);
            close(connection);
        }
    }

    private static List<Item> getItemsFromResultSet(ResultSet result, int groupId) {
        List<Item> itemList = new ArrayList<>();
        Item item;
        try {
            while (result.next()) {
                item = new Item();
                item.setName(result.getString("item_name"));
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
