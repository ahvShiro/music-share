package br.com.shiroshima.repository;

import br.com.shiroshima.exception.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class DAO<T, Long extends Serializable> {

    protected final EntityManager em;
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public DAO(EntityManager em) {
        this.em = em;
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T save(T entity) {
        try {
            em.persist(entity);
            return entity;
        } catch (PersistenceException e) {
            throw new DAOException("Error saving");
        }
    }

    public T update(T entity) {
        try {
            return em.merge(entity);
        } catch (PersistenceException e) {
            throw new DAOException("Error updating entity");
        }
    }

    public void delete(T entity) {
        try {
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);
        } catch (PersistenceException e) {
            throw new DAOException("Error deleting entity");
        }
    }
}
