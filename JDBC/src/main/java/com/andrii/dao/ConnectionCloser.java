package com.andrii.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class ConnectionCloser {
    static Connection connection;
    static ResultSet result;
    static Statement statement;

    static void close(Connection c, ResultSet r, Statement s) {
        if (c != null && r != null && s != null) {
            try {
                c.close();
                r.close();
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
