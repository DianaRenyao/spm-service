package buptspirit.spm.logic;

import buptspirit.spm.message.MessageMapper;
import buptspirit.spm.message.SectionCreationMessage;
import buptspirit.spm.message.SectionMessage;
import buptspirit.spm.persistence.facade.ChapterFacade;
import buptspirit.spm.persistence.facade.CourseFacade;
import buptspirit.spm.persistence.facade.SectionFacade;

import javax.inject.Inject;

public class SectionLogic {
    @Inject
    ChapterFacade chapterFacade;

    @Inject
    CourseFacade courseFacade;

    @Inject
    SectionFacade sectionFacade;

    @Inject
    private MessageMapper messageMapper;

    public SectionMessage insertSection(int courseId, int chapterId, SectionCreationMessage sectionCreationMessage) {

    }
}
