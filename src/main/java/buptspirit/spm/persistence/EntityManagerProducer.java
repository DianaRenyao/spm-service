package buptspirit.spm.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

public class EntityManagerProducer {

    @Produces
    public EntityManager getEntityManager() {
        return PersistenceSingleton.instance.getEntityManagerFactory().createEntityManager();
    }
}
