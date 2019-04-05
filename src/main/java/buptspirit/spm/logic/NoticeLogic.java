package buptspirit.spm.logic;

import buptspirit.spm.exception.ServiceAssertionException;
import buptspirit.spm.exception.ServiceError;
import buptspirit.spm.exception.ServiceException;
import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.NoticeCreationMessage;
import buptspirit.spm.message.NoticeMessage;
import buptspirit.spm.message.SessionMessage;
import buptspirit.spm.persistence.entity.NoticeEntity;
import buptspirit.spm.persistence.facade.NoticeFacade;
import buptspirit.spm.persistence.facade.TeacherFacade;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

import static buptspirit.spm.persistence.JpaUtility.transactional;

@Singleton
public class NoticeLogic {

    @Inject
    private NoticeFacade noticeFacade;

    @Inject
    private MessageMapper messageMapper;

    @Inject
    private TeacherFacade teacherFacade;

    public List<NoticeMessage> getAllNotice() {
        return transactional(
                em -> noticeFacade.findAll(em).stream().map(
                        notice -> messageMapper.intoMessage(em, notice)
                ).collect(Collectors.toList()),
                "failed to find notices"
        );
    }

    public NoticeMessage getNotice(int id) {
        return transactional(
                em -> {
                    NoticeEntity notice = noticeFacade.find(em, id);
                    return messageMapper.intoMessage(em, notice);
                },
                "failed to find notice"
        );
    }

    public NoticeMessage createNotice(
            SessionMessage sessionMessage,
            NoticeCreationMessage noticeCreationMessage) throws ServiceAssertionException {
        noticeCreationMessage.enforce();
        int teacherId = sessionMessage.getUserInfo().getId(); // also userId
        NoticeEntity entity = new NoticeEntity();
        entity.setAuthor(teacherId);
        entity.setTitle(noticeCreationMessage.getTitle());
        entity.setDetail(noticeCreationMessage.getDetail());
        return transactional(
                em -> {
                    noticeFacade.create(em, entity);
                    // fetch all information to construct full notice message
                    return messageMapper.intoMessage(em, entity);
                },
                "failed to create notice"
        );
    }

    public NoticeMessage modifyNotice(
            int id,
            SessionMessage sessionMessage,
            NoticeCreationMessage noticeCreationMessage) throws ServiceException, ServiceAssertionException {
        noticeCreationMessage.enforce();
        NoticeEntity noticeEntity = transactional(
                em -> noticeFacade.find(em, id),
                "failed to find notice"
        );
        if (noticeEntity == null)
            throw ServiceError.PUT_NOTICE_NO_SUCH_NOTICE.toException();
        if (noticeEntity.getAuthor() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        noticeEntity.setTitle(noticeCreationMessage.getTitle());
        noticeEntity.setDetail(noticeCreationMessage.getDetail());
        return transactional(
                em -> {
                    noticeFacade.edit(em, noticeEntity);
                    return messageMapper.intoMessage(em, noticeEntity);
                },
                "failed to edit notice"
        );
    }

    public void deleteNotice(
            int id,
            SessionMessage sessionMessage) throws ServiceException {
        NoticeEntity noticeEntity = transactional(
                em -> noticeFacade.find(em, id),
                "failed to find notice"
        );
        if (noticeEntity == null)
            throw ServiceError.DELETE_NOTICE_NO_SUCH_NOTICE.toException();
        if (noticeEntity.getAuthor() != sessionMessage.getUserInfo().getId())
            throw ServiceError.FORBIDDEN.toException();
        transactional(
                em -> {
                    noticeFacade.remove(em, noticeEntity);
                    return null;
                },
                "failed to remove notice"
        );
    }
}
