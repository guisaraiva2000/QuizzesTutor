package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;

import javax.persistence.*;

@Entity
@DiscriminatorValue(Question.QuestionTypes.OPEN_ANSWER_QUESTION)
public class OpenAnswer extends AnswerDetails {

    @Column(columnDefinition = "TEXT")
    private String answer;

    public OpenAnswer() {
        super();
    }

    public OpenAnswer(QuestionAnswer questionAnswer){
        super(questionAnswer);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer(OpenAnswerQuestion question, OpenAnswerStatementAnswerDetailsDto openAnswerStatementAnswerDetailsDto) {
        if (openAnswerStatementAnswerDetailsDto.getAnswer() == null) {
            this.setAnswer(null);
        } else /*if (question.getCorrectAnswer().equals(openAnswerStatementAnswerDetailsDto.getAnswer())) */{
            this.remove();
            this.setAnswer(openAnswerStatementAnswerDetailsDto.getAnswer());
        }
    }

    @Override
    public boolean isCorrect() {
        String correctAnswer = ((OpenAnswerQuestion) this.getQuestionAnswer().getQuestion().getQuestionDetails()).getCorrectAnswer();
        return getAnswer() != null && getAnswer().equals(correctAnswer);
    }

    @Override
    public void remove() {
        this.answer = null;
    }

    @Override
    public AnswerDetailsDto getAnswerDetailsDto() {
        return new OpenAnswerDto(this);
    }

    @Override
    public String getAnswerRepresentation() {
        return this.answer;
    }

    @Override
    public StatementAnswerDetailsDto getStatementAnswerDetailsDto() {
        return new OpenAnswerStatementAnswerDetailsDto(this);
    }

    @Override
    public boolean isAnswered() {
        return this.answer != null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAnswerDetails(this);
    }
}
