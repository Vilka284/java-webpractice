package com.springapp.andrii.service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface IService<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t, long id);

    void delete(T t);

    boolean exist(T t);
}
