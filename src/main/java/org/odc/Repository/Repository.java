package org.odc.Repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T save(T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    void delete(Long id);
    void delete(T entity);
}