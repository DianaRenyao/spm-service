package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.UserInfoEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Singleton
public class UserInfoFacade extends AbstractFacade<UserInfoEntity> {

    public UserInfoFacade() {
        super(UserInfoEntity.class);
    }

    public UserInfoEntity findByUsername(EntityManager em, String username) {
        try {
            return em.createQuery("select u from UserInfoEntity u where u.username = :username", UserInfoEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserInfoEntity> findByRole(EntityManager em, String role) {
        try {
            return em.createQuery("select u from UserInfoEntity u where u.role = :role", UserInfoEntity.class)
                    .setParameter("role", role)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
