package buptspirit.spm.persistence.facade;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(EntityManager em, T entity) {
        em.persist(entity);
    }

    public void edit(EntityManager em, T entity) {
        em.merge(entity);
    }

    public void remove(EntityManager em, T entity) {
        T merged = em.merge(entity);
        em.remove(merged);
    }

    public T find(EntityManager em, Object id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll(EntityManager em) {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    public List<T> findRange(EntityManager em, int[] range) {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        TypedQuery<T> q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count(EntityManager em) {
        CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult().intValue();
    }
}
