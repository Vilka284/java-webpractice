package com.andrii.dao;

import com.andrii.model.User;
import lombok.Singleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Singleton(style = Singleton.Style.HOLDER)
public class UserDAO implements DAO {

    private static Connection connection;
    private static ResultSet result;
    private static Statement statement;

    public User login(User u) {
        String username = u.getUsername();
        String password = u.getPassword();
        final String getUserQuery =
                        "SELECT " +
                        "*" +
                        " FROM \"user\"" +
                        " WHERE user_name=\'" + username + "\' AND password=\'" + password + "\';";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getUserQuery);

            boolean check = result.next();
            if (check) {
                int role = result.getInt("role_id");
                final String getUserRoleQuery =
                        "SELECT " +
                                "role_name" +
                                " FROM role" +
                                " WHERE id=" + role + ";";

                result = statement.executeQuery(getUserRoleQuery);
                if (result.next())
                    u.setRole(result.getString("role_name"));
            }

            u.setValid(check);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }

        return u;
    }

    public boolean register(User u) {
        String username = u.getUsername();
        String password = u.getPassword();
        final String getUserQuery =
                        "SELECT " +
                        " * " +
                        " FROM \"user\" " +
                        " WHERE user_name=\'" + username + "\';";
        final String insertUserQuery =
                "INSERT INTO \"user\" (user_name, password, role_id) " +
                "VALUES (\'" + username + "\', \'" + password + "\', 1);";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getUserQuery);

            if (result == null)
                return false;
            else
                statement.executeUpdate(insertUserQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }
        return true;
    }

    public void setRole(int userId, int roleId) {
        final String updateUserRoleQuery =
                        "UPDATE \"user\" " +
                        " SET role_id=" + roleId +
                        " WHERE id=" + userId + ";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(updateUserRoleQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }
    }

}
