package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.MultipleChoiceStatementAnswerDetailsDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDto;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.QuestionDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Entity
@DiscriminatorValue(Question.QuestionTypes.MULTIPLE_CHOICE_QUESTION)
public class MultipleChoiceAnswerItem extends QuestionAnswerItem {

    private List<Integer> optionId = new ArrayList<>();

    public MultipleChoiceAnswerItem() {
    }

    public MultipleChoiceAnswerItem(String username, int quizId, StatementAnswerDto answer, MultipleChoiceStatementAnswerDetailsDto detailsDto) {
        super(username, quizId, answer);
        for (int i = 0; i < detailsDto.getOptionId().size(); i++){
            this.optionId.add(detailsDto.getOptionId().get(i));
        }
    }

    @Override
    public String getAnswerRepresentation(QuestionDetails questionDetails) {
        String something = "" ;

        for (int i = 0; i < this.getOptionId().size(); i++){
            if(this.getOptionId() != null){
                something += questionDetails.getAnswerRepresentation(Arrays.asList(optionId.get(i)));
            }
            else{
                something += "-";
            }
        }
        return something;
    }

    public List<Integer> getOptionId() {
        return optionId;
    }

    public void setOptionId(List<Integer> optionId) {
        this.optionId = optionId;
    }
}
