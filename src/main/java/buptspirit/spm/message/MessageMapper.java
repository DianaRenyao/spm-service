package buptspirit.spm.message;
import buptspirit.spm.persistence.entity.ApplicationEntity;
import buptspirit.spm.persistence.entity.ChapterEntity;
import buptspirit.spm.persistence.entity.CourseEntity;
import buptspirit.spm.persistence.entity.FileSourceEntity;
import buptspirit.spm.persistence.entity.NoticeEntity;
import buptspirit.spm.persistence.entity.SectionEntity;
import buptspirit.spm.persistence.entity.StudentEntity;
import buptspirit.spm.persistence.entity.TeacherEntity;
import buptspirit.spm.persistence.entity.UserInfoEntity;
import buptspirit.spm.persistence.entity.SelectedCourseEntity;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.FileSourceFacade;
import buptspirit.spm.persistence.facade.SectionFacade;
import buptspirit.spm.persistence.facade.StudentFacade;
import buptspirit.spm.persistence.facade.TeacherFacade;
import buptspirit.spm.persistence.facade.UserInfoFacade;
import buptspirit.spm.persistence.facade.SelectedCourseFacade;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MessageMapper {

    @Inject
    private UserInfoFacade userInfoFacade;

    @Inject
    private TeacherFacade teacherFacade;

    @Inject
    private StudentFacade studentFacade;

    @Inject
    private CourseFacade courseFacade;

    @Inject
    private SectionFacade sectionFacade;

    @Inject
    private ChapterFacade chapterFacade;

    @Inject
    private FileSourceFacade fileSourceFacade;

    public NoticeMessage intoNoticeMessage(EntityManager em, NoticeEntity entity) {
        int authorId = entity.getAuthor();
        TeacherMessage teacher = intoTeacherMessage(em, teacherFacade.find(em, authorId));
        return NoticeMessage.fromEntity(entity, teacher);
    }

    public TeacherMessage intoTeacherMessage(EntityManager em, TeacherEntity entity) {
        int userId = entity.getUserId();
        UserInfoMessage user = intoUserInfoMessage(em, userInfoFacade.find(em, userId));
        return TeacherMessage.fromEntity(entity, user);
    }

    public UserInfoMessage intoUserInfoMessage(EntityManager em, UserInfoEntity entity) {
        return UserInfoMessage.fromEntity(entity);
    }

    public StudentMessage intoStudentMessage(EntityManager em, StudentEntity entity) {
        int userId = entity.getUserId();
        UserInfoMessage user = intoUserInfoMessage(em, userInfoFacade.find(em, userId));
        return StudentMessage.fromEntity(entity, user);
    }

    public SelectedCourseMessage intoScoreMessage(EntityManager em, SelectedCourseEntity entity) {
        return SelectedCourseMessage.fromEntity(entity);
    }

    public CourseMessage intoCourseMessage(EntityManager em, CourseEntity entity) {
        int userId = entity.getTeacherUserId();
        TeacherMessage teacher = intoTeacherMessage(em, teacherFacade.find(em, userId));
        int courseId = entity.getCourseId();
        List<ChapterMessage> chapters = chapterFacade.findCourseChapters(em, courseId).stream().map(
                chapter -> intoChapterMessage(em, chapter)).collect(Collectors.toList());
        return CourseMessage.fromEntity(entity, teacher, chapters);
    }

    public CourseSummaryMessage intoCourseSummaryMessage(EntityManager em, CourseEntity entity) {
        int userId = entity.getTeacherUserId();
        TeacherMessage teacher = intoTeacherMessage(em, teacherFacade.find(em, userId));
        return CourseSummaryMessage.fromEntity(entity, teacher);
    }

    public ApplicationMessage intoApplicationMessage(EntityManager em, ApplicationEntity entity) {
        int studentUserId = entity.getStudentUserId();
        StudentMessage student = intoStudentMessage(em, studentFacade.find(em, studentUserId));
        int courseId = entity.getCourseId();
        CourseSummaryMessage courseMessage = intoCourseSummaryMessage(em, courseFacade.find(em, courseId));
        return ApplicationMessage.fromEntity(entity, courseMessage, student);
    }

    public ChapterMessage intoChapterMessage(EntityManager em, ChapterEntity entity) {
        int chapterId = entity.getChapterId();
        List<SectionMessage> sections = sectionFacade.findCourseChapterSections(em, chapterId).stream().map(
                section -> intoSectionMessage(em, section)).collect(Collectors.toList());
        return ChapterMessage.fromEntity(entity, sections);
    }

    public SectionMessage intoSectionMessage(EntityManager em, SectionEntity entity) {
        int sectionId = entity.getSectionId();
        List<FileSourceMessage> fileSources = fileSourceFacade.findSectionFile(em, sectionId)
                .map(file -> intoFileSourceMessage(em, file)).collect(Collectors.toList());
        return SectionMessage.fromEntity(entity, fileSources);
    }

    public FileSourceMessage intoFileSourceMessage(EntityManager em, FileSourceEntity entity) {
        return FileSourceMessage.fromEntity(entity);
    }
}
