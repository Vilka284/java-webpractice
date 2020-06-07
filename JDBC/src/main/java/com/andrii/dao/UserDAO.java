package com.andrii.dao;

import com.andrii.module.user.User;

public class UserDAO extends ConnectionCloser {

    public static User login(User u) {
        String username = u.getUsername();
        String password = u.getPassword();
        String getUserQuery =
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
                String getUserRoleQuery =
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
            close(connection, result, statement);
        }

        return u;
    }

    public static boolean register(User u) {
        String username = u.getUsername();
        String password = u.getPassword();
        String getUserQuery =
                        "SELECT " +
                        " * " +
                        " FROM \"user\" " +
                        " WHERE user_name=\'" + username + "\';";
        String insertUserQuery =
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
            close(connection, result, statement);
        }
        return true;
    }

    public static void setRole(int userId, int roleId) {
        String updateUserRoleQuery =
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
            close(connection, result, statement);
        }
    }

}
