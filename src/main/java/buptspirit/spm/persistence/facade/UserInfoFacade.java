package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.UserInfo;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Singleton
public class UserInfoFacade extends AbstractFacade<UserInfo> {

    public UserInfoFacade() {
        super(UserInfo.class);
    }

    public UserInfo findByUsername(EntityManager em, String username) {
        try {
            return em.createNamedQuery("UserInfo.findByUsername", UserInfo.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
