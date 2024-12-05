package org.odc.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Method;

public class RepositoryList<T> implements Repository<T> {
    protected List<T> entities = new ArrayList<>();

    @Override
    public T save(T entity) {
        try {
            Method getIdMethod = entity.getClass().getMethod("getId");
            Method setIdMethod = entity.getClass().getMethod("setId", Long.class);

            Long id = (Long) getIdMethod.invoke(entity);
            if (id == null) {
                setIdMethod.invoke(entity, (long) (entities.size() + 1));
                entities.add(entity);
            } else {
                int index = entities.indexOf(entity);
                if (index != -1) {
                    entities.set(index, entity);
                } else {
                    entities.add(entity);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error accessing ID methods", e);
        }
        return entity;
    }

    @Override
    public Optional<T> findById(Long id) {
        return entities.stream()
                .filter(e -> {
                    try {
                        Method getIdMethod = e.getClass().getMethod("getId");
                        return id.equals(getIdMethod.invoke(e));
                    } catch (Exception ex) {
                        throw new RuntimeException("Error accessing ID method", ex);
                    }
                })
                .findFirst();
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public void delete(Long id) {
        entities.removeIf(e -> {
            try {
                Method getIdMethod = e.getClass().getMethod("getId");
                return id.equals(getIdMethod.invoke(e));
            } catch (Exception ex) {
                throw new RuntimeException("Error accessing ID method", ex);
            }
        });
    }

    @Override
    public void delete(T entity) {
        entities.remove(entity);
    }
}