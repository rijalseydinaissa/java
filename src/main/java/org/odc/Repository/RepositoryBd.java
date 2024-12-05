package org.odc.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public  class RepositoryBd<T> implements Repository<T> {
    protected static EntityManagerFactory emf;
    protected static EntityManager em;
    private Class<T> entityClass;

    public RepositoryBd(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.em = entityManager;
    }

    @Override
    public T save(T entity) {
        try {
            em.getTransaction().begin();
            if (em.contains(entity)) {
                em.merge(entity);
            } else {
                em.persist(entity);
            }
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(jpql, entityClass).getResultList();
    }

    public void update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        T entity = em.find(entityClass, id);
        if (entity != null) {
            em.remove(entity);
        }
        em.getTransaction().commit();
    }
}