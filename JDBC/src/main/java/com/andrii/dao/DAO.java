package com.andrii.dao;

public interface DAO {

    public default <T extends AutoCloseable> void close(T objectToClose){
        if (objectToClose != null) {
            try {
                objectToClose.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
