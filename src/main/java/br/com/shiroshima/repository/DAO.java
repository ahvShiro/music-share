package br.com.shiroshima.repository;

import br.com.shiroshima.exception.DAOException;
import jakarta.persistence.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class DAO<T, Long extends Serializable> implements DAOInterface<T> {

    protected final EntityManagerFactory emf;
    protected final EntityManager em;
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public DAO() {
        this.emf = Persistence.createEntityManagerFactory("persistenceUnit");
        this.em = emf.createEntityManager();
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Optional<T> findById(Long id) {
        try {
            T entity = em.find(persistentClass, id);
            return Optional.ofNullable(entity);
        } catch (PersistenceException e) {
            throw new DAOException("Error finding entity with id: " + id);
        }
    }

    public List<T> findAll() {
        try {
            return em.createQuery("SELECT c FROM " + persistentClass.getSimpleName() + " c ORDER BY c.createdAt DESC", persistentClass).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException("Error fetching all comments");
        }
    }

    public T save(T entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DAOException("Error saving entity: " + e.getMessage());
        }
    }

    public T update(T entity) {
        try {
            em.getTransaction().begin();
            T updatedEntity = em.merge(entity);
            em.getTransaction().commit();
            return updatedEntity;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DAOException("Error updating entity: " + e.getMessage());
        }
    }

    public void delete(T entity) {
        try {
            em.getTransaction().begin();
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DAOException("Error deleting entity: " + e.getMessage());
        }
    }
}
