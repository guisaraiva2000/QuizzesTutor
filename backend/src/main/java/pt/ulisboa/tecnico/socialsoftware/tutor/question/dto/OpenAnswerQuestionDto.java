package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import java.util.regex.Pattern;


public class OpenAnswerQuestionDto extends QuestionDetailsDto {
    private String correctAnswer;
    private Pattern expression;

    public OpenAnswerQuestionDto() {
    }

    public OpenAnswerQuestionDto(OpenAnswerQuestion question) {
        this.correctAnswer = question.getCorrectAnswer();
        this.expression = question.getExpression();
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Pattern getExpression() {
        return expression;
    }

    public void setExpression(Pattern expression) {
        this.expression = expression;
    }

    @Override
    public void update(OpenAnswerQuestion question) {
        question.update(this);
    }

    @Override
    public String toString() {
        return "OpenAnswerQuestionDto{" +
                "correctAnswer='" + correctAnswer + '\'' +
                ", expression=" + expression +
                '}';
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return new OpenAnswerQuestion(question, this);
    }
}
