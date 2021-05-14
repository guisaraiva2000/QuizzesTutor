package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;

public class OpenAnswerCorrectAnswerDto extends CorrectAnswerDetailsDto{
    private String correctAnswer;

    public OpenAnswerCorrectAnswerDto(OpenAnswerQuestion questionAnswer){
        this.correctAnswer = questionAnswer.getCorrectAnswer();
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
    }

    @Override
    public String toString() {
        return "OpenAnswerCorrectAnswerDto{" +
                "correctAnswer=" + correctAnswer +
                '}';
    }
}
