package buptspirit.spm.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

public class JpaUtility {

    public static <T> T transactional(final JpaOperation<T> operation, String errorMessage) {
        EntityManager em = PersistenceSingleton.instance.getEntityManagerFactory().createEntityManager();

        final EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            final T returnedData = operation.execute(em);
            tx.commit();
            return returnedData;
        } catch (RollbackException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new IllegalStateException(errorMessage, e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @FunctionalInterface
    public interface JpaOperation<T> {
        T execute(EntityManager entityManager);
    }
}

