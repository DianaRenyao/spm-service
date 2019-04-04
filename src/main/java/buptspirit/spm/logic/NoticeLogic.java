package buptspirit.spm.logic;

import buptspirit.spm.persistence.entity.NoticeEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import buptspirit.spm.persistence.facade.NoticeFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;
import buptspirit.spm.rest.message.UserInfoMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;
@Singleton
public class NoticeLogic {

    @Inject
    private NoticeFacade noticeFacade;

    @Inject
    private UserInfoFacade userInfoFacade;

    /*
     *   上传notice
     * */
    public List<NoticeMsg> getAllNotice() {
        List<NoticeEntity> notices = transactional(
                em -> noticeFacade.findAll(em),
                "failed to find any notices"
        );
        if (notices == null) return null;
        List<UserInfoEntity> users = transactional(
                em -> userInfoFacade.findByIds(em, notices.stream()
                        .map(NoticeEntity::getAuthor).collect(Collectors.toList())),
                "Fuck! internal error"
        );

        List<NoticeMsg> result = new ArrayList<>();
        for (int i = 0; i < notices.size(); i++) {
            result.add(new NoticeMsg(notices.get(i), users.get(i)));
        }
        return result;
    }

    public static class NoticeMsg {
        private int noticeId;
        private UserInfoMsg author;
        private String title;
        private String detail;
        private Timestamp timeCreated;

        public NoticeMsg(NoticeEntity noticeEntity, UserInfoEntity userInfoEntity) {
            noticeId = noticeEntity.getNoticeId();
            author = new UserInfoMsg(userInfoEntity);
            title = noticeEntity.getTitle();
            detail = noticeEntity.getDetail();
            timeCreated = noticeEntity.getTimeCreated();
        }
    }


    public static class UserInfoMsg {
        private String username;
        private String password;
        private Timestamp timeCreated;
        private String role;
        private String realName;
        private String email;
        private String phone;

        public UserInfoMsg(UserInfoEntity userInfoEntity) {

        }
    }

}
