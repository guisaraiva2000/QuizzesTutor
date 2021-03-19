package pt.ulisboa.tecnico.socialsoftware.tutor.question.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;


public class OpenAnswerQuestionDto extends QuestionDetailsDto {
    // TODO

    public OpenAnswerQuestionDto() {
        // TODO
    }

    public OpenAnswerQuestionDto(OpenAnswerQuestion question) {
        // TODO
    }

    @Override
    public QuestionDetails getQuestionDetails(Question question) {
        return null;
    }
}
