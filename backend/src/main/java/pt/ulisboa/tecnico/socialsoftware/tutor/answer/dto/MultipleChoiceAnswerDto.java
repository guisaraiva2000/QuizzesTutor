package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.MultipleChoiceAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OptionDto;
import java.util.List;
import java.util.ArrayList;

public class MultipleChoiceAnswerDto extends AnswerDetailsDto {
    private List<OptionDto> option = new ArrayList<>();

    public MultipleChoiceAnswerDto() {
    }

    public MultipleChoiceAnswerDto(MultipleChoiceAnswer answer) {
        if (answer.getOption() != null)
            for (int i = 0; i < answer.getOption().size(); i++){
                this.option.add(new OptionDto(answer.getOption().get(i)));
            }
    }

    public List<OptionDto> getOption() {
        return option;
    }

    public void setOption(List<OptionDto> option) {
        this.option = option;
    }
}
