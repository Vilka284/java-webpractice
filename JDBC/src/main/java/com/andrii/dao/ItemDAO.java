package com.andrii.dao;

import com.andrii.module.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends ConnectionCloser {

    public static Item getItemById(int itemId){
        Item i = new Item();
        String getItemQuery =
                "SELECT " +
                "*" +
                " FROM item WHERE id=\"" + itemId +"\";";

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
            close(connection, result, statement);
        }

        return i;
    }

    public static List<Item> getItemsByGroupId(int groupId) {
        List<Item> itemList = new ArrayList<>();
        Item i;
        String getItemsQuery =
                "SELECT " +
                "*" +
                " FROM item i " +
                "INNER JOIN \"group\" g " +
                "ON i.group_id = \'" + groupId + "\';";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getItemsQuery);

            while (result.next()){
                i = new Item();
                i.setItemName(result.getString("item_name"));
                i.setPrice(result.getFloat("price"));
                i.setQuantity(result.getInt("quantity"));
                i.setGroupId(groupId);
                itemList.add(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, result, statement);
        }

        return itemList;
    }

    public static void insertItem(Item i){
        String insertItemQuery =
                "INSERT INTO item (item_name, price, quantity, group_id) " +
                        "VALUES (\'" + i.getItemName() + "\', \'"
                                   + i.getPrice() + "\', \'"
                                   + i.getQuantity() + "\', \'"
                                   + i.getGroupId() + "\');";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertItemQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, result, statement);
        }

    }

    public static void removeItem(int id){
        String deleteItemQuery =
                "DELETE FROM item " +
                "WHERE id=\'" + id + "\';";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(deleteItemQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, result, statement);
        }
    }

}
