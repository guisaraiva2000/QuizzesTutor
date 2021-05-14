import { QuestionTypes } from '@/services/QuestionHelpers';
import AnswerDetails from '@/models/management/questions/AnswerDetails';
import OpenAnswerQuestionDetails from '@/models/management/questions/OpenAnswerQuestionDetails';

export default class OpenAnswerAnswerType extends AnswerDetails {
  answer: string = '';

  constructor(jsonObj?: OpenAnswerAnswerType) {
    super(QuestionTypes.OpenAnswer);
    if (jsonObj) {
      this.answer = jsonObj.answer || this.answer;
    }
  }

  answerRepresentation(): string {
    return this.answer;
  }

  isCorrect(questionDetails: OpenAnswerQuestionDetails): boolean {
    return this.answer == questionDetails.correctAnswer;
  }
}
