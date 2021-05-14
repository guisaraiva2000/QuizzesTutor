package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

import javax.persistence.Transient;

public class OpenAnswerStatementAnswerDetailsDto extends StatementAnswerDetailsDto {
    private String answer;

    public OpenAnswerStatementAnswerDetailsDto() {
    }

    public OpenAnswerStatementAnswerDetailsDto(OpenAnswer questionAnswer) {
        if (questionAnswer.getAnswer() != null) {
            this.answer = questionAnswer.getAnswer();
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Transient
    private OpenAnswer createdOpenAnswer;

    @Override
    public AnswerDetails getAnswerDetails(QuestionAnswer questionAnswer) {
        createdOpenAnswer = new OpenAnswer(questionAnswer);
        questionAnswer.getQuestion().getQuestionDetails().update(this);
        return createdOpenAnswer;
    }

    @Override
    public boolean emptyAnswer() {
        return answer == null;
    }

    @Override
    public QuestionAnswerItem getQuestionAnswerItem(String username, int quizId, StatementAnswerDto statementAnswerDto) {
        return new OpenAnswerItem(username, quizId, statementAnswerDto, this);
    }

    @Override
    public void update(OpenAnswerQuestion question) {
        createdOpenAnswer.setAnswer(question, this);
    }

    @Override
    public String toString() {
        return "OpenAnswerStatementAnswerDto{" +
                "answer=" + answer +
                '}';
    }
}
