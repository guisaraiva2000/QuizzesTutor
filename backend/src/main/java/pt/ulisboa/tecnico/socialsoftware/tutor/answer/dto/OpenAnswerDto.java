package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.*;

public class OpenAnswerDto extends AnswerDetailsDto {
    private String answer;

    public OpenAnswerDto() {
        super();
    }

    public OpenAnswerDto(OpenAnswer questionAnswer){
        if (questionAnswer.getAnswer() != null)
            this.answer = questionAnswer.getAnswer();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
