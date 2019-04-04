package buptspirit.spm.persistence.facade;

import buptspirit.spm.persistence.entity.UserInfo;
import buptspirit.spm.persistence.entity.UserInfoEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class UserInfoFacade extends AbstractFacade<UserInfoEntity> {

    public UserInfoFacade() {
        super(UserInfoEntity.class);
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

    public List<UserInfoEntity> findByIds(EntityManager em, List<Integer> ids) {
        List<UserInfoEntity> users = new ArrayList<>();
        ids.forEach(e -> users.add(find(em, e)));
        return users;
    }
}
