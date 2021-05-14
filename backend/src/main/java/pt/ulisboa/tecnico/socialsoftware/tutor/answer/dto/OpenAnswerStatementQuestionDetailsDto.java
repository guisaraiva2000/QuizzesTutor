package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

public class OpenAnswerStatementQuestionDetailsDto extends StatementQuestionDetailsDto {
    private String correctAnswer;

    public OpenAnswerStatementQuestionDetailsDto(OpenAnswerQuestion question) {
        this.correctAnswer = question.getCorrectAnswer();
    }

    public String getAnswer() {
        return correctAnswer;
    }

    public void setAnswer(String answer) {
        this.correctAnswer = answer;
    }

    @Override
    public String toString() {
        return "OpenAnswerStatementQuestionDetailsDto{" +
                "correctAnswer=" + correctAnswer +
                '}';
    }
}
