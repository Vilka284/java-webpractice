package com.springapp.andrii.service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface IService<T> {
    @Transactional
    T get(long id);

    @Transactional
    List<T> getAll();

    @Transactional
    void save(T t);

    @Transactional
    void update(T t, long id);

    @Transactional
    void delete(T t);

    @Transactional
    boolean exist(T t);
}
