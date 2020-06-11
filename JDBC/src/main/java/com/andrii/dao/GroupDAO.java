package com.andrii.dao;

import com.andrii.model.Group;
import com.andrii.model.Item;
import lombok.Singleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton(style = Singleton.Style.HOLDER)
public class GroupDAO implements DAO {

    private static Connection connection;
    private static ResultSet result;
    private static Statement statement;

    public Group getGroup(Item item) {
        Group group = new Group();
        final String getGroupQuery =
                "SELECT" +
                        "*" +
                        "FROM \"group\" WHERE id=" + item.getGroupId() + ";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getGroupQuery);

            group.setId(result.getInt("id"));
            group.setName(result.getString("group_name"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }
        return group;
    }

    /*
    Recursive get full group path
    example: Item group is bullets. Full group path will look like
    [bullets, weapon, hunting], Where "hunting" it is the parent group
    and id = null
     */
    public List<Group> getGroupPath(Item item) {
        List<Group> groupPath = new ArrayList<>();
        int groupId = item.getGroupId();
        final String getGroupPathQuery =
                "SELECT" +
                        "*" +
                        "FROM \"group\" WHERE id=" + groupId + ";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getGroupPathQuery);

            groupPath.add(new Group(result.getInt("id"), result.getString("group_name")));
            item.setGroupId(--groupId);
            // result.getInt return 0 if column is null
            if (result.getInt("parent_group_id") != 0)
                groupPath.addAll(getGroupPath(item));
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            close(result);
            close(statement);
            close(connection);
        }
        return groupPath;
    }
}
