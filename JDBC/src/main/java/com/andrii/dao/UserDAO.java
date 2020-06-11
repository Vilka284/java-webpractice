package com.andrii.dao;

import com.andrii.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO implements DAO {

    private static Connection connection;
    private static ResultSet result;
    private static Statement statement;
    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
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
                    user.setRole(result.getString("role_name"));
            }

            user.setValid(check);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
            close(connection);
        }

        return user;
    }

    public boolean register(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
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
