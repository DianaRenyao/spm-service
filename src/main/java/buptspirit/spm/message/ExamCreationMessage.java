package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

import java.util.List;

import static buptspirit.spm.exception.ServiceAssertionUtility.serviceAssert;

public class ExamCreationMessage implements InputMessage {
    private int chapterId;
    private List<QuestionCreationMessage> questionsCreationMessages;

    @Override
    public void enforce() throws ServiceAssertionException {
        serviceAssert(questionsCreationMessages != null && !questionsCreationMessages.isEmpty());
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public List<QuestionCreationMessage> getQuestionsCreationMessages() {
        return questionsCreationMessages;
    }

    public void setQuestionsCreationMessages(List<QuestionCreationMessage> questionsCreationMessages) {
        this.questionsCreationMessages = questionsCreationMessages;
    }
}
