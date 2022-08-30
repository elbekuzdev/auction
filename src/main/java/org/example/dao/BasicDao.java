package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface BasicDao<T> {
    boolean append(T t);
    boolean update(T t);
    boolean delete(int id);
    Optional<T> findById(int id);
    List<T> getAll();
}
