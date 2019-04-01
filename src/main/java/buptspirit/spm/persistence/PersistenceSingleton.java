package buptspirit.spm.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum PersistenceSingleton {
    instance;

    private EntityManagerFactory entityManagerFactory;

    private EntityManagerFactory setupEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("buptspirit.spm.persistence");
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = setupEntityManagerFactory();
        }
        return entityManagerFactory;
    }

    public void destroy() {
        if (entityManagerFactory != null)
            entityManagerFactory.close();
        entityManagerFactory = null;
    }
}
