package br.com.shiroshima.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class DAO<T, ID extends Serializable> {

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
            throw new RuntimeException("Erro ao salvar a entidade", e);
        }
    }

    public T update(T entity) {
        try {
            return em.merge(entity);
        } catch (PersistenceException e) {
            throw new RuntimeException("Erro ao atualizar a entidade", e);
        }
    }

    public void delete(T entity) {
        try {
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);
        } catch (PersistenceException e) {
            throw new RuntimeException("Erro ao deletar a entidade", e);
        }
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(persistentClass, id));
    }

    public List<T> findAll() {
        return em.createQuery("FROM " + persistentClass.getName(), persistentClass).getResultList();
    }
}
