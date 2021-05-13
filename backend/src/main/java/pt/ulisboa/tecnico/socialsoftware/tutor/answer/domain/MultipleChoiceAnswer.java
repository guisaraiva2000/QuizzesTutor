package pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.*;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.MultipleChoiceQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Option;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.List;
import java.util.ArrayList;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.QUESTION_OPTION_MISMATCH;

@Entity
@DiscriminatorValue(Question.QuestionTypes.MULTIPLE_CHOICE_QUESTION)
public class MultipleChoiceAnswer extends AnswerDetails {
    @ManyToOne
    @JoinColumn(name = "option_id")
    private List<Option> option = new ArrayList<>();

    public MultipleChoiceAnswer() {
        super();
    }

    public MultipleChoiceAnswer(QuestionAnswer questionAnswer){
        super(questionAnswer);
    }

    public MultipleChoiceAnswer(QuestionAnswer questionAnswer, List<Option> option){
        super(questionAnswer);
        this.setOption(option);
    }

    public List<Option> getOption() {
        return option;
    }

    public void setOption(List<Option> option) {
        this.option = option;

        for (int i = 0; i < option.size(); i++){
            if (option.get(i) != null)
                option.get(i).addQuestionAnswer(this);
        }   
    }

    public void addOption(Option option) {
        this.option.add(option);
    }

    public void setOption(MultipleChoiceQuestion question, MultipleChoiceStatementAnswerDetailsDto multipleChoiceStatementAnswerDetailsDto) {
        if (multipleChoiceStatementAnswerDetailsDto.getOptionId() != null) {
            Option opt = question.getOptions().stream()
                    .filter(option1 -> option1.getId().equals(multipleChoiceStatementAnswerDetailsDto.getOptionId()))
                    .findAny()
                    .orElseThrow(() -> new TutorException(QUESTION_OPTION_MISMATCH, multipleChoiceStatementAnswerDetailsDto.getOptionId()));

            this.addOption(opt);
        } else {
            this.setOption(null);
        }
    }

    @Override
    public boolean isCorrect() {
        boolean correct = true;
        for (int i = 0; i < getOption().size(); i++){
                correct = correct && getOption().get(i).isCorrect() && getOption().get(i) != null;
        }   
        return correct;
    }


    public void remove() {
        if (option != null) {
            for (int i = 0; i < option.size(); i++){
                option.get(i).getQuestionAnswers().remove(this);
            }
            option = null;
        }
    }

    @Override
    public AnswerDetailsDto getAnswerDetailsDto() {
        return new MultipleChoiceAnswerDto(this);
    }

    @Override
    public boolean isAnswered() {
        return this.getOption() != null;
    }

    @Override
    public String getAnswerRepresentation() {
        String something = "" ;

        for (int i = 0; i < this.getOption().size(); i++){
            if(this.getOption() != null){
                something += MultipleChoiceQuestion.convertSequenceToLetter(this.getOption().get(i).getSequence());
            }
            else{
                something += "-";
            }
        }
        return something;
    }

    @Override
    public StatementAnswerDetailsDto getStatementAnswerDetailsDto() {
        return new MultipleChoiceStatementAnswerDetailsDto(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAnswerDetails(this);
    }
}
