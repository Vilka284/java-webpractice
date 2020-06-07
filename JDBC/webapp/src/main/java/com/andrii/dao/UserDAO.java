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
                        " WHERE user_name=\"" + username + "\" AND password=\"" + password + "\";";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getUserQuery);

            boolean check = result.next();
            int role = result.getInt("role_id");
            String getUserStatusQuery =
                    "SELECT " +
                            "role_name" +
                            " FROM role" +
                            " WHERE id=\"" + role + "\";";

            result = statement.executeQuery(getUserStatusQuery);

            if (check)
                u.setRole(result.getString("role_name"));

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
                        " WHERE user_name=\"" + username + "\" AND password=\"" + password + "\";";
        String insertUserQuery =
                "INSERT INTO \"user\" (user_name, password, role) " +
                "VALUES (\"" + username + "\", \"" + password + "\", 0);";

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(getUserQuery);

            if (result.getString("user_name").equals(u.getUsername()))
                return false;
            else
                result = statement.executeQuery(insertUserQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, result, statement);
        }
        return true;
    }

    public static void setRole(int userId, int roleId) {

    }

}
