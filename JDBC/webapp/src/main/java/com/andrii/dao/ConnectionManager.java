package com.andrii.dao;

import java.sql.*;


class ConnectionManager {

    private static Connection connection;

    static Connection getConnection() {

        try {
            String url = "jdbc:postgresql:" + "java_task";
            Class.forName("org.postgresql.Driver");

            try {
                connection = DriverManager.getConnection(url, "andrii", "lmc325");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

